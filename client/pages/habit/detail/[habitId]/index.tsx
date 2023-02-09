import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import Image from 'next/image';
import { DropDown } from '../../../../components/dropDown';
import { AiFillStar } from 'react-icons/ai';
import { useAppSelector } from '../../../../ducks/store';
import { getHabitDetail } from '../../../../module/habitFunctionMoudules';
import { BiRun } from 'react-icons/bi';
import styled from 'styled-components';

interface habitDetailOverview {
  habitId: number;
  hostUserId: number;
  title: string;
  body: string;
  thumbImgUrl: string;
  challengeStatus?: string;
  score: number;
  day: number;
}

interface habitDetailDetail {
  hostUsername: string;
  hostUserImgUrl: string;
  subTitle: string;
  bodyHTML: string;
  category: string;
  categoryId?: number;
  authType: string | null;
  authStartTime: string;
  authEndTime: string;
  challengeStatus: string;
  isBooked: boolean;
  challengers: number;
  allChallengers: number;
}

interface habitDetailImage {
  succImgUrl: string | null;
  failImgUrl?: string | null;
}

interface habitDataType {
  overview?: habitDetailOverview;
  detail?: habitDetailDetail;
  image?: habitDetailImage;
}

export type { habitDataType };

const ProgressBar = styled.div`
  width: ${(props) => `${props.width}%`};
`;

const ProgressSticker = styled.div`
  left: ${(props) => `${props.left}%`};
`;

const HabitDetail: React.FC = () => {
  const router = useRouter();
  const habitId = +router.query.habitId;
  const { userId, username } = useAppSelector((state) => state.loginIdentity);
  const [habitData, setHabitData] = useState<habitDataType>({});
  useEffect(() => {
    if (!router.isReady) return;
    getHabitDetail({ userId, habitId }).then((data) => {
      setHabitData(data);
    });
  }, [router.isReady]);

  const progressValue = Math.ceil((habitData?.overview?.day / 66) * 100);
  const progress = progressValue > 100 ? 100 : progressValue;
  const succImgClassName = 'max-w-[50%]';

  const searchButtonHandler = () => {
    router.push({
      pathname: '/habit/search',
      query: {
        categoryId: habitData.detail.categoryId,
      },
    });
  };

  return (
    <div className="habit-detail-container">
      <div className="habit-detail-top">
        <div className="habit-detail-top-image">
          <Image
            src={
              Object.keys(habitData).length
                ? habitData.overview.thumbImgUrl
                : '/image/defaultImg.jpg'
            }
            alt="habit image"
            width={500}
            height={500}
          />
        </div>
        <div className="habit-detail-top-info p-5 border-b border-borderColor">
          <div className="habit-detail-subtitle pt-2.5">
            <span className="px-2.5 py-1 bg-black text-white rounded-full text-sm font-semibold">
              {habitData?.detail?.subTitle}
            </span>
          </div>
          <div className="habit-detail-title-container flex justify-between items-center mb-5 pt-[15px]">
            <h2 className="habit-detail-title text-2xl font-bold">
              {habitData?.overview?.title}
            </h2>
            <DropDown
              dropDownType="habit"
              boolean={
                userId === habitData?.overview?.hostUserId ? true : false
              }
              habitId={habitData?.overview?.habitId}
              hostUserId={habitData?.overview?.hostUserId}
              habitData={{
                title: habitData?.overview?.title,
                imageUrl: habitData?.overview?.thumbImgUrl,
                habitId: habitData?.overview?.habitId,
                challengers: habitData?.detail?.challengers,
              }}
            />
          </div>
          <div className="habit-detail-metainfo-container flex items-center gap-2.5 h-[30px] leading-[35px]">
            <div className="habit-detail-postuser-profile-image w-[30px]">
              <Image
                src={
                  habitData?.detail?.hostUserImgUrl || '/image/baseProfile.svg'
                }
                alt="post user profile image"
                width={500}
                height={500}
              />
            </div>
            <div className="habit-detail-postuser">
              {habitData?.detail?.hostUsername}
            </div>
            <div className="habit-detail-score-container flex items-center gap-1">
              <AiFillStar className="text-subColor" />
              <span className="text-sm ">{habitData?.overview?.score}</span>
            </div>
          </div>
        </div>
      </div>
      {habitData?.detail?.challengeStatus !== 'NONE' && (
        <div className="habit-challenge-message-container p-5 border-b border-borderColor">
          <h3 className="text-lg font-semibold pb-5">
            {username}님의 진행현황
            {habitData?.detail?.challengeStatus !== 'FAIL' && '🔥'}
          </h3>
          {habitData?.detail?.challengeStatus === 'FAIL' && (
            <div>
              <div className="text-center pb-2.5 font-semibold">
                <span className="text-subColor font-bold">
                  {habitData.overview?.day}
                </span>
                번째 날에 실패하셨던 도전이에요 🥲
              </div>
              <div className="text-center font-semibold">
                다시 한 번 도전해보시는 건 어떨까요!?
              </div>
            </div>
          )}
          {habitData?.detail?.challengeStatus === 'SUCCESS' && (
            <div>
              <div className="text-center pb-2.5 font-semibold">
                성공적으로 형성한 습관이에요! 👍
              </div>
              <div className="text-center pb-5 font-semibold">
                유사한 다른 습관들도 형성해보시는건 어떨까요~?
              </div>
              <div className="flex justify-center">
                <button
                  className="py-2.5 px-5 bg-subColor rounded text-white h-[45px] leading-[28px]"
                  onClick={searchButtonHandler}
                >
                  둘러보기
                </button>
              </div>
            </div>
          )}
          {habitData?.detail?.challengeStatus === 'CHALLENGE' && (
            <div className="habit-challenge-message-wrapper relative">
              <ProgressSticker
                className="absolute -top-[5px] -translate-x-1/2"
                left={progress}
              >
                <BiRun size="30px" color="#222" />
                <div className="flex justify-center">
                  <div className="border-2 border-bordercolor h-[60px]"></div>
                </div>
              </ProgressSticker>
              <div className="habit-challenge-message-bar pt-10 pb-5">
                <div className="w-full h-[30px] border-2 border-borderColor rounded-full relative overflow-hidden flex justify-center items-center">
                  <ProgressBar
                    className={`${
                      progress <= 10
                        ? 'bg-red-700'
                        : progress <= 20
                        ? 'bg-red-500'
                        : progress <= 30
                        ? 'bg-orange-600'
                        : progress <= 40
                        ? 'bg-orange-400'
                        : progress <= 50
                        ? 'bg-yellow-500'
                        : progress <= 60
                        ? 'bg-yellow-400'
                        : progress <= 70
                        ? 'bg-green-500'
                        : progress <= 80
                        ? 'bg-green-600'
                        : progress <= 90
                        ? 'bg-green-700'
                        : 'bg-green-800'
                    } h-[26px] w-[100%] rounded-l-full ${
                      progress < 100 ? 'rounded-r-none' : 'rounded-r-full'
                    } animate-gage anim absolute left-0`}
                    width={progress}
                  ></ProgressBar>
                  <div className="w-full text-center text-sm font-semibold pt-1 z-[1]">
                    {progress}%
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
      )}
      <div className="habit-detail-middle p-5 border-b border-borderColor">
        <div className="habit-detail-body">
          <h3 className="text-lg font-semibold pb-5">상세내용</h3>
          <div
            className="[&>*]:pb-2.5 [&>*]:w-full [&>*]:whitespace-normal [&>*]:break-words"
            dangerouslySetInnerHTML={{ __html: habitData?.detail?.bodyHTML }}
          />
        </div>
      </div>
      <div className="habit-detail-bottom p-5">
        <h3 className="text-lg font-semibold pb-5">인증방법</h3>
        <p className="pb-2.5">
          인증 가능한 시간대는 매일&nbsp;
          <span className="text-subColor font-bold">
            {habitData?.detail?.authStartTime}
          </span>
          부터{' '}
          <span className="text-subColor font-bold">
            {habitData?.detail?.authEndTime}
          </span>
          까지 입니다.
        </p>
        <p>인증 사진의 올바른 예와 잘못된 예는 아래와 같습니다.</p>
        <div className="pt-5 flex justify-center gap-5">
          <div className="flex flex-col items-center flex-[1_1_50%]">
            <div
              className={`min-h-[150px] ${
                habitData?.image?.failImgUrl ? '' : succImgClassName
              } flex items-center border-[1px] border-borderColor rounded`}
            >
              <Image
                className="w-full aspect-square"
                src={
                  Object.keys(habitData).length
                    ? habitData.image.succImgUrl
                    : '/image/defaultImg.jpg'
                }
                alt="correct auth image"
                width={500}
                height={500}
                style={{ objectFit: 'cover' }}
              />
            </div>
            <div className="text-center text-green-600 pt-2.5 text-sm font-bold">
              {'올바른 인증사진 예시'}
            </div>
          </div>
          {habitData?.image?.failImgUrl && (
            <div className="flex flex-col items-center flex-[1_1_50%]">
              <div className="min-h-[150px] flex items-center border-[1px] border-borderColor rounded">
                <Image
                  className="w-full aspect-square"
                  src={habitData.image.failImgUrl}
                  alt="incorrect auth image"
                  width={500}
                  height={500}
                  style={{ objectFit: 'cover' }}
                />
              </div>
              <div className="text-center text-rose-600 pt-2.5 text-sm font-bold">
                {'잘못된 인증사진 예시'}
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default HabitDetail;

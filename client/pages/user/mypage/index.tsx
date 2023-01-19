import { useState, useEffect } from 'react';
// import { getCookie } from '../../../module/cookies';
import { useSelector } from 'react-redux';
import { useAppSelector } from '../../../ducks/store';
import axios from 'axios';
import { useRouter } from 'next/router';
import styled from 'styled-components';
import { FC } from 'react';

const ProgressBar = styled.div`
  width: ${(props) => `${props.width}%`};
`;

const MyPage: FC = () => {
  //   console.log(getCookie('accessJwtToken'));
  const userId = useAppSelector((state) => state.loginIdentity.userId);
  const [userInfo, setUserInfo] = useState(null);
  const router = useRouter();

  useEffect(() => {
    axios(`${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}`).then(
      (data): void => {
        setUserInfo(data.data);
        console.log(data.data);
      },
    );
  }, []);

  const handleHabitDetail = (id: number): void => {
    router.push(`/habit/detail/${id}`);
  };
  return (
    <>
      {userInfo && (
        <main className="flex flex-col items-stretch">
          <div className="flex flex-col items-center border-2">
            <div className="mt-2 text-[0.6rem] text-right">
              반가워요!{' '}
              <span className="text-green-600 text-[0.8rem]">
                {userInfo.biggestNumOfChallengeHabitDays}
              </span>
              일째 개근중인
            </div>
            <div className="text-2xl">{userInfo.username} 님</div>
            <div className="text-[0.7rem] text-gray-400 mb-2 ">
              Email : {userInfo.email}
            </div>
          </div>
          <div className="border-2 mt-1">
            <div className="mt-2 ml-2 mb-1 font-semibold">
              나의 습관 진행현황
            </div>
            <div className="border-2 mx-2 h-12 rounded-xl flex flex-nowrap overflow-x-auto">
              {userInfo &&
                userInfo.activeChallenges.map((e) => {
                  const progress = Math.ceil((e.authDays / 66) * 100);

                  return (
                    <button
                      onClick={() => handleHabitDetail(e.challengeId)}
                      className="h-[36px] mx-2 rounded-xl my-1 w-36 shrink-0 border p-px border-mainColor flex items-center justify-center max-h-min bg-white relative overflow-hidden z-20"
                    >
                      <ProgressBar
                        className={`absolute h-[34px] ${
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
                        }  rounded-r-xl left-0 animate-gage z-10 anim`}
                        width={progress}
                      ></ProgressBar>

                      <div className="z-30">
                        <span className="text-center ">
                          {e.habitSubTitle}
                          <span className="text-xs">{` (${e.authDays}/66)`}</span>
                        </span>
                      </div>
                    </button>
                  );
                })}
            </div>
          </div>
          <div className="border border-mainColor w-[79%] h-[100px]"></div>
        </main>
      )}
    </>
  );
};

export default MyPage;
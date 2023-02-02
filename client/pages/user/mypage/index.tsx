import React from 'react';
import { useState, useEffect, useCallback, useRef } from 'react';
import { useAppSelector } from '../../../ducks/store';
import { useRouter } from 'next/router';
import styled from 'styled-components';
import { getUserInfo } from '../../../module/userFunctionMoudules';
import { MyPageMenuList } from '../../../components/myPageMenuList';
import { Modal } from '../../../components/modal';
import Image from 'next/image';
import {
  profileChange,
  profileDelete,
} from '../../../module/userFunctionMoudules';
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend as ChartLegend,
} from 'chart.js';
import { Pie } from 'react-chartjs-2';
ChartJS.register(ArcElement, Tooltip, ChartLegend);
import {
  RadarChart,
  PolarAngleAxis,
  PolarRadiusAxis,
  PolarGrid,
  Radar,
  Legend,
  ResponsiveContainer,
  BarChart,
  Bar,
  Cell,
  XAxis,
  YAxis,
  CartesianGrid,
  ReferenceLine,
} from 'recharts';
import Link from 'next/link';
import { useForm } from 'react-hook-form';
import { FileUploader } from '../../../components/fileUploader';
import { UserInfoType } from '../../../module/moduleInterface';

interface ProfileEditType {
  userId: number;
  profileImage: File | null;
}

const graytext = 'text-[#7d7d7d] text-sm font-semibold';
const subtext =
  'text-base h-min  rounded-md text-center px-2 font-semibold text-subColor';

const MyPage = () => {
  const userId = useAppSelector((state) => state.loginIdentity.userId);
  const [userInfo, setUserInfo] = useState<UserInfoType | null>(null);
  const [isProfileModalOpen, setIsProfileModalOpen] = useState(false);
  const router = useRouter();

  const { register, watch, reset } = useForm<ProfileEditType>();

  const { profileImage } = watch();
  const [profileImagePreview, setProfileImagePreview] = useState('');

  const categoryList = [
    '운동',
    '식습관',
    '학습',
    '일상생활',
    '자기관리',
    '환경',
    '취미',
    '기타',
  ];

  //스크롤
  const [slide, setSlide] = useState(true);
  const containerRef = useRef<HTMLUListElement>(null);
  const onWheel = (e: any) => {
    lockScroll();
    const { deltaY } = e;
    const el = containerRef.current;
    if (!el) return;
    if (deltaY > 0 && slide === true) {
      setSlide(false);
      el.scrollTo({
        left: el.scrollLeft + deltaY * 2,
        behavior: 'smooth',
      });
      setSlide(true);
    }
    if (deltaY < 0 && slide === true) {
      setSlide(false);
      el.scrollTo({
        left: el.scrollLeft + deltaY * 2,
        behavior: 'smooth',
      });
      setSlide(true);
    }
  };
  const lockScroll = useCallback(() => {
    document.body.style.overflow = 'hidden';
  }, []);
  const openScroll = useCallback(() => {
    if (document.body.style.overflow === 'hidden') {
      document.body.style.removeProperty('overflow');
    }
  }, []);
  //스크롤

  useEffect(() => {
    if (profileImage && profileImage.length > 0) {
      const file = profileImage[0];
      setProfileImagePreview(URL.createObjectURL(file));
    }
  }, [profileImage]);

  useEffect(() => {
    getUserInfo({ userId }).then((data) => {
      if (data) {
        data.activeChallenges.sort((a, b) => {
          if (a.progressDays > b.progressDays) {
            return 1;
          }
          if (a.progressDays < b.progressDays) {
            return -1;
          }
          return 0;
        });
        setUserInfo(data);
        setProfileImagePreview(data.profileImageUrl);
      } else {
        router.push('/user/login');
      }
    });
  }, [isProfileModalOpen]);

  const handleHabitDetail = (id: number): void => {
    router.push(`/habit/detail/${id}`);
  };

  const ProgressBar = styled.div`
    width: ${(props) => `${props.width}%`};
  `;

  const profileEdit = (): void => {
    setIsProfileModalOpen(!isProfileModalOpen);
  };

  const profileEditModal = (imageUrl: string | null): JSX.Element => {
    return (
      <div className="flex flex-col w-full items-center">
        <div className="w-full text-center text-xl mb-3">프로필 사진 변경</div>
        <FileUploader
          imgFilePreview={profileImagePreview}
          register={register('profileImage')}
        />
        {profileImagePreview ? (
          <div className="flex justify-centeritems-center py-2 px-5 bg-mainColor rounded h-[40px] mt-5">
            <span
              className="text-base text-iconColor"
              onClick={() => {
                setProfileImagePreview('');
                reset({
                  profileImage: null,
                });
              }}
            >
              삭제하기
            </span>
          </div>
        ) : null}
      </div>
    );
  };

  const Profile = () => {
    return (
      <div className="flex flex-row items-center justify-center solid py-5 border-b-[10px] border-borderColor">
        {isProfileModalOpen ? (
          <Modal
            isOpen={isProfileModalOpen}
            setIsOpen={setIsProfileModalOpen}
            buttonName="변경하기"
            onClick={() => {
              if (profileImage) {
                profileChange({ userId, profileImage: profileImage[0] }).then(
                  (res) => {
                    setUserInfo(Object.assign({}, userInfo, res.data));
                  },
                );
              } else {
                profileDelete({ userId }).then((res) => {
                  setUserInfo(
                    Object.assign({}, userInfo, { profileImageUrl: null }),
                  );
                });
              }
              setIsProfileModalOpen(!isProfileModalOpen);
            }}
            children={profileEditModal(userInfo.profileImageUrl)}
          />
        ) : (
          ''
        )}
        <div
          className="ml-[-4rem] mr-2 w-[4.75rem] h-[4.75rem] rounded-full border flex justify-center items-center"
          onClick={profileEdit}
        >
          {userInfo.profileImageUrl ? (
            <Image
              src={userInfo.profileImageUrl}
              alt="프로필 사진"
              className="w-full h-full rounded-full cursor-pointer"
              width={500}
              height={500}
            />
          ) : (
            <Image
              src="/image/baseProfile.svg"
              alt="base profile image"
              className="w-full h-full rounded-full cursor-pointer"
              width={700}
              height={700}
            />
          )}
        </div>
        <div className="flex flex-col items-center">
          <div className="mt-2 text-[0.6rem] text-right">
            반가워요!{' '}
            <span className="text-green-600 text-[0.8rem]">
              {userInfo.biggestProgressDays}
            </span>
            일째 개근중인
          </div>
          <div className="text-2xl">{userInfo.username} 님</div>
          <div className="text-[0.7rem] text-gray-400 mb-2 ">
            Email : {userInfo.email}
          </div>
        </div>
      </div>
    );
  };

  const ActiveChallenges = () => {
    return (
      <div className="p-5">
        <div className="pb-5 font-semibold w-max text-xl">
          나의 습관 진행현황
        </div>
        <ul
          className={`py-2.5 rounded-xl flex flex-nowrap overflow-x-auto scrollbar-hide border-[1px] border-borderColor ${
            userInfo.activeChallenges.length ? '' : 'justify-center'
          }`}
          ref={containerRef}
          onWheel={(e) => {
            e.stopPropagation();
            onWheel(e);
          }}
        >
          {userInfo.activeChallenges.length ? null : (
            <li className="flex flex-col items-center">
              <div>도전중인 습관이 없어요</div>
              <Link
                href="/home/hotlist"
                className="rounded-full w-max px-2 bg-subColor text-white my-2 animate-bounce"
              >
                요즘 인기있는 습관
              </Link>
            </li>
          )}
          {userInfo.activeChallenges.map((e) => {
            const progress = Math.ceil((e.progressDays / 66) * 100);
            return (
              <li
                onClick={() => {
                  handleHabitDetail(e.habitId);
                }}
                className="h-[36px] mx-2 rounded-xl my-1 w-36 shrink-0 border p-px border-mainColor flex items-center justify-center max-h-min bg-white relative overflow-hidden cursor-pointer"
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
                  }  rounded-r-xl left-0 animate-gage z-[1] anim`}
                  width={progress}
                ></ProgressBar>
                <span
                  className={`z-[1] pt-[4px] ${
                    e.subTitle.length >= 5 ? 'text-xs' : ''
                  }`}
                >
                  {e.subTitle}
                  {e.subTitle.length < 10 ? (
                    <span className="text-[8px]">{`(${e.progressDays}/66)`}</span>
                  ) : (
                    ''
                  )}
                </span>
              </li>
            );
          })}
        </ul>
      </div>
    );
  };

  const MyStatics = () => {
    const _ = userInfo;
    const chalTotal = [..._.activeChallenges, ..._.daysOfFailList];
    const chalSuccess = _.activeChallenges.filter((e) => e.progressDays >= 66);
    const chalIng = _.activeChallenges.filter((e) => e.progressDays < 66);
    const favCate =
      categoryList[_.favoriteCategories[0]?.categoryId - 1] || null;
    const failLen = _.averageDaysOfFail;
    const totalAuth = _.numOfAuthByChallengeList.reduce((sum, e) => {
      sum += e.numOfAuth;
      return sum;
    }, 0);

    const challengeTotalData = {
      labels: ['도전중', '실패', '성공'],
      datasets: [
        {
          label: '갯수',
          data: [chalIng.length, _.daysOfFailList.length, chalSuccess.length],
          backgroundColor: [
            'rgba(255, 206, 86, 0.2)',
            'rgba(255, 99, 132, 0.2)',
            'rgba(75, 192, 192, 0.2)',
          ],
          borderColor: [
            'rgba(255, 206, 86, 1)',
            'rgba(255, 99, 132, 1)',
            'rgba(75, 192, 192, 1)',
          ],
          borderWidth: 2,
        },
      ],
    };

    const categoryCount = new Array(8).fill(0);
    _.activeCategories.forEach((e) => {
      if (e.categoryId) {
        categoryCount[e.categoryId - 1]++;
      }
    });

    const progressCount = new Array(6).fill(0);
    _.activeChallenges.forEach((e) => {
      if (e.progressDays <= 11) {
        progressCount[0]++;
        return;
      }
      if (e.progressDays <= 22) {
        progressCount[1]++;
        return;
      }
      if (e.progressDays <= 33) {
        progressCount[2]++;
        return;
      }
      if (e.progressDays <= 44) {
        progressCount[3]++;
        return;
      }
      if (e.progressDays <= 55) {
        progressCount[4]++;
        return;
      }
      if (e.progressDays < 66) {
        progressCount[5]++;
        return;
      }
    });

    const progressData = {
      labels: ['~11일', '~22일', '~33일', '~44일', '~55일', '~66일'],
      datasets: [
        {
          label: '갯수',
          data: progressCount,
          backgroundColor: [
            'rgba(141, 209, 225, 0.2)',
            'rgba(131, 166, 237, 0.2)',
            'rgba(136, 132, 216, 0.2)',
            'rgba(130, 202, 157, 0.2)',
            'rgba(164, 222, 108, 0.2)',
            'rgba(255, 198, 88, 0.2)',
          ],
          borderColor: [
            'rgba(141, 209, 225, 1)',
            'rgba(131, 166, 237, 1)',
            'rgba(136, 132, 216, 1)',
            'rgba(130, 202, 157, 1)',
            'rgba(164, 222, 108, 1)',
            'rgba(255, 198, 88, 1)',
          ],
          borderWidth: 2,
        },
      ],
    };

    const cateData = [
      {
        subject: categoryList[0],
        A: categoryCount[0],
        fullMark: Math.max(...categoryCount),
      },
      {
        subject: categoryList[1],
        A: categoryCount[1],
        fullMark: Math.max(...categoryCount),
      },
      {
        subject: categoryList[2],
        A: categoryCount[2],
        fullMark: Math.max(...categoryCount),
      },
      {
        subject: categoryList[3],
        A: categoryCount[3],
        fullMark: Math.max(...categoryCount),
      },
      {
        subject: categoryList[4],
        A: categoryCount[4],
        fullMark: Math.max(...categoryCount),
      },
      {
        subject: categoryList[5],
        A: categoryCount[5],
        fullMark: Math.max(...categoryCount),
      },
      {
        subject: categoryList[6],
        A: categoryCount[6],
        fullMark: Math.max(...categoryCount),
      },
      {
        subject: categoryList[7],
        A: categoryCount[7],
        fullMark: Math.max(...categoryCount),
      },
    ];

    let newD = [];
    for (let i = 0; i < 12; i++) {
      newD.push({
        name: `${(i + 1) * 6}일`,
        도전: chalIng.filter((el) => el.progressDays > i * 6).length,
        실패: -_.daysOfFailList.filter(
          (el) => el.daysOfFail <= (i + 1) * 6 && el.daysOfFail > i * 6,
        ).length,
      });
    }
    newD[0].실패 -= _.daysOfFailList.filter((e) => e.daysOfFail <= 0).length;
    newD[11].name = '결과';
    newD[11].성공 = chalSuccess.length;
    newD[11].실패 = -_.daysOfFailList.length;

    return (
      <div className="w-auto p-5 border-b-[10px] border-borderColor">
        <div className="pb-5 font-semibold w-max text-xl">나의 습관 통계</div>
        <div
          className={`py-2.5 rounded-xl flex-col border-[1px] border-borderColor ${
            userInfo.activeChallenges.length ? '' : 'justify-center'
          }`}
        >
          {_.activeCategories.length ? null : (
            <div className="flex flex-col items-center">
              <div>도전중인 습관이 없어요</div>
              <Link
                href="/habit/post"
                className="rounded-full w-max px-2 bg-subColor text-white my-2 animate-bounce"
              >
                직접 습관 만들어 보기
              </Link>
            </div>
          )}
          {_.activeCategories.length ? (
            <div className="statics-row-1 mb-2 flex flex-row w-[100%] my-2 items-center justify-center">
              <span className="w-3/4 flex flex-col justify-center items-center">
                <label className="text-base text-mainColor font-semibold pb-2.5">
                  도전 현황
                </label>
                <Pie data={challengeTotalData} />
              </span>
            </div>
          ) : null}
          {_.activeCategories.length ? (
            <div className="statics-row-2 flex flex-row w-[100%] my-2 mt-2 items-center">
              <span className="w-full flex justify-center  items-center">
                <label className={graytext}>성공 :</label>
                <span
                  className={
                    'text-base h-min  rounded-md text-center px-2 font-semibold text-[#4BC02A]'
                  }
                >
                  {chalSuccess.length}
                </span>
                <label className={graytext}>실패 :</label>
                <span
                  className={
                    'text-base h-min  rounded-md text-center px-2 font-semibold text-[#FF6384]'
                  }
                >
                  {_.daysOfFailList.length}
                </span>
                <label className={graytext}>도전중 :</label>
                <span
                  className={
                    'text-base h-min  rounded-md text-center px-2 font-semibold text-[#FFCC66]'
                  }
                >
                  {chalIng.length}
                </span>
              </span>
            </div>
          ) : null}
          {_.activeCategories.length ? (
            <div className="statics-row-3 mb-2 flex flex-row w-[100%] my-2 items-center justify-center">
              <span className="w-[85%] flex flex-col justify-center  items-center ">
                <label className="text-base text-mainColor font-semibold pb-2.5">
                  진행도별 습관 분류
                </label>
                <Pie data={progressData} />
              </span>
            </div>
          ) : null}
          {_.activeCategories.length ? (
            <div className="statics-row-4 mb-2 flex flex-row w-[100%] my-2 items-center">
              <span className="w-full flex justify-center mt-2 items-center ">
                <label className={graytext}>평균 도전 진행도 :</label>
                <span className={subtext}>
                  {Math.ceil(
                    chalIng.reduce((sum, e) => (sum += e.progressDays), 0) /
                      chalIng.length,
                  )}
                  일
                </span>
              </span>
            </div>
          ) : null}

          {_.activeCategories.length ? (
            <div className=" statics-row-2 flex flex-col items-center">
              <label className="text-base text-mainColor font-semibold mb-2">
                현재 진행중인 카테고리
              </label>
              <ResponsiveContainer
                width="100%"
                height={270}
                className="text-sm"
              >
                <RadarChart outerRadius={110} data={cateData}>
                  <PolarGrid />
                  <PolarAngleAxis dataKey="subject" />
                  <PolarRadiusAxis domain={[0, Math.max(...categoryCount)]} />
                  <Radar
                    dataKey="A"
                    stroke="#8884d8"
                    fill="#8884d8"
                    fillOpacity={0.6}
                  />
                </RadarChart>
              </ResponsiveContainer>
              <label className="flex justify-center items-center max-h-min mb-1 px-3 rounded-lg">
                <span className="text-[#7d7d7d] text-sm font-semibold">
                  현재 관심 있는 카테고리 :
                </span>
                <span className="text-base h-min  rounded-md text-center px-2 font-semibold text-subColor">
                  {
                    categoryList[
                      categoryCount.indexOf(Math.max(...categoryCount))
                    ]
                  }
                </span>
              </label>
              <label className="flex justify-center items-center max-h-min mb-1 px-3 rounded-lg">
                <span className="text-[#7d7d7d] text-sm font-semibold">
                  가장 많이 도전한 카테고리 :
                </span>
                <span className="text-base h-min  rounded-md text-center px-2 font-semibold text-subColor">
                  {favCate}
                </span>
              </label>
            </div>
          ) : null}
          {_.activeChallenges.length ? (
            <div className="pt-5 statics-row-3 flex flex-col items-center">
              <label className="text-base text-mainColor font-semibold mb-2">
                도전 경과 그래프
              </label>
              <ResponsiveContainer width="100%" height={400}>
                <BarChart
                  width={500}
                  height={400}
                  data={newD}
                  stackOffset="sign"
                  margin={{
                    right: 10,
                    left: -25,
                  }}
                >
                  <XAxis dataKey="name" />
                  <YAxis />
                  <Legend align="right" iconType="circle" />
                  <ReferenceLine y={0} stroke="#000" />
                  <Bar dataKey="도전" fill="#F89500" stackId="stack" />
                  <Bar dataKey="성공" fill="#4BC02A" stackId="stack" />
                  <Bar dataKey="실패" fill="#FF6384" stackId="stack" />
                </BarChart>
              </ResponsiveContainer>
              <label className="flex justify-center items-center max-h-min mb-1 px-3 rounded-lg mt-2">
                <span className="text-[#7d7d7d] text-sm font-semibold">
                  도전 실패까지 걸린 평균기간 :
                </span>
                <span className="text-base h-min  rounded-md text-center px-2 font-semibold text-[#FF6384]">
                  {failLen}일
                </span>
              </label>
            </div>
          ) : null}
        </div>
      </div>
    );
  };

  return (
    <>
      {userInfo && (
        <main
          className="flex flex-col items-stretch min-h-screen"
          onWheel={() => {
            openScroll();
          }}
        >
          <Profile />
          <ActiveChallenges />
          <MyStatics />
          <MyPageMenuList
            email={userInfo.email}
            successArr={userInfo.activeChallenges.filter(
              (e) => e.progressDays >= 66,
            )}
            bookmark={userInfo.numOfBookmarkHabit}
            hosted={userInfo.numOfHostHabit}
          />
        </main>
      )}
    </>
  );
};

export default MyPage;

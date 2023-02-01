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
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Pie } from 'react-chartjs-2';
ChartJS.register(ArcElement, Tooltip, Legend);

import logo from '../../../public/image/66logo.png';
import { useForm } from 'react-hook-form';
import { FileUploader } from '../../../components/fileUploader';
import { UserInfoType } from '../../../module/moduleInterface';

interface ProfileEditType {
  userId: number;
  profileImage: File | null;
}

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
      console.log(data);
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
          <div className="flex justify-center w-full items-center bg-mainColor rounded-full h-[40px]  mt-4">
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
      <div className="flex flex-row items-center justify-center solid border-y-2">
        {isProfileModalOpen ? (
          <Modal
            isOpen={isProfileModalOpen}
            setIsOpen={setIsProfileModalOpen}
            buttonName="변경"
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
              className="w-full h-full rounded-full"
              width={500}
              height={500}
            />
          ) : (
            <Image src={logo} alt="66logo" className="w-10 h-10" />
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
      <div className="border mx-1 pb-1 mt-2 mb-2 solid rounded-xl">
        <div className="mt-2 ml-4 mb-1 font-semibold w-max border-y border-gray-400 ">
          나의 습관 진행현황
        </div>
        <ul
          className=" mx-2 h-12 rounded-xl flex flex-nowrap overflow-x-auto scrollbar-hide"
          ref={containerRef}
          onWheel={(e) => {
            e.stopPropagation();
            onWheel(e);
          }}
        >
          {userInfo.activeChallenges.map((e) => {
            const progress = Math.ceil((e.progressDays / 66) * 100);
            return (
              <li
                onClick={() => {
                  handleHabitDetail(e.habitId);
                }}
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
                  }  rounded-r-xl left-0 animate-gage z-[1] anim`}
                  width={progress}
                ></ProgressBar>
                <span
                  className={`z-[2] ${e.subTitle.length >= 5 ? 'text-xs' : ''}`}
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
    const favCate = categoryList[_.favoriteCategories[0].categoryId];
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
          data: [chalIng.length, failLen, chalSuccess.length],
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
      categoryCount[e.categoryId - 1]++;
    });

    const categoryData = {
      labels: categoryList,
      datasets: [
        {
          label: '갯수',
          data: [...categoryCount],
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

    const progressCount = new Array(3).fill(0);
    _.activeChallenges.forEach((e) => {
      if (e.progressDays <= 22) {
        progressCount[0]++;
        return;
      }
      if (e.progressDays <= 44) {
        progressCount[1]++;
        return;
      }
      if (e.progressDays < 66) {
        progressCount[2]++;
        return;
      }
    });

    const progressData = {
      labels: ['~22일', '~44일', '~66일'],
      datasets: [
        {
          label: '갯수',
          data: progressCount,
          backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
          ],
          borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
          ],
          borderWidth: 2,
        },
      ],
    };

    return (
      <div className="w-auto mx-1 border rounded-xl">
        <div className="mt-2 ml-4 mb-6 font-semibold w-max border-y border-gray-400 ">
          나의 습관 통계
        </div>
        <div className="statics-row-1 mb-2 flex flex-row w-[100%] my-2 items-center">
          <span className="w-1/2 flex flex-col justify-center  items-center">
            <label className="text-sm bg-gray-500 rounded-lg text-white px-2">
              도전 현황
            </label>
            <Pie data={challengeTotalData} />
          </span>
          <span className="w-1/2 flex flex-col justify-center  items-center ">
            <label className="text-sm bg-gray-500 rounded-lg text-white px-2">
              진행도별 분류
            </label>
            <Pie data={progressData} />
          </span>
        </div>
        <div className="mt-4 statics-row-2 flex flex-col items-center">
          <label className="flex justify-center items-center max-h-min mb-1 bg-gray-500 px-3 rounded-lg">
            <span className="text-white text-sm">도전실패 까지 걸린 기간</span>
            <span className="ml-2 text-xs h-min bg-subColor rounded-md text-center px-2 text-white">
              {`평균 : ${failLen}일`}{' '}
            </span>
          </label>
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

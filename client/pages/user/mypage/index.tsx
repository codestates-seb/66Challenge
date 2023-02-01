import React from 'react';
import { useState, useEffect } from 'react';
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

import logo from '../../../public/image/66logo.png';
import { useForm } from 'react-hook-form';
import { FileUploader } from '../../../components/fileUploader';

interface ProfileEditType {
  userId: number;
  profileImage: File | null;
}

const MyPage = () => {
  const userId = useAppSelector((state) => state.loginIdentity.userId);
  const [userInfo, setUserInfo] = useState(null);
  const [isProfileModalOpen, setIsProfileModalOpen] = useState(false);
  const router = useRouter();

  const { register, watch, reset } = useForm<ProfileEditType>();

  const { profileImage } = watch();
  const [profileImagePreview, setProfileImagePreview] = useState('');

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
        <div className=" mx-2 h-12 rounded-xl flex flex-nowrap overflow-x-auto">
          {userInfo.activeChallenges.map((e) => {
            const progress = Math.ceil((e.progressDays / 66) * 100);
            return (
              <button
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
                  }  rounded-r-xl left-0 animate-gage z-10 anim`}
                  width={progress}
                ></ProgressBar>
                <div className="z-30">
                  <span className="text-center ">
                    {e.subTitle}
                    <span className="text-xs">{` (${e.progressDays}/66)`}</span>
                  </span>
                </div>
              </button>
            );
          })}
        </div>
      </div>
    );
  };

  const MyStatics = (): JSX.Element => {
    return (
      <div className="w-auto mx-1 border rounded-xl">
        <div className="mt-2 ml-4 mb-1 font-semibold w-max border-y border-gray-400 ">
          나의 습관 통계
        </div>
      </div>
    );
  };

  return (
    <>
      {userInfo && (
        <main className="flex flex-col items-stretch min-h-screen">
          <Profile />
          <ActiveChallenges />
          <MyStatics />
          <MyPageMenuList
            email={userInfo.email}
            successArr={userInfo.activeChallenges.filter(
              (e) => e.progressDays >= 66,
            )}
          />
        </main>
      )}
    </>
  );
};

export default MyPage;

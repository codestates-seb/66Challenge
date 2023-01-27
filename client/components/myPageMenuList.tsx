import React from 'react';
import { SlArrowRight } from 'react-icons/sl';
import { useState } from 'react';
import Link from 'next/link';
import { Modal } from './modal';
import { CertificationModal } from './certificationModal';
import { useAppDispatch } from '../ducks/store';
import { initLoginIdentity } from '../ducks/loginIdentitySlice';
import { KaKaoShare } from '../module/kakaoShare';

interface ItemProps {
  title: string;
  path: string;
}

export const MyPageMenuList = ({ email, successArr }) => {
  const [isCertActive, setIsCertActive] = useState(false);
  const [isCertOpen, setIsCertOpen] = useState(false);
  const [isInviteOpen, setIsInviteOpen] = useState(false);
  const [certId, setCertId] = useState(null);
  const dispatch = useAppDispatch();

  const CertDropDown = ({ success }): JSX.Element => {
    return (
      <div className="flex flex-col items-stretch">
        {success.length > 0 ? (
          success.map((el) => {
            return (
              <div
                key={el.challengeId}
                className={`flex place-content-between border solid border-black h-8 items-center mb-1 mx-2 rounded-xl ${
                  el.subTitle ? '' : 'justify-center'
                }`}
              >
                <span className={`${el.subTitle ? 'ml-5' : 'text-xl'}`}>
                  {el.subTitle || '성공 데이터 없음'}
                </span>
                {el.subTitle ? (
                  <button
                    className={`border-2 text-sm mr-4`}
                    onClick={(): void => {
                      setCertId(el.habitId);
                      setIsCertOpen(!isCertOpen);
                    }}
                  >
                    발급
                  </button>
                ) : (
                  ''
                )}
              </div>
            );
          })
        ) : (
          <div
            className="flex place-content-between border solid border-black h-8 items-center mb-1 mx-2 rounded-xl 
                justify-center"
          >
            <span className="text-xl">성공 데이터 없음</span>{' '}
          </div>
        )}
      </div>
    );
  };

  const handleCertOpen = (id: number): void => {};

  const MenuItem = ({ path, title }: ItemProps): JSX.Element => {
    return (
      <Link
        className="pl-5 cursor-pointer flex place-content-between border-black solid border-2 h-10 text-lg items-center mb-1"
        href={path}
      >
        <span>{title}</span>
        <div className="pr-5 ">
          <SlArrowRight className="inline align-middle dark:bg-white" />
        </div>
      </Link>
    );
  };

  const LogOut = ({ path, title }: ItemProps): JSX.Element => {
    return (
      <Link
        className="pl-5 cursor-pointer flex place-content-between border-black solid border-2 h-10 text-lg items-center mb-1"
        href={path}
        onClick={() => {
          dispatch(initLoginIdentity());
        }}
      >
        <span>{title}</span>
        <div className="pr-5 ">
          <SlArrowRight className="inline align-middle dark:bg-white" />
        </div>
      </Link>
    );
  };

  return (
    <div>
      {isCertOpen && (
        <Modal
          isOpen={isCertOpen}
          setIsOpen={setIsCertOpen}
          buttonName="종료"
          onClick={() => {
            console.log(certId);
          }}
          children={<CertificationModal />}
        />
      )}
      {isInviteOpen && (
        <Modal
          isOpen={isInviteOpen}
          setIsOpen={setIsInviteOpen}
          children={<KaKaoShare />}
        />
      )}
      <MenuItem title="찜한 습관" path="/user/mypage/savedhabit" />
      <MenuItem title="내가 만든 습관" path="/user/mypage/madehabit" />
      <div
        className="pl-5 cursor-pointer flex place-content-between border-black solid border-2 h-10 text-lg items-center mb-1"
        onClick={() => {
          setIsCertActive(!isCertActive);
        }}
      >
        <span>인증서 발급</span>
        <div className="pr-5 ">
          <SlArrowRight className="inline align-middle dark:bg-white" />
        </div>
      </div>
      {isCertActive && <CertDropDown success={successArr} />}
      <div
        className="pl-5 cursor-pointer flex place-content-between border-black solid border-2 h-10 text-lg items-center mb-1"
        onClick={() => {
          setIsInviteOpen(!isInviteOpen);
        }}
      >
        <span>친구 초대하기</span>
        <div className="pr-5 ">
          <SlArrowRight className="inline align-middle dark:bg-white" />
        </div>
      </div>
      {/* TODO : 고객센터 추가 */}
      <MenuItem title="고객 센터" path="/user/mypage" />
      <MenuItem title="회원 정보 수정" path="/user/mypage/edit" />
      <LogOut title="로그아웃" path="/" />
      <MenuItem
        title="회원탈퇴"
        path={`/user/mypage/withdraw?email=${email}`}
      />
    </div>
  );
};

import React from 'react';
import { SlArrowRight } from 'react-icons/sl';
import { useState } from 'react';
import Link from 'next/link';
import { Modal } from './modal';
import { CertificationModal } from './certificationModal';
import { useAppDispatch } from '../ducks/store';
import { initLoginIdentity } from '../ducks/loginIdentitySlice';
import { KaKaoShare } from '../module/kakaoShare';
import { getUserCertificate } from '../module/userFunctionMoudules';
import { useAppSelector } from '../ducks/store';

interface ItemProps {
  title: string;
  path: string;
  children?: JSX.Element;
}

export const MyPageMenuList = ({ email, successArr, bookmark, hosted }) => {
  const [isCertActive, setIsCertActive] = useState(false);
  const [isCertOpen, setIsCertOpen] = useState(false);
  const [isInviteOpen, setIsInviteOpen] = useState(false);
  const [certId, setCertId] = useState(null);
  const [certInfo, setCertInfo] = useState(null);
  const userId = useAppSelector((state) => state.loginIdentity.userId);
  const dispatch = useAppDispatch();

  const menuStyle =
    'w-full px-5 cursor-pointer flex place-content-between h-10 text-lg items-center mb-2 bg-white text-black hover:bg-[#f5f5f5] rounded-full hover:animate-hover';

  const CertDropDown = ({ success }): JSX.Element => {
    return (
      <div className="flex flex-col items-stretch w-[70%]">
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
                    className={`bg-mainColor text-white text-sm mr-4 px-2 rounded-full hover:bg-subColor`}
                    onClick={(): void => {
                      getUserCertificate({
                        userId,
                        habitId: el.habitId,
                      }).then((res) => {
                        setCertInfo(res.data);
                        setCertId(el.habitId);
                        setIsCertOpen(!isCertOpen);
                      });
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
            <span className="text-lg">성공 데이터 없음</span>{' '}
          </div>
        )}
      </div>
    );
  };

  const handleCertOpen = (id: number): void => {};

  const MenuItem = ({ path, title, children }: ItemProps): JSX.Element => {
    return (
      <Link className={menuStyle} href={path}>
        <div className="flex gap-2.5">
          <div className="pt-[2px]">{title}</div>
          <div>{children}</div>
        </div>

        <div className="flex items-center justify-center">
          <SlArrowRight className="inline align-middle dark:bg-white" />
        </div>
      </Link>
    );
  };

  const LogOut = ({ path, title }: ItemProps): JSX.Element => {
    return (
      <Link
        className={menuStyle}
        href={path}
        onClick={() => {
          dispatch(initLoginIdentity());
        }}
      >
        <span>{title}</span>
        <div>
          <SlArrowRight className="inline align-middle dark:bg-white" />
        </div>
      </Link>
    );
  };

  return (
    <div className="p-5 flex flex-col w-full items-center">
      {isCertOpen && (
        <Modal
          isOpen={isCertOpen}
          setIsOpen={setIsCertOpen}
          buttonName="종료"
          onClick={() => {
            console.log(certId);
            setIsCertOpen(!isCertOpen);
          }}
          children={<CertificationModal data={certInfo} />}
        />
      )}
      {isInviteOpen && (
        <Modal
          isOpen={isInviteOpen}
          setIsOpen={setIsInviteOpen}
          children={<KaKaoShare />}
        />
      )}
      <MenuItem
        title="찜한 습관"
        path="/user/mypage/savedhabit"
        children={
          <span className="w-10 h-7 text-sm pt-[3px]  mr-2 rounded-full flex justify-center items-center bg-subColor text-white font-semibold">
            {bookmark}
          </span>
        }
      />
      <MenuItem
        title="내가 만든 습관"
        path="/user/mypage/madehabit"
        children={
          <span className="w-10 h-7 text-sm pt-[3px]  mr-2 rounded-full flex justify-center items-center bg-subColor text-white font-semibold">
            {hosted}
          </span>
        }
      />
      <div
        className={menuStyle}
        onClick={() => {
          setIsCertActive(!isCertActive);
        }}
      >
        <div className="flex gap-2.5">
          <div className="pt-[2px]">인증서 발급</div>
          <div className="w-10 h-7 text-sm mr-2 pt-[3px] rounded-full flex justify-center items-center bg-subColor text-white font-semibold ">
            {successArr.length}
          </div>
        </div>
        <div className=" flex items-center justify-center">
          <SlArrowRight className="inline align-middle dark:bg-white" />
        </div>
      </div>
      {isCertActive && <CertDropDown success={successArr} />}
      <div
        className={menuStyle}
        onClick={() => {
          setIsInviteOpen(!isInviteOpen);
        }}
      >
        <span>친구 초대하기</span>
        <div>
          <SlArrowRight className="inline align-middle dark:bg-white" />
        </div>
      </div>
      <MenuItem title="회원 정보 수정" path="/user/mypage/edit" />
      <LogOut title="로그아웃" path="/" />
      <MenuItem
        title="회원탈퇴"
        path={`/user/mypage/withdraw?email=${email}`}
      />
    </div>
  );
};

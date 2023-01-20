// 현재 임의의 주소를 가져와 인증서 발급 클릭시
// 모달컴포넌트가 뜨게 만들어진 상황입니다

import { SlArrowRight } from 'react-icons/sl';
import { useState } from 'react';
import { FC } from 'react';
import { useRouter } from 'next/router';
import Link from 'next/link';
import { Modal } from './modal';
import { CertificationModal } from './certificationModal';

interface ItemProps {
  title: string;
  path: string;
}

const testSuccess = [
  { challengeId: 45, progressDays: 2, habitId: 1, subTitle: 'Mr' },
];

export const MyPageMenuList = () => {
  const [isCertActive, setIsCertActive] = useState(false);
  const [isCertOpen, setIsCertOpen] = useState(false);
  const [certId, setCertId] = useState(null);
  const router = useRouter();

  const CertDropDown = ({ success }): JSX.Element => {
    return (
      <div className="flex flex-col items-stretch">
        {success.map((el) => {
          return (
            <div className="flex place-content-between indent-3 border solid border-black h-8 items-center mb-1 mx-2 rounded-xl">
              <span className="">{el.title || '데이터 없음'}</span>
              <button
                className="border-2 text-sm mr-4"
                onClick={(): void => {
                  setCertId(el.habitId);
                  setIsCertOpen(!isCertOpen);
                }}
              >
                발급
              </button>
            </div>
          );
        })}
      </div>
    );
  };

  const handleDropDown = (): void => {
    setIsCertActive(!isCertActive);
  };

  const handleCertOpen = (id: number): void => {};

  const MenuItem = ({ path, title }: ItemProps): JSX.Element => {
    // const { path, title } = props;
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
      <MenuItem title="찜한 습관" path="/" />
      <MenuItem title="내가 만든 습관" path="/" />
      <div
        className="pl-5 cursor-pointer flex place-content-between border-black solid border-2 h-10 text-lg items-center mb-1"
        onClick={handleDropDown}
      >
        <span>인증서 발급</span>
        <div className="pr-5 ">
          <SlArrowRight className="inline align-middle dark:bg-white" />
        </div>
      </div>
      {isCertActive && <CertDropDown success={testSuccess} />}
      <MenuItem title="친구 초대" path="/" />
      <MenuItem title="고객 센터" path="/" />
      <MenuItem title="회원 정보 수정" path="/" />
      <MenuItem title="로그아웃" path="/" />
      <MenuItem title="회원탈퇴" path="/" />
    </div>
  );
};

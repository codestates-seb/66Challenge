import React from 'react';
import { RiKakaoTalkFill } from 'react-icons/ri';
import { IoClose } from 'react-icons/io5';

export const CertificationModal = () => {
  const today = new Date();
  let year = today.getFullYear(); // 년도
  let month = today.getMonth() + 1; // 월
  let date = today.getDate(); // 날짜

  return (
    <div className="w-96 h-full flex-columns text-center justify-center border-black border rounded-2xl">
      <div className="flex justify-end">
        <IoClose
          className="cursor-pointer p-2.5 mt-2.5"
          size="50"
          // onClick={closeModalHandler}
        />
      </div>

      <img
        src="../image/prizeborder.jpg"
        className="bg-cover bg-no-repeat inline-block my-3 max-w-full -mt-2"
        alt="prizetemplit"
      />

      <div className="border-2 absolute left-14 top-1/4 w-full">
        <h2 className="text-center text-2xl mb-10">인증서</h2>
        <div className="flex justify-center">
          <div className="mx-3 mb-16">
            <p>마스터한 습관</p>
            <p className="text-footerMemberTextColor">여기습관이름 올려</p>
          </div>
          <div className="mx-3">
            <p>
              시작일자:{' '}
              <span className="text-footerMemberTextColor">해당날짜올려</span>
            </p>
            <p>
              성공일자:{' '}
              <span className="text-footerMemberTextColor">해당날짜올려</span>
            </p>
          </div>
        </div>
        <div className="mb-20 text-xl">
          <span className="text-footerMemberTextColor">xxx</span> 님은
          성공적으로 습관 체득에 성공하셨음을 66 Challenge에서 인증합니다.
        </div>
        <div className="flex justify-center mb-2">
          <img src="../image/66logoForModal.svg" alt="66ChagllengeLogo" />
        </div>
        <p>
          {year}년 {month}월 {date}일
        </p>
      </div>

      <div className="flex justify-center -mt-2 mb-5">
        <RiKakaoTalkFill size="30" />
        {/* 카카오톡 공유하기 버튼 */}
      </div>
    </div>
  );
};

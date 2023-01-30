import React from 'react';
import { RiKakaoTalkFill } from 'react-icons/ri';
import { IoClose } from 'react-icons/io5';

export const CertificationModal = ({ data }) => {
  console.log(data);
  const today = new Date();
  let year = today.getFullYear(); // 년도
  let month = today.getMonth() + 1; // 월
  let date = today.getDate(); // 날짜

  return (
    <div className="w-full h-full flex-columns text-center p-2.5 mt-2.5 justify-center border-black border rounded-2xl">
      <img
        src="../image/prizeborder.jpg"
        className="bg-cover bg-no-repeat inline-block my-3 max-w-full"
        alt="prizetemplit"
      />

      <div className="absolute left-2 top-12 w-full">
        <h2 className="text-center text-xl mb-10">인증서</h2>
        <div className="flex justify-center">
          <div className="mx-3 mb-16 text-xs">
            <p>마스터한 습관</p>
            <p className="text-footerMemberTextColor text-xs">{data.title}</p>
          </div>
          <div className="mx-3 text-xs">
            <p>
              시작일자:{' '}
              <span className="text-footerMemberTextColor">
                {data.createdAt.substr(0, 4)}년 {data.createdAt.substr(5, 2)}월{' '}
                {data.createdAt.substr(8, 2)}일
              </span>
            </p>
            <p>
              성공일자:{' '}
              <span className="text-footerMemberTextColor">
                {data.completedAt.substr(0, 4)}년{' '}
                {data.completedAt.substr(5, 2)}월{' '}
                {data.completedAt.substr(8, 2)}일
              </span>
            </p>
          </div>
        </div>
        <div className="mb-10 text-xs">
          <span className="text-footerMemberTextColor">{data.username}</span>{' '}
          님은 성공적으로 습관 체득에 성공하셨음을{' '}
          <p>66 Challenge에서 인증합니다.</p>
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

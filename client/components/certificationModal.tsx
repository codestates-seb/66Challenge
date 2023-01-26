import React from 'react';
import { RiKakaoTalkFill } from 'react-icons/ri';
import { IoClose } from 'react-icons/io5';

export const CertificationModal = () => {
  return (
    <div className="w-96 h-96 bg-footerColor flex-columns text-center justify-center">
      <div className="h-5 flex justify-end items-center">
        <IoClose
          className="cursor-pointer p-2.5 mt-2.5"
          size="50"
          // onClick={closeModalHandler}
        />
      </div>
      <div className="border-2">
        <h2 className="text-center">인증서</h2>
        <div className="flex justify-center">
          <div className="mx-3">
            <p>마스터한 습관</p>
            <p>여기습관이름 올려</p>
          </div>
          <div className="mx-3">
            <p>시작일자: 해당날짜올려</p>
            <p>성공일자: 해당날짜올려</p>
          </div>
        </div>
        <div>
          xxx 님은 성공적으로 습관 체득에 성공 하셨음을 66 Challenge에서
          인증합니다.
        </div>
        <div className="flex justify-center">
          <img src="../image/66logoForModal.svg" />
        </div>
        <p>여기다 날짜 박어</p>
      </div>
      <div className="flex justify-center">
        <RiKakaoTalkFill />
        {/* 카카오톡 공유하기 버튼 */}
      </div>
    </div>
  );
};

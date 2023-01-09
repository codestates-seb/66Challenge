import { useState } from 'react';

export function ReviewHabitBottomNav() {
  //게시글 클릭 유저의 상황에 따른 달라지는 네비게이션 현재는 임의로 조정
  //useState 초기 값은 getServerSideProps로 받아와 부여 할 생각 중 해봐야 알듯.

  const reviewHabitHandle = () => {
    //리뷰 모달창 꺼내야 한다.
  };
  return (
    <div className="flex bg-white h-[3rem] px-6  w-full fixed bottom-0 min-w[300px] justify-center items-center">
      <button
        className="bg-mainColor h-3/4 w-full rounded-lg  text-textColor"
        onClick={reviewHabitHandle}
      >
        write Review
      </button>
    </div>
  );
}

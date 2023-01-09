import { useState } from 'react';

export function EditHabitBottomNav() {
  //게시글 클릭 유저의 상황에 따른 달라지는 네비게이션 현재는 임의로 조정
  //useState 초기 값은 getServerSideProps로 받아와 부여 할 생각 중 해봐야 알듯.

  const startHabitHandle = () => {
    //습관 시작 모달 호출 핸들
    //이미 진행 중인 유저는 하는 중이다 알려주는 모달 창 띄우기
  };
  const editHabitHandle = () => {
    //수정 페이지 이동해야함. 프롭스로 전달 예정
  };
  return (
    <div className="flex bg-white h-[3rem] px-6  w-full fixed bottom-0 min-w[300px] justify-center items-center">
      <button
        className="bg-mainColor h-3/4 w-1/2 rounded-lg mr-2.5 text-textColor"
        onClick={editHabitHandle}
      >
        Edit
      </button>
      <button
        className="bg-mainColor h-3/4 w-1/2 rounded-lg ml-2.5 text-textColor"
        onClick={startHabitHandle}
      >
        Start
      </button>
    </div>
  );
}

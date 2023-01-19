import { useState } from 'react';
import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';

interface IBookMarkValue {
  boolean: boolean;
  animate: string;
}

export function StartHabitBottomNav() {
  const [isBookMark, setIsBookMark] = useState<IBookMarkValue>({
    boolean: false,
    animate: 'h-3/4 w-1/4 text-subColor ',
  });
  const bookMarkHandle = (): void => {
    //login 여부 확인 후 false면 로그인 페이지로 경로 설정
    //북마크 관련 비동기 요청 함수
    if (isBookMark.boolean === false) {
      setIsBookMark({
        boolean: true,
        animate: isBookMark.animate + 'animate-bookMark',
      });
    } else {
      setIsBookMark({ boolean: false, animate: 'animate-bookMark' });
    }
  };
  const startHabitHandle = () => {
    //습관 시작 모달 호출 핸들
    //이미 진행 중인 유저는 하는 중이다 알려주는 모달 창 띄우기
  };

  return (
    <div className="flex bg-white h-[3rem] px-6  w-full fixed bottom-0 border-t min-w[300px] justify-center items-center">
      {isBookMark.boolean === false ? (
        <AiOutlineHeart
          className="h-3/4 w-1/4 text-subColor"
          onClick={bookMarkHandle}
        />
      ) : (
        <AiFillHeart className={isBookMark.animate} onClick={bookMarkHandle} />
      )}

      <button
        className="bg-mainColor h-4/5 w-3/4 rounded-lg ml-5 text-iconColor text-base"
        onClick={startHabitHandle}
      >
        Start
      </button>
    </div>
  );
}

import { useState } from 'react';
import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
export function StartHabitBottomNav() {
  const [isBookMark, setIsBookMark] = useState(true);
  const [animate, setAnimate] = useState('h-3/4 w-1/4 text-subColor ');
  const bookMarkHandle = () => {
    //login 여부 확인 후 false면 로그인 페이지로 경로 설정
    //북마크 관련 비동기 요청 함수
    if (isBookMark === false) {
      setAnimate(animate + 'animate-bookMark');
    } else {
      setAnimate('h-3/4 w-1/4 text-subColor ');
    }
    setIsBookMark(!isBookMark);
  };
  const startHabitHandle = () => {
    //습관 시작 모달 호출 핸들
    //이미 진행 중인 유저는 하는 중이다 알려주는 모달 창 띄우기
  };

  return (
    <div className="flex bg-white h-[3rem] px-6  w-full fixed bottom-0 border-t min-w[300px] justify-center items-center">
      {isBookMark === false ? (
        <AiOutlineHeart
          className="h-3/4 w-1/4 text-subColor"
          onClick={bookMarkHandle}
        />
      ) : (
        <AiFillHeart className={animate} onClick={bookMarkHandle} />
      )}

      <button
        className="bg-mainColor h-4/5 w-3/4 rounded-lg ml-5 text-iconColor"
        onClick={startHabitHandle}
      >
        Start
      </button>
    </div>
  );
}

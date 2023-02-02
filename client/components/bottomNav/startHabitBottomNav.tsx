import { useState, useEffect } from 'react';
import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
import { Modal } from '../modal';
import {
  postStartChallenge,
  postBookMark,
  deleteBookMark,
} from '../../module/habitFunctionMoudules';
import { useRouter } from 'next/router';
interface IBookMarkValue {
  boolean: boolean;
  animate: string;
}
interface IPropValue {
  habitId: number;
  userId: number;
  isLogin: boolean;
  challengeStatus: string;
  isBooked: boolean;
}
export function StartHabitBottomNav({
  habitId,
  userId,
  isLogin,
  challengeStatus,
  isBooked,
}: IPropValue) {
  const defaultClassName = 'h-3/4 w-full text-subColor ';
  const [isBookMark, setIsBookMark] = useState<IBookMarkValue>({
    boolean: false,
    animate: defaultClassName,
  });
  const router = useRouter();
  const [isOpen, setIsOpen] = useState(false);
  const [isStart, setIsStart] = useState(false);
  const bookMarkHandle = async () => {
    //login 여부 확인 후 false면 로그인 페이지로 경로 설정
    //북마크 관련 비동기 요청 함수
    if (isBookMark.boolean === false && isLogin === true) {
      setIsBookMark({
        boolean: true,
        animate: defaultClassName + 'animate-bookMark',
      });
      await postBookMark({ habitId, userId });
    } else if (isBookMark.boolean === true && isLogin === true) {
      setIsBookMark({ boolean: false, animate: defaultClassName });
      await deleteBookMark({ habitId, userId });
    } else {
      router.push('/user/login');
    }
  };
  const startHabitHandle = (): void => {
    if (isLogin === false) {
      router.push('/user/login');
    } else {
      setIsOpen(true);
    }
  };
  console.log(challengeStatus);
  useEffect(() => {
    if (isBooked === true) {
      setIsBookMark({ boolean: true, animate: defaultClassName });
    }
  }, [isBooked]);
  return (
    <div className="flex bg-white h-[60px] w-full  min-w-[360px] max-w-[460px] fixed bottom-0 border-t pr-2  items-center z-[2]">
      <div className="w-[15%] h-full flex items-center justify-center">
        {isBookMark.boolean === false ? (
          <AiOutlineHeart
            className={isBookMark.animate}
            onClick={bookMarkHandle}
          />
        ) : (
          <AiFillHeart
            className={isBookMark.animate}
            onClick={bookMarkHandle}
          />
        )}
      </div>
      <button
        className="bg-mainColor h-4/5 w-5/6 rounded-lg  text-iconColor text-base disabled:bg-footerColor"
        onClick={() => {
          startHabitHandle();
        }}
        disabled={
          challengeStatus == 'CHALLENGE' ||
          isStart === true ||
          challengeStatus !== 'FAIL'
        }
      >
        {challengeStatus === 'CHALLENGE' ||
        isStart === true ||
        challengeStatus !== 'FAIL'
          ? '진행 중인 습관'
          : '습관 시작하기'}
      </button>
      {isOpen && (
        <Modal
          isOpen={isOpen}
          setIsOpen={setIsOpen}
          buttonName="습관 시작"
          onClick={async () => {
            const response: number = await postStartChallenge({
              userId,
              habitId,
            });
            if (response !== 201) {
              router.push('/user/login');
            } else {
              setIsOpen(false);
              setIsStart(true);
            }
          }}
        >
          <div className="flex flex-col items-center">
            <img src="/image/logo.svg" className="mb-[40px] " />
            <span className="block text-base text-center font-semibold ">
              이겨내고 나아가든가, 사로잡혀 좌절하든가
            </span>
            <span>-크리스 가드너-</span>
          </div>
        </Modal>
      )}
    </div>
  );
}

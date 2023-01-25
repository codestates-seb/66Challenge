import { useState, useEffect } from 'react';
import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
import { Modal } from '../modal';
import { postStartChallenge } from '../../module/habitFunctionMoudules';
import { useRouter } from 'next/router';
interface IBookMarkValue {
  boolean: boolean;
  animate: string;
}
interface IidValue {
  habitId: number;
  userId: number;
  isLogin: boolean;
}
export function StartHabitBottomNav({ habitId, userId, isLogin }: IidValue) {
  const [isBookMark, setIsBookMark] = useState<IBookMarkValue>({
    boolean: false,
    animate: 'h-3/4 w-1/4 text-subColor ',
  });
  const router = useRouter();
  const [isOpen, setIsOpen] = useState(false);
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
  const startHabitHandle = (): void => {
    if (isLogin === false) {
      router.push('/user/login');
    } else {
      setIsOpen(true);
    }
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
        onClick={() => {
          startHabitHandle();
        }}
        // disabled={doing === true}
      >
        Start
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

import { useState } from 'react';
import { useRouter } from 'next/router';
import { Modal } from '../modal';
import { postStartChallenge } from '../../module/habitFunctionMoudules';
interface IPropValue {
  habitId: number;
  userId: number;
  challengeStatus: string;
  challengers: number;
}
export function EditHabitBottomNav({
  habitId,
  userId,
  challengeStatus,
  challengers,
}: IPropValue) {
  const [isOpen, setIsOpen] = useState(false);
  const [isStart, setIsStart] = useState(false);
  const router = useRouter();
  const startHabitHandle = (): void => {
    setIsOpen(true);
  };
  const editHabitHandle = (): void => {
    router.push(`/habit/edit/${habitId}`);
  };

  return (
    <div className="flex bg-white h-[60px] px-6 w-full fixed bottom-0 min-w[300px] justify-center items-center border-t min-w-[360px] max-w-[460px] z-[2]">
      {challengers !== 0 || isStart === true ? null : (
        <button
          className="bg-mainColor h-3/4 w-1/2 rounded-lg mr-2.5 text-iconColor text-base"
          onClick={editHabitHandle}
        >
          습관 수정
        </button>
      )}

      <button
        className="bg-mainColor h-3/4 w-full rounded-lg ml-2.5 text-iconColor text-base disabled:opacity-50"
        onClick={startHabitHandle}
        disabled={challengeStatus === 'CHALLENGE' || isStart === true}
      >
        {challengeStatus === 'CHALLENGE' || isStart === true
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
            }
            setIsOpen(false);
            setIsStart(true);
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

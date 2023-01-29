import { useState } from 'react';
import { Modal } from '../modal';
import { AiOutlineStar, AiFillStar } from 'react-icons/ai';
import { postHabitReview } from '../../module/reviewFunctionModules';
import { useRouter } from 'next/router';
interface IPropValue {
  habitId: number;
  userId: number;
}
export function ReviewHabitBottomNav({ habitId, userId }) {
  const [isOpen, setIsOpen] = useState(false);
  const [value, setValue] = useState('');
  const [score, setScore] = useState(-1);
  const router = useRouter();

  const scoreHandle = (score: number) => {
    setScore(score);
  };
  const max = new Array(5).fill(null);

  return (
    <div className="flex bg-white h-[3rem] px-6 w-full fixed bottom-0 min-w-[360px] max-w-[460px] justify-center items-center border-t">
      <button
        className="bg-mainColor h-3/4 w-full rounded-lg  text-iconColor text-base"
        onClick={() => {
          setIsOpen(true);
        }}
      >
        write Review
      </button>
      {isOpen && (
        <Modal
          isOpen={isOpen}
          setIsOpen={setIsOpen}
          buttonName="후기작성 완료"
          onClick={(): void => {
            postHabitReview({
              userId,
              habitId: Number(habitId),
              body: value,
              score: score + 1,
            });
            router.push(`/habit/detail/${habitId}/review`);
          }}
        >
          <form className="flex flex-col">
            <div className="flex items-center mb-4">
              <span className="text-base font-semibold mr-2">습관 만족도</span>
              {max.map((_, idx) => {
                if (idx > score) {
                  return (
                    <AiOutlineStar
                      key={idx}
                      onClick={() => scoreHandle(idx)}
                      className="text-[20px] mr-1 "
                    />
                  );
                } else if (idx <= score) {
                  return (
                    <AiFillStar
                      key={idx}
                      onClick={() => scoreHandle(idx)}
                      className="text-subColor text-[20px] mr-1 animate-bookMark"
                    />
                  );
                }
              })}
            </div>
            <div>
              <label
                htmlFor="reviewInput"
                className="block text-base font-semibold mb-2"
              >
                성공 후기
              </label>
              <textarea
                id="reviewInput"
                className="w-full h-40 border border-mainColor rounded-lg focus:outline-subColor "
                value={value}
                onChange={(e) => setValue(e.target.value)}
              />
            </div>
          </form>
        </Modal>
      )}
    </div>
  );
}

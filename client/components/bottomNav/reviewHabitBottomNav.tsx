import { useEffect, useState } from 'react';
import { Modal } from '../modal';
import { AiOutlineStar, AiFillStar } from 'react-icons/ai';
import { postHabitReview } from '../../module/habitFunctionMoudules';
import { useAppSelector } from '../../ducks/store';
import { useRouter } from 'next/router';
export function ReviewHabitBottomNav() {
  const [isOpen, setIsOpen] = useState(true);
  const [value, setValue] = useState('');
  const [score, setScore] = useState(-1);
  const router = useRouter();

  const scoreHandle = (score: number) => {
    setScore(score);
  };
  const { userId } = useAppSelector((state) => state.loginIdentity);
  const max = new Array(5).fill(null);
  const habitId: string | string[] = router.query.habitId;

  return (
    <div className="flex bg-white h-[3rem] px-6  w-full fixed bottom-0 min-w[300px] justify-center items-center border-t">
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
          onClick={() => console.log(userId, habitId, value)}
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
                      className="text-[20px] mr-1"
                    />
                  );
                } else if (idx <= score) {
                  return (
                    <AiFillStar
                      key={idx}
                      onClick={() => scoreHandle(idx)}
                      className="text-subColor text-[20px] mr-1"
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
                className="w-full h-40 border border-mainColor rounded-lg "
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

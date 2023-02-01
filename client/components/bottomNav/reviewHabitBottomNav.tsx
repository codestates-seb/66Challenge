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
  const [authState, setAuthState] = useState({ state: '', boolean: false });
  const router = useRouter();

  const scoreHandle = (score: number) => {
    setScore(score);
  };
  const max = new Array(5).fill(null);
  return (
    <div className="flex bg-white h-[3rem] px-6 w-full fixed bottom-0 min-w-[360px] max-w-[460px] justify-center items-center border-t">
      <button
        className="bg-mainColor h-3/4 w-full rounded-lg  text-iconColor text-base disabled:opacity-50"
        disabled={authState.boolean === true}
        onClick={() => {
          setIsOpen(true);
        }}
      >
        성공후기 등록
      </button>
      {isOpen && (
        <Modal
          isOpen={isOpen}
          setIsOpen={setIsOpen}
          buttonName="후기작성 완료"
          onClick={async () => {
            const response = await postHabitReview({
              userId,
              habitId: Number(habitId),
              body: value,
              score: score + 1,
            });
            if (response === 409) {
              setAuthState({ state: 'none', boolean: true });
              setTimeout(() => {
                setAuthState({ state: '', boolean: true });
              }, 1500);
            }

            setIsOpen(false);
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
      <div
        className={`${
          authState.state === 'none' ? 'flex' : 'hidden'
        } absolute w-3/4 bg-white border-2 border-subColor rounded-full -top-[700px] justify-center h-10 items-center animate-dropDown`}
      >
        <span className="text-subColor font-semibold">
          이미 성공 후기를 등록하셨습니다!
        </span>
      </div>
    </div>
  );
}

import { useRouter } from 'next/router';
import { useEffect, useState } from 'react';
import { getHabitStatistics } from '../../../../module/habitFunctionMoudules';
import { MdExpandMore } from 'react-icons/md';
import { AiFillCrown } from 'react-icons/ai';
interface Iratio {
  ageRatio: (string | unknown)[];
  sexRatio: (string | unknown)[];
  statusRatio: (string | unknown)[];
}
interface IarrowValue {
  className: string;
  boolean: boolean;
}
export default function HabitStatisticDetail() {
  const router = useRouter();
  const [highRatio, setHighRatio] = useState({
    ageRatio: [],
    sexRatio: [],
    statusRatio: [],
  });
  const [arrowDirection, setArrowDirection] = useState<IarrowValue>({
    className: '',
    boolean: false,
  });
  const upArrow: string = 'rotate-180 duration-500';
  const downArrow: string = 'rotate-0 ';
  const arrowDirectionHandle = (): void => {
    if (arrowDirection.boolean === false) {
      setArrowDirection({ className: upArrow, boolean: true });
    } else {
      setArrowDirection({ className: downArrow, boolean: false });
    }
  };
  const dummy = {
    ageRatio: {
      teenager: 10,
      twenties: 20,
      thirties: 30,
      forties: 10,
      fifties: 15,
      sixties: 5,
      seventies: 10,
    },
    sexRatio: {
      male: 60,
      female: 40,
    },
    statusRatio: {
      challenge: 60,
      success: 10,
      fail: 30,
    },
  };
  const korean = [
    ['teenager', '10대'],
    ['twenties', '20대'],
    ['thirties', '30대'],
    ['forties', '40대'],
    ['fifties', '50대'],
    ['sixties', '60대'],
    ['seventies', '70대'],
    ['male', '남자'],
    ['female', '여자'],
    ['challenge', '도전'],
    ['success', '성공'],
    ['fail', '실패'],
  ];
  useEffect(() => {
    if (!router.isReady) {
      return;
    }
    async function axiosFunc() {
      const response = await getHabitStatistics(Number(router.query.habitId));
      if (typeof response === 'number') {
        // alert('통계 조회 에러 발생');
      } else {
        let obj: Iratio = { ageRatio: [], sexRatio: [], statusRatio: [] };
        for (const key in response) {
          const ratio: (string | unknown)[] = Object.entries(response[key])
            .sort((a, b) => {
              return +b[1] - +a[1];
            })
            .map((el) => {
              for (let i = 0; i < korean.length; i++) {
                if (el[0] === korean[i][0]) {
                  return [korean[i][1], el[1]];
                }
              }
            });
          if (key === 'ageRatio') {
            obj.ageRatio = ratio;
          } else if (key === 'sexRatio') {
            obj.sexRatio = ratio;
          } else if (key === 'statusRatio') {
            obj.statusRatio = ratio;
          }
        }
        setHighRatio(obj);
      }
    }
    axiosFunc();
  }, [router.isReady]);
  const defaultSectionClassName =
    'flex flex-col border w-full min-h-[200px] mb-5  p-2 border-subColor rounded-md flex-wrap justify-between';
  const defaultRatioClassName =
    'w-full aspect-square bg-mainColor rounded-full flex flex-col items-center justify-center relative';
  const defaultTitleClassName = 'text-[18px] font-bold block';
  return (
    <div className="habit-detail-container px-5 w-full min-w-[360px] max-w-[460px]  py-5 ">
      <div className={`habit-detail-top ${defaultSectionClassName} `}>
        <div className="w-full flex items-center justify-between ">
          <span className={`${defaultTitleClassName}`}>
            이 습관의 성공 비율
          </span>
        </div>
        <div className="w-full grid grid-cols-3 justify-center items-center gap-5 ">
          {highRatio.statusRatio.map((el, idx) => {
            return (
              <div
                className={`${defaultRatioClassName} ${
                  idx === 0 ? 'animate-bounce' : null
                }`}
                key={idx}
              >
                {idx === 0 ? (
                  <AiFillCrown className="absolute text-[40px] text-subColor -top-8" />
                ) : null}

                <span className="text-iconColor">{el[0]}</span>
                <span className="text-iconColor">{el[1]}%</span>
              </div>
            );
          })}
        </div>
      </div>
      <div className={`habit-detail-middle  ${defaultSectionClassName}`}>
        <div className="w-full flex items-center justify-between mb-3">
          <span className={`${defaultTitleClassName}`}>
            이 습관에 참여한 남녀 비율
          </span>
        </div>
        <div className="w-full flex justify-center items-center ">
          {highRatio.sexRatio.map((el, idx) => {
            return (
              <div
                className={`w-[30%] aspect-square bg-mainColor rounded-full flex flex-col items-center justify-center mx-5 relative ${
                  idx === 0 ? 'animate-bounce' : null
                }`}
                key={idx}
              >
                {idx === 0 ? (
                  <AiFillCrown className="absolute text-[40px] text-subColor -top-7" />
                ) : null}
                <span className="text-iconColor">{el[0]}</span>
                <span className="text-iconColor">{el[1]}%</span>
              </div>
            );
          })}
        </div>
      </div>
      <div className={`habit-detail-bottom ${defaultSectionClassName}`}>
        <div className="w-full flex items-center justify-between mb-12">
          <span className={`${defaultTitleClassName}`}>
            이 습관에 참여한 나이 비율
          </span>
          <MdExpandMore
            size={18}
            className={arrowDirection.className}
            onClick={arrowDirectionHandle}
          />
        </div>

        <div className="w-full grid grid-cols-3 justify-center items-center gap-5">
          {highRatio.ageRatio.slice(0, 3).map((el, idx) => {
            return (
              <div
                className={`${defaultRatioClassName} ${
                  idx === 0 ? 'animate-bounce' : null
                }`}
                key={idx}
              >
                {idx === 0 ? (
                  <AiFillCrown className="absolute text-[40px] text-subColor -top-7" />
                ) : null}
                <span className="text-iconColor">{el[0]}</span>
                <span className="text-iconColor">{el[1]}%</span>
              </div>
            );
          })}

          {arrowDirection.boolean === true
            ? highRatio.ageRatio.slice(3).map((el, idx) => {
                return (
                  <div
                    className="w-full aspect-square bg-mainColor rounded-full flex flex-col items-center justify-center"
                    key={idx}
                  >
                    <span className="text-iconColor">{el[0]}</span>
                    <span className="text-iconColor">{el[1]}%</span>
                  </div>
                );
              })
            : null}
        </div>
      </div>
    </div>
  );
}

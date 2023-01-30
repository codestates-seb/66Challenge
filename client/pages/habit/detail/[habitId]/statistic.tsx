import { useRouter } from 'next/router';
import { useEffect } from 'react';
import { getHabitStatistics } from '../../../../module/habitFunctionMoudules';
export default function HabitStatisticDetail() {
  const router = useRouter();
  const habitId: string | string[] = router.query.habitId;
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

  useEffect(() => {
    if (!router.isReady) {
      return;
    }
    //   async function axiosFunc() {
    //    const response=await getHabitStatistics(Number(router.query.habitId));
    //   }
    // axiosFunc();
  }, [router.isReady]);

  return (
    <div className="habit-detail-container px-5">
      <div className="habit-detail-top">
        <div>정해진 것이 없다.</div>
        <div></div>
      </div>
      <div className="habit-detail-middle"></div>
      <div className="habit-detail-bottom"></div>
    </div>
  );
}

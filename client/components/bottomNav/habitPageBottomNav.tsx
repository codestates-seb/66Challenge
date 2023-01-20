import { useEffect, useMemo, useState } from 'react';
import { StartHabitBottomNav } from './startHabitBottomNav';
import { EditHabitBottomNav } from './editHabitBottomNav';
import { ReviewHabitBottomNav } from './reviewHabitBottomNav';
import { useRouter } from 'next/router';
import { getHabitDetail } from '../../module/habitFunctionMoudules';
import { useAppSelector } from '../../ducks/store';
interface IhabitDetail {
  authEndTime: string;
  authStartTime: string;
  authType: string;
  body: string;
  category: string;
  habitId: number;
  isBooked: boolean;
  score: number;
  subTitle: string;
  title: string;
  userId: number;
}
export function HabitPageBottomNav() {
  //user가 접근한 습관에 대해 시작 여부에 따른 분배
  const router = useRouter();
  const { userId, isLogin } = useAppSelector((state) => state.loginIdentity);
  const [habitId, setHabitId] = useState(0);
  const a = 3;

  const id = useMemo(() => {
    const id = router.query.habitId;
    return id;
  }, [router.query.habitId]);
  useEffect(() => {
    setHabitId(() => (id !== undefined ? Number(router.query.habitId) : 0));
  }, [id]);
  if (isLogin === false || a === 3) {
    return (
      <StartHabitBottomNav
        habitId={habitId}
        userId={userId}
        isLogin={isLogin}
      />
    );
  } else if (a === 2) {
    return <EditHabitBottomNav />;
  } else {
    return <ReviewHabitBottomNav />;
  }
}

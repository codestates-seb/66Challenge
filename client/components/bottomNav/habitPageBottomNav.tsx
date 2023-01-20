import { useEffect, useState } from 'react';
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
  const { userId } = useAppSelector((state) => state.loginIdentity);
  const habitId: string | string[] = router.query.habitId;
  const a = 1;

  if (a === 3) {
    return <StartHabitBottomNav />;
  } else if (a === 2) {
    return <EditHabitBottomNav />;
  } else {
    return <ReviewHabitBottomNav />;
  }
}

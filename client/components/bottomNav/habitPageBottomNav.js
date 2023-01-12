import { useState } from 'react';
import { StartHabitBottomNav } from './startHabitBottomNav';
import { EditHabitBottomNav } from './editHabitBottomNav';
import { ReviewHabitBottomNav } from './reviewHabitBottomNav';
export function HabitPageBottomNav() {
  const a = 1;
  //현재 유저와 습관 게시글의 연관을 따져서 작업해야함
  if (a === 1) {
    return <StartHabitBottomNav />;
  } else if (a === 2) {
    return <EditHabitBottomNav />;
  } else {
    return <ReviewHabitBottomNav />;
  }
}

import { useState } from 'react';
import { StartHabitBottomNav } from './startHabitBottomNav';
import { EditHabitBottomNav } from './editHabitBottomNav';
import { ReviewHabitBottomNav } from './reviewHabitBottomNav';
export function HabitPageBottomNav() {
  const a = 1;

  if (a === 1) {
    return <StartHabitBottomNav />;
  } else if (a === 2) {
    return <EditHabitBottomNav />;
  } else {
    return <ReviewHabitBottomNav />;
  }
}

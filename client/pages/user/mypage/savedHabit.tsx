import { HabitWrapperVertical } from '../../../components/habitWrapperVertical';
import { useState } from 'react';
import { useIntersection } from '../../../hooks/useIntersection';

export default function SavedHabit() {
  const [savedHabits, setsSvedHabits] = useState([]);
  const [page, setPage] = useState(0);
  const url: string = 'http://localhost:4000/habitdata';
  const [setTarget] = useIntersection(url, page, setPage, setsSvedHabits);
  return (
    <div className="w-full scrollbar-hide">
      <HabitWrapperVertical
        habitWrapperTitle="내가 찜한 습관"
        habitWrapperData={savedHabits}
      />
      <div ref={setTarget}></div>
    </div>
  );
}

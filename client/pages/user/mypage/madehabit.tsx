import { HabitWrapperVertical } from '../../../components/habitWrapperVertical';
import { useState } from 'react';
import { useIntersection } from '../../../hooks/useIntersection';

const MadeHabit = () => {
  const [madeHabits, setMadeHabits] = useState([]);
  const [page, setPage] = useState(0);
  const url: string = 'http://localhost:4000/habitdata';
  const [setTarget] = useIntersection(url, page, setPage, setMadeHabits);
  return (
    <div className="h-screen w-full overflow-y-scroll scrollbar-hide absolute">
      <HabitWrapperVertical
        habitWrapperTitle="내가 만든 습관"
        habitWrapperData={madeHabits}
      />
      <div ref={setTarget} className="w-full  h-16"></div>
    </div>
  );
}

export default MadeHabit
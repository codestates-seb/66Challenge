import { HabitWrapperVertical } from '../../../components/habitWrapperVertical';
import { useState, useEffect, useCallback } from 'react';
import { useIntersection } from '../../../hooks/useIntersection';

export default function MadeHabit() {
  const [madeHabits, setMadeHabits] = useState([]);
  const [page, setPage] = useState(0);
  const url = 'http://localhost:4000/habitdata';
  const [setTarget] = useIntersection(url, page, setPage, setMadeHabits);
  const getMyHabits = useCallback(async () => {
    setLoaded(false);
    const addHabits = habitWrapperData.slice((page - 1) * 10, 10 * page);
    if (addHabits.length === 0) {
      endRef.current = true;
    }
    preventRef.current = true;
    setMadeHabits([...madeHabits, ...addHabits]);
    setLoaded(true);
  }, [page]);
  useEffect(() => {
    getMyHabits();
  }, [page]);
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

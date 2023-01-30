import { HabitWrapperVertical } from '../../../components/habitWrapperVertical';
import { FC, useState } from 'react';
import { useIntersection } from '../../../hooks/useIntersection';
import { useAppSelector } from '../../../ducks/store';

const MadeHabit: FC = () => {
  const userId: number = useAppSelector((state) => state.loginIdentity.userId);
  const [madeHabits, setMadeHabits] = useState([]);
  const url: string = `${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}/habits/hosts?`;
  const [lastId, setLastId] = useState<number>(null);
  const size = 15;
  const type = 'habit';

  const [setTarget] = useIntersection(
    url,
    lastId,
    setLastId,
    setMadeHabits,
    size,
    type,
  );
  return (
    <div className="h-screen w-full overflow-y-scroll scrollbar-hide">
      <HabitWrapperVertical
        habitWrapperTitle="내가 만든 습관"
        habitWrapperData={madeHabits}
      />
      <div ref={setTarget} className="w-full h-16"></div>
    </div>
  );
};

export default MadeHabit;

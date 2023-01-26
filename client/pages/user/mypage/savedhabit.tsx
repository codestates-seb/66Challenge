import { HabitWrapperVertical } from '../../../components/habitWrapperVertical';
import { FC, useState } from 'react';
import { useIntersection } from '../../../hooks/useIntersection';
import { useAppSelector } from '../../../ducks/store';

const SavedHabit: FC = () => {
  const userId: number = useAppSelector((state) => state.loginIdentity.userId);
  const [savedHabits, setSavedHabits] = useState([]);
  const [page, setPage] = useState<number>(1);
  const url: string = `${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}/bookmarks?`;
  const [setTarget] = useIntersection(url, page, setPage, setSavedHabits);
  return (
    <div className="h-screen w-full overflow-y-scroll scrollbar-hide absolute">
      <HabitWrapperVertical
        habitWrapperTitle="내가 찜한 습관"
        habitWrapperData={savedHabits}
      />
      <div ref={setTarget} className="w-full h-16"></div>
    </div>
  );
};

export default SavedHabit;
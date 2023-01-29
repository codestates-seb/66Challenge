import { useState } from 'react';
import { HabitWrapperVertical } from '../../components/habitWrapperVertical';
import { useIntersection } from '../../hooks/useIntersection';
import { useAppSelector } from '../../ducks/store';

const HotList: React.FC = () => {
  const [habitWrapperData, setHabitWrapperData] = useState([]);
  const { userId } = useAppSelector((state) => state.loginIdentity);
  const url: string = `${
    process.env.NEXT_PUBLIC_SERVER_URL
  }/habits/sort/popularity?${userId ? 'userId=' + userId + '&' : ''}`;
  const [lastId, setLastId] = useState<number>(null);
  const size = 15;
  const type = 'habit';

  const [setTarget] = useIntersection(
    url,
    lastId,
    setLastId,
    setHabitWrapperData,
    size,
    type,
  );

  return (
    <div className="hotlist-container">
      <div>
        <HabitWrapperVertical
          habitWrapperTitle="많은 사람들이 형성중인 습관 🔥"
          habitWrapperData={habitWrapperData}
        />
        <div ref={setTarget}></div>
      </div>
    </div>
  );
};

export default HotList;

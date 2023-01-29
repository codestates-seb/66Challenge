import { useState } from 'react';
import { HabitWrapperVertical } from '../../components/habitWrapperVertical';
import { useIntersection } from '../../hooks/useIntersection';
import { useAppSelector } from '../../ducks/store';

const RecommendList: React.FC = () => {
  const [habitWrapperData, setHabitWrapperData] = useState([]);
  const { userId } = useAppSelector((state) => state.loginIdentity);
  const url: string = `${
    process.env.NEXT_PUBLIC_SERVER_URL
  }/habits/sort/recommend?${userId ? 'userId=' + userId + '&' : ''}`;

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
    <div className="recommendlist-container">
      <div>
        <HabitWrapperVertical
          habitWrapperTitle="관심도가 높은 습관들 🤔"
          habitWrapperData={habitWrapperData}
        />
        <div ref={setTarget}></div>
      </div>
    </div>
  );
};

export default RecommendList;

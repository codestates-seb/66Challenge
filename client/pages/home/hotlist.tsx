import { useState } from 'react';
import { HabitWrapperVertical } from '../../components/habitWrapperVertical';
import { useIntersectionPage } from '../../hooks/useIntersection';
import { useAppSelector } from '../../ducks/store';

const HotList: React.FC = () => {
  const [habitWrapperData, setHabitWrapperData] = useState([]);
  const [page, setPage] = useState(1);
  const { userId } = useAppSelector((state) => state.loginIdentity);
  const url: string = `${
    process.env.NEXT_PUBLIC_SERVER_URL
  }/habits/sort/popularity?${userId ? 'userId=' + userId + '&' : ''}`;

  const [setTarget] = useIntersectionPage(
    url,
    page,
    setPage,
    setHabitWrapperData,
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

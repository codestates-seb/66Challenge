import { useState } from 'react';
import { HabitWrapperVertical } from '../../components/habitWrapperVertical';
import { useIntersection } from '../../hooks/useIntersection';

const HotList: React.FC = () => {
  const [habitWrapperData, setHabitWrapperData] = useState([]);
  const [page, setPage] = useState(0);
  const url: string = 'http://localhost:4000/habitdata';

  const [setTarget] = useIntersection(url, page, setPage, setHabitWrapperData);

  return (
    <div className="hotlist-container">
      <div>
        <HabitWrapperVertical
          habitWrapperTitle="실시간 인기 습관"
          habitWrapperData={habitWrapperData}
        />
        <div ref={setTarget}></div>
      </div>
    </div>
  );
};

export default HotList;

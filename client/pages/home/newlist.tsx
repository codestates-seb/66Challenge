import { useState } from 'react';
import { HabitWrapperVertical } from '../../components/habitWrapperVertical';
import { useIntersection } from '../../hooks/useIntersection';

const NewList: React.FC = () => {
  const [habitWrapperData, setHabitWrapperData] = useState([]);
  const [page, setPage] = useState(0);
  const url: string = 'http://localhost:4000/habitdata';

  const [setTarget] = useIntersection(url, page, setPage, setHabitWrapperData);

  return (
    <div className="newlist-container">
      <div>
        <HabitWrapperVertical
          habitWrapperTitle="따끈따끈 새로운 습관"
          habitWrapperData={habitWrapperData}
        />
        <div ref={setTarget}></div>
      </div>
    </div>
  );
};

export default NewList;

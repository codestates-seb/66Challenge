import { useState } from 'react';
import { HabitWrapperVertical } from '../../components/habitWrapperVertical';
import { useIntersection } from '../../hooks/useIntersection';
import { useAppSelector } from '../../ducks/store';

const NewList: React.FC = () => {
  const [habitWrapperData, setHabitWrapperData] = useState([]);
  const [page, setPage] = useState(1);
  const { userId } = useAppSelector((state) => state.loginIdentity);
  const url: string = `${
    process.env.NEXT_PUBLIC_SERVER_URL
  }/habits/sort/newest?${userId ? 'userId=' + userId + '&' : ''}`;

  const [setTarget] = useIntersection(url, page, setPage, setHabitWrapperData);

  return (
    <div className="newlist-container">
      <div>
        <HabitWrapperVertical
          habitWrapperTitle="따끈따끈 새로운 습관 👶"
          habitWrapperData={habitWrapperData}
        />
        <div ref={setTarget}></div>
      </div>
    </div>
  );
};

export default NewList;

import { useRouter } from 'next/router';

const HabitDetail = () => {
  const router = useRouter();
  const habitId = router.query.habitId;

  return (
    <div className="habit-detail-container">
      <div className="habit-detail-top">
        <div>냥냥냥</div>
        <div></div>
      </div>
      <div className="habit-detail-middle"></div>
      <div className="habit-detail-bottom"></div>
    </div>
  );
};

export default HabitDetail;

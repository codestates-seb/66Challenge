import { useRouter } from 'next/router';

export default function HabitStatisticDetail() {
  const router = useRouter();
  const habitId = router.query.habitId;

  return (
    <div className="habit-detail-container px-5">
      <div className="habit-detail-top">
        <div>정해진 것이 없다.</div>
        <div></div>
      </div>
      <div className="habit-detail-middle"></div>
      <div className="habit-detail-bottom"></div>
    </div>
  );
}

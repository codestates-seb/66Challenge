import { useRouter } from 'next/router';
import { AuthArticle } from '../../../../components/authArticle';

export default function HabitAuthDetail() {
  const router = useRouter();
  const habitId = router.query.habitId;

  return (
    <div className="habit-detail-review-container px-5">
      <div className="habit-detail-review-top">
        <AuthArticle />
      </div>
      <div className="habit-detail-review-middle"></div>
      <div className="habit-detail-review-bottom"></div>
    </div>
  );
}

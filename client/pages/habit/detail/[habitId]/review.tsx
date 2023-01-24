import { useRouter } from 'next/router';
import { ReviewArticle } from '../../../../components/reviewArticle';

export default function HabitDetailReview() {
  const router = useRouter();
  const habitId = router.query.habitId;

  return (
    <div className="habit-detail-review-container px-5">
      <div className="habit-detail-review-top">
        <ReviewArticle />
      </div>
      <div className="habit-detail-review-middle"></div>
      <div className="habit-detail-review-bottom"></div>
    </div>
  );
}

import { useRouter } from 'next/router';
import { ReviewArticle } from '../../../../components/reviewArticle';
import { useState } from 'react';
import { useIntersection } from '../../../../hooks/useIntersection';
import type { reviewArticleProps } from '../../../../components/reviewArticle';

export default function HabitDetailReview() {
  const router = useRouter();
  const habitId = router.query.habitId;
  const url: string = `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/reviews?`;
  const [reviewData, setReviewData] = useState<Array<reviewArticleProps>>([]);
  const [page, setPage] = useState(1);
  const [setTarget] = useIntersection(url, page, setPage, setReviewData);

  return (
    <div className="habit-detail-review-container px-5">
      {reviewData.map((el) => {
        return (
          <div className="habit-detail-review-wrapper" key={el.reviewId}>
            <ReviewArticle {...el} habitId={+habitId} />
          </div>
        );
      })}
      <div ref={setTarget}></div>
    </div>
  );
}

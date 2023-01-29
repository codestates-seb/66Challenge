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
  const [lastId, setLastId] = useState<number>(null);
  const size = 15;
  const type = 'review';
  const [setTarget] = useIntersection(
    url,
    lastId,
    setLastId,
    setReviewData,
    size,
    type,
  );

  return (
    <div className="habit-detail-review-container px-5 pt-5">
      {reviewData.length !== 0 ? (
        reviewData.map((el) => {
          return (
            <div className="habit-detail-review-wrapper" key={el.reviewId}>
              <ReviewArticle {...el} habitId={+habitId} />
            </div>
          );
        })
      ) : (
        <div className="h-screen -mt-[116px] -mb-[100px] flex justify-center items-center">
          아직 등록된 후기가 없습니다.
        </div>
      )}
      <div ref={setTarget}></div>
    </div>
  );
}

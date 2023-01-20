import { useRouter } from 'next/router';
import { AuthArticle } from '../../../../components/authArticle';
import { useState, useEffect } from 'react';
import { useIntersection } from '../../../../hooks/useIntersection';
import type { authArticleProps } from '../../../../components/authArticle';

export default function HabitDetailAuth() {
  const router = useRouter();
  const habitId = router.query.habitId;
  const url: string = '';
  const [authData, setAuthData] = useState<Array<authArticleProps>>([]);
  const [page, setPage] = useState(0);
  const [setTarget] = useIntersection(url, page, setPage, setAuthData);

  return (
    <div className="habit-detail-review-container px-5">
      {authData.map((el) => {
        return (
          <div className="habit-detail-review-wrapper">
            <AuthArticle {...el} />
          </div>
        );
      })}
      <div ref={setTarget}></div>
    </div>
  );
}

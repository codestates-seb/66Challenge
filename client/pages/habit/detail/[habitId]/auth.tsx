import { useRouter } from 'next/router';
import { AuthArticle } from '../../../../components/authArticle';
import { useState } from 'react';
import { useIntersection } from '../../../../hooks/useIntersection';
import type { authArticleProps } from '../../../../components/authArticle';

export default function HabitDetailAuth() {
  const router = useRouter();
  const habitId = router.query.habitId;
  const url: string = `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/auths?`;
  const [authData, setAuthData] = useState<Array<authArticleProps>>([]);
  const [page, setPage] = useState(1);
  const [setTarget] = useIntersection(url, page, setPage, setAuthData);

  return (
    <div className="habit-detail-auth-container px-5">
      {authData.map((el) => {
        return (
          <div className="habit-detail-auth-wrapper" key={el.authId}>
            <AuthArticle {...el} habitId={+habitId} />
          </div>
        );
      })}
      <div ref={setTarget}></div>
    </div>
  );
}

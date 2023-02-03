import { useRouter } from 'next/router';
import { AuthArticle } from '../../../../components/authArticle';
import { useState } from 'react';
import { useIntersection } from '../../../../hooks/useIntersection';
import type { authArticleProps } from '../../../../components/authArticle';
interface IpropsValue {
  authImageUrl: string;
  authId: number;
  body: string;
}
export default function HabitDetailAuth({
  authImageUrl,
  authId,
  body,
}: IpropsValue) {
  const router = useRouter();
  const habitId = router.query.habitId;
  const url: string = `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/auths/?`;
  const [authData, setAuthData] = useState<Array<authArticleProps>>([]);
  const [lastId, setLastId] = useState<number>(null);
  const size = 15;
  const type = 'auth';
  const [setTarget] = useIntersection(
    url,
    lastId,
    setLastId,
    setAuthData,
    size,
    type,
  );

  return (
    <div className="habit-detail-auth-container px-5 pt-5 min-h-screen flex items-center justify-center">
      <div>
        {authData.length !== 0 ? (
          authData.map((el) => {
            return (
              <div className="habit-detail-auth-wrapper" key={el.authId}>
                <AuthArticle {...el} habitId={+habitId} />
              </div>
            );
          })
        ) : (
          <div className="h-full -mt-[116px] -mb-[100px] flex justify-center items-center">
            아직 등록된 인증글이 없습니다.
          </div>
        )}
      </div>
      <div ref={setTarget}></div>
    </div>
  );
}

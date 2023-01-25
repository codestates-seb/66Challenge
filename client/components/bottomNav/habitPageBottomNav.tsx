import { useEffect, useMemo, useState } from 'react';
import { StartHabitBottomNav } from './startHabitBottomNav';
import { EditHabitBottomNav } from './editHabitBottomNav';
import { ReviewHabitBottomNav } from './reviewHabitBottomNav';
import { useRouter } from 'next/router';
import { getHabitDetail } from '../../module/habitFunctionMoudules';
import { useAppSelector } from '../../ducks/store';
interface IhabitDetail {
  hostUserId?: number;
  isBooked?: boolean;
  challengeStatus?: string;
  habitId?: number;
}
export function HabitPageBottomNav() {
  //user가 접근한 습관에 대해 시작 여부에 따른 분배
  const router = useRouter();
  const { userId, isLogin } = useAppSelector((state) => state.loginIdentity);
  const [habitInfo, setHabitInfo] = useState<IhabitDetail>({});
  const id = router.query.habitId;

  useEffect(() => {
    if (!router.isReady) {
      return;
    }

    async function axiosFunc() {
      const response = await getHabitDetail({
        habitId: Number(id),
        userId,
      });
      const { hostUserId, isBooked, habitId } = response.overview;
      const { challengeStatus } = response.detail;
      setHabitInfo({ hostUserId, isBooked, challengeStatus, habitId });
    }
    axiosFunc();
  }, [router.isReady]);
  if (
    isLogin === false ||
    (habitInfo.hostUserId !== userId && habitInfo.challengeStatus !== 'SUCCESS')
  ) {
    return (
      <StartHabitBottomNav
        habitId={habitInfo.habitId}
        userId={userId}
        isLogin={isLogin}
        challengeStatus={habitInfo.challengeStatus}
        isBooked={habitInfo.isBooked}
      />
    );
  } else if (
    isLogin === true &&
    habitInfo.hostUserId === userId &&
    habitInfo.challengeStatus !== 'SUCCESS'
  ) {
    return (
      <EditHabitBottomNav
        habitId={habitInfo.habitId}
        userId={userId}
        challengeStatus={habitInfo.challengeStatus}
      />
    );
  } else {
    return <ReviewHabitBottomNav habitId={habitInfo.habitId} userId={userId} />;
  }
}

import { MainBottomNav } from './mainBottomNav';
import { HabitPageBottomNav } from './habitPageBottomNav';
import { useRouter } from 'next/router';

export function BottomNav() {
  //라우팅 된 페이지 경로에 따른 조건 부 렌더링 작성해야함.
  const router = useRouter();
  const param = router.pathname;
  //경로 조건에 따른 nav bar 다르게 해야함. 페이지 작성 다되고나면 분할 예정
  // 로그인 페이지 회원가입 페이지는 nav bar 존재하지 않는다.
  if (
    param === '/' ||
    param === '/search' ||
    param === '/auth' ||
    param === '/post' ||
    param === '/user'
  ) {
    return <MainBottomNav param={param} />;
  } else {
    return <HabitPageBottomNav />;
  }
}

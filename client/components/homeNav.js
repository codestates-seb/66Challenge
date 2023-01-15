import Link from 'next/link';
import { useRouter } from 'next/router';

export const HomeNav = () => {
  const router = useRouter();
  const pathname = router.pathname;

  const homeNav = [
    {
      navTitle: '홈',
      navTo: '/',
    },
    {
      navTitle: '신규',
      navTo: '/home/newlist',
    },
    {
      navTitle: '인기',
      navTo: '/home/hotlist',
    },
    {
      navTitle: '추천',
      navTo: '/home/recommendlist',
    },
  ];

  const activeNav = 'border-b-[3px] border-mainColor';

  if (
    ['/', '/home/newlist', '/home/hotlist', '/home/recommendlist'].includes(
      pathname,
    )
  ) {
    return (
      <nav className="home-navigation-bar border-b b-borderColor sticky top-[56px] z-50 bg-white">
        <ul className="home-navigation-list flex h-[40px] px-[20px]">
          {homeNav.map((el, idx) => {
            return (
              <li
                className={`${
                  pathname === el.navTo ? activeNav : null
                } home-navigation-element w-full text-base flex justify-center items-center`}
                key={idx}
              >
                <Link
                  className={`${
                    pathname === el.navTo ? 'pt-[3px] font-semibold' : null
                  }`}
                  href={el.navTo}
                >
                  {el.navTitle}
                </Link>
              </li>
            );
          })}
        </ul>
      </nav>
    );
  } else {
    return;
  }
};

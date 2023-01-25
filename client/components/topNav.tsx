import Link from 'next/link';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import { homeNavData, detailNavFunc } from '../data/navData';

export const TopNav: React.FC = () => {
  const router = useRouter();
  const { pathname, query } = router;

  const activeNav: string = 'border-b-[3px] border-mainColor';
  const detailNavData = detailNavFunc(query.habitId);

  const [page, setPage] = useState('');
  useEffect(() => {
    if (
      ['/', '/home/newlist', '/home/hotlist', '/home/recommendlist'].includes(
        pathname,
      )
    ) {
      setPage('home');
    } else if (pathname.includes('/habit/detail/[habitId]')) {
      setPage('detail');
    } else {
      setPage('');
    }
  }, [pathname]);
  console.log(pathname);

  if (page.length) {
    return (
      <nav className="home-navigation-bar border-b b-borderColor sticky top-[56px] z-50 bg-white">
        <ul className="home-navigation-list flex h-[40px] px-[20px]">
          {(page === 'home' ? homeNavData : detailNavData).map((el, idx) => {
            return (
              <li
                className={`${
                  pathname === el.pathname ? activeNav : null
                } home-navigation-element w-full text-base flex justify-center items-center`}
                key={idx}
              >
                <Link
                  className={`w-full text-center ${
                    pathname === el.pathname ? 'pt-[3px] font-semibold' : null
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

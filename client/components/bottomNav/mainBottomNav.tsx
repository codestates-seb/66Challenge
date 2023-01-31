import { useEffect, useState } from 'react';
import {
  AiOutlineHome,
  AiOutlineSearch,
  AiOutlineCamera,
  AiOutlineUser,
  AiOutlineLogin,
} from 'react-icons/ai';
import { IoIosAddCircleOutline } from 'react-icons/io';
import { useRouter } from 'next/router';
import { useAppSelector } from '../../ducks/store';

interface ImenusValue {
  name: string;
  icon: JSX.Element;
  dis: string;
  link: string;
}

export function MainBottomNav({ param }) {
  const [active, setActive] = useState(0);
  const [menus, setMenus] = useState<ImenusValue[]>([]);
  const router = useRouter();
  const { isLogin } = useAppSelector((state) => state.loginIdentity);
  useEffect(() => {
    let currentActive: number;
    if (param === 'home') {
      currentActive = 0;
    } else if (param === 'search') {
      currentActive = 1;
    } else if (param === 'auth') {
      currentActive = 2;
    } else if (param === 'post') {
      currentActive = 3;
    } else if (param === 'mypage') {
      currentActive = 4;
    }
    setActive(currentActive);
  }, [param]);
  useEffect(() => {
    let menuArr = [
      {
        name: '홈',
        icon: <AiOutlineHome />,
        dis: 'translate-x-0',
        link: '/',
      },
      {
        name: '습관검색',
        icon: <AiOutlineSearch />,
        dis: 'translate-x-14',
        link: '/habit/search',
      },
      {
        name: '인증하기',
        icon: <AiOutlineCamera />,
        dis: 'translate-x-28',
        link: '/auth',
      },
      {
        name: '습관등록',
        icon: <IoIosAddCircleOutline />,
        dis: 'translate-x-42',
        link: '/habit/post',
      },
    ];
    if (isLogin === true) {
      setMenus([
        ...menuArr,
        {
          name: '내 정보',
          icon: <AiOutlineUser />,
          dis: 'translate-x-56',
          link: '/user/mypage',
        },
      ]);
    } else {
      setMenus([
        ...menuArr,
        {
          name: '로그인',
          icon: <AiOutlineLogin />,
          dis: 'translate-x-56',
          link: '/user/login',
        },
      ]);
    }
  }, [isLogin]);
  const onClickHandle = (link: string) => {
    //버튼 별 페이지 경로 작성 해야함.

    const linkBoolean: boolean = menus.slice(2).some((el) => {
      return el.link.includes(link);
    });
    if (isLogin === false && linkBoolean === true && link !== '/') {
      router.push('/user/login');
    } else {
      router.push(link);
    }
  };
  return (
    <div className="flex bg-mainColor h-[50px] px-6 w-full   fixed bottom-0 min-w-[360px] max-w-[460px] justify-center">
      <ul className="flex relative items-center  justify-center">
        <span
          className={`bg-subColor duration-500 ${menus[active]?.dis} border-4 border-white h-14 w-14 absolute -top-7 -left-0 rounded-full `}
        ></span>
        {menus.map((menu, i) => {
          return (
            <li key={i} className="w-14">
              <div
                className="flex flex-col text-center pt-6 cursor-pointer"
                onClick={() => {
                  setActive(i);
                  onClickHandle(menu.link);
                }}
              >
                <span
                  className={`flex justify-center text-xl text-iconColor  duration-500 z-50 ${
                    i === active && '-mt-11 text-iconColor '
                  }`}
                >
                  {menu.icon}
                </span>
                <span
                  className={` ${
                    active === i
                      ? 'translate-y-4 duration-700 opacity-100 text-sm text-iconColor text-base'
                      : 'opacity-0 translate-y-10 text-iconColor text-sm text-base'
                  }`}
                >
                  {menu.name}
                </span>
              </div>
            </li>
          );
        })}
      </ul>
    </div>
  );
}

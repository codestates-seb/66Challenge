import { useEffect, useState } from 'react';
import {
  AiOutlineHome,
  AiOutlineSearch,
  AiOutlineCamera,
  AiOutlineUser,
} from 'react-icons/ai';
import { IoIosAddCircleOutline } from 'react-icons/io';
import { useRouter } from 'next/router';
export function MainBottomNav({ param }) {
  const [active, setActive] = useState(0);
  const router = useRouter();
  //로그인 여부 판단 할 근거가 필요함
  const [isLogin, setIsLogin] = useState(true);
  const menus = [
    { name: 'Home', icon: <AiOutlineHome />, dis: 'translate-x-0', link: '/' },
    {
      name: 'Search',
      icon: <AiOutlineSearch />,
      dis: 'translate-x-14',
      link: '/habit/search',
    },
    {
      name: 'Auth',
      icon: <AiOutlineCamera />,
      dis: 'translate-x-28',
      link: '/auth',
    },
    {
      name: 'Post',
      icon: <IoIosAddCircleOutline />,
      dis: 'translate-x-42',
      link: '/habit/post',
    },
    {
      name: 'MyPage',
      icon: <AiOutlineUser />,
      dis: 'translate-x-56',
      link: '/user/mypage',
    },
  ];

  useEffect(() => {
    let currentActive;
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
  }, []);

  const onClickHandle = (link) => {
    //버튼 별 페이지 경로 작성 해야함.

    const linkBoolean = menus.slice(2).some((el) => {
      return el.link.includes(link);
    });
    if (isLogin === false && linkBoolean === true) {
      router.push('/user/login');
    } else {
      router.push(link);
    }
  };
  return (
    <div className="flex bg-mainColor h-[50px] px-6  w-full fixed bottom-0 min-w[300px] justify-center">
      <ul className="flex relative items-center  justify-center">
        <span
          className={`bg-subColor duration-500 ${menus[active].dis} border-4 border-white h-14 w-14 absolute -top-7 -left-0 rounded-full `}
        ></span>
        {menus.map((menu, i) => {
          return (
            <li key={i} className="w-14">
              <div
                className="flex flex-col text-center pt-6"
                onClick={() => setActive(i)}
              >
                <sapn
                  className={`flex justify-center text-xl text-iconColor cursor-pointer duration-500 z-50 ${
                    i === active && '-mt-11 text-iconColor '
                  }`}
                  onClick={() => onClickHandle(menu.link)}
                >
                  {menu.icon}
                </sapn>
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

import { useEffect, useState, useCallback } from 'react';
import {
  AiOutlineHome,
  AiOutlineSearch,
  AiOutlineCamera,
  AiOutlineUser,
} from 'react-icons/ai';
import { IoIosAddCircleOutline } from 'react-icons/io';
import { useRouter } from 'next/router';
export function MainBottomNav({ param }) {
  const menus = [
    { name: 'Home', icon: <AiOutlineHome />, dis: 'translate-x-0' },
    { name: 'Search', icon: <AiOutlineSearch />, dis: 'translate-x-14' },
    { name: 'Auth', icon: <AiOutlineCamera />, dis: 'translate-x-28' },
    { name: 'Post', icon: <IoIosAddCircleOutline />, dis: 'translate-x-42' },
    { name: 'MyPage', icon: <AiOutlineUser />, dis: 'translate-x-56' },
  ];

  const [active, setActive] = useState(0);
  const router = useRouter();
  useEffect(() => {
    let currentActive;
    if (param === '/') {
      currentActive = 0;
    } else if (param === '/search') {
      currentActive = 1;
    } else if (param === '/auth') {
      currentActive = 2;
    } else if (param === '/post') {
      currentActive = 3;
    } else if (param === '/user') {
      currentActive = 4;
    }
    setActive(currentActive);
  }, []);

  const onClickHandle = () => {
    //버튼 별 페이지 경로 작성 해야함.
  };
  return (
    <div className="flex bg-mainColor h-[3rem] px-6  w-full fixed bottom-0 min-w[300px] justify-center">
      <ul className="flex relative items-center  justify-center">
        <span
          className={`bg-subColor duration-500 ${menus[active].dis} border-4 border-white h-14 w-14 absolute -top-7 -left-0 rounded-full `}
        ></span>
        {menus.map((menu, i) => {
          return (
            <li key={i} className="w-14">
              <a
                className="flex flex-col text-center pt-6"
                onClick={() => setActive(i)}
              >
                <sapn
                  className={`flex justify-center text-xl cursor-pointer duration-500 z-50 ${
                    i === active && '-mt-10 text-textColor '
                  }`}
                  onClick={onClickHandle}
                >
                  {menu.icon}
                </sapn>
                <span
                  className={` ${
                    active === i
                      ? 'translate-y-2 duration-700 opacity-100 text-textColor'
                      : 'opacity-0 translate-y-10 text-textColor'
                  }`}
                >
                  {menu.name}
                </span>
              </a>
            </li>
          );
        })}
      </ul>
    </div>
  );
}

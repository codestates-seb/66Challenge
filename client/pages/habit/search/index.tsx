import { useState, useEffect, useCallback } from 'react';
import { AiOutlineSearch } from 'react-icons/ai';
import { HabitWrapperVertical } from '../../../components/habitWrapperVertical';
import { useIntersection } from '../../../hooks/useIntersection';
import { MdExpandMore } from 'react-icons/md';
import { useRouter } from 'next/router';
import { useAppSelector } from '../../../ducks/store';
interface IarrowValue {
  className: string;
  boolean: boolean;
}
interface IcategoryList {
  categoryId: number;
  name: string;
}
interface IhabitValue {
  body: string;
  habitId: number;
  hostUserId: number;
  isBooked: boolean;
  score: number;
  thumbImgUrl: null | string;
  title: string;
}
export default function SearchHabit() {
  const router = useRouter();

  const [arrowDirection, setArrowDirection] = useState<IarrowValue>({
    className: '',
    boolean: false,
  });
  const upArrow: string = 'rotate-180 duration-500';
  const downArrow: string = 'rotate-0';
  const arrowDirectionHandle = (): void => {
    if (arrowDirection.boolean === false) {
      setArrowDirection({ className: upArrow, boolean: true });
    } else {
      setArrowDirection({ className: downArrow, boolean: false });
    }
  };
  const [search, setSearch] = useState('');
  const [searchHabits, setSearchHabits] = useState<IhabitValue[]>([]);
  const [doing, setDoing] = useState('all');
  const [page, setPage] = useState(1);
  const [active, setActive] = useState(0);
  const [url, setUrl] = useState(
    `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/search?`,
  );
  const [setTarget] = useIntersection(url, page, setPage, setSearchHabits);
  const { userId } = useAppSelector((state) => state.loginIdentity);
  const categoryList: IcategoryList[] = [
    { categoryId: 0, name: '전체' },
    { categoryId: 1, name: '운동' },
    { categoryId: 2, name: '식습관' },
    { categoryId: 3, name: '학습' },
    { categoryId: 4, name: '일상생활' },
    { categoryId: 5, name: '자기관리' },
    { categoryId: 6, name: '환경' },
    { categoryId: 7, name: '취미' },
    { categoryId: 8, name: '기타' },
  ];
  const onSearchHandle = useCallback((e) => {
    setSearch(e.target.value);
  }, []);
  const onSubmitHandle = () => {
    //키워드 비동기 함수 호출
    if (search === '') {
      setDoing('all');
      setPage(1);
      setSearchHabits([]);
      if (userId === null) {
        setUrl(`${process.env.NEXT_PUBLIC_SERVER_URL}/habits/search?`);
      }
      setUrl(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/search?userId=${userId}&`,
      );
    } else {
      setDoing('search');
      setPage(1);
      setSearchHabits([]);
      if (userId === null) {
        setUrl(
          `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/search?keyword=${search}&`,
        );
      }
      setUrl(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/search?keyword=${search}&userId=${userId}&`,
      );
    }
  };
  useEffect(() => {
    if (active !== 0) {
      setDoing('category');
      setPage(1);
      setSearchHabits([]);
      if (userId === null) {
        setUrl(
          `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/search/${active}?`,
        );
      } else {
        setUrl(
          `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/search/${active}?userId=${userId}&`,
        );
      }
    } else {
      setDoing('all');
      setPage(1);
      setSearchHabits([]);
      setUrl(`${process.env.NEXT_PUBLIC_SERVER_URL}/habits/search?`);
    }
  }, [active]);
  useEffect(() => {
    const categoryId: number | undefined =
      router.query.categoryId !== undefined
        ? Number(router.query.categoryId)
        : undefined;
    if (categoryId !== undefined) {
      setActive(categoryId);
    }
  }, []);
  return (
    <div className="w-full min-w-[360px] max-w-[460px] overflow-y-scroll scrollbar-hide flex flex-col items-center p-4 pb-0 min-h-screen">
      <form className="w-4/5 flex  justify-center mt-3 mb-6 items-center relative ">
        <input
          className="w-full border border-mainColor rounded-full text-sm h-[40px]  pl-3 pr-[40px] focus:border-subColor outline-none focus:shadow-[0_0_0.5rem] focus:shadow-subColor focus:outline-[1px] focus:outline-[#379fef];"
          autoComplete="off"
          placeholder="찾고자 하는 습관을 검색해주세요."
          value={search}
          onChange={(e) => onSearchHandle(e)}
          required
        />
        <AiOutlineSearch
          size="22"
          className="absolute right-3"
          onClick={onSubmitHandle}
        />
      </form>
      <div
        className="flex items-center w-full mb-3"
        onClick={arrowDirectionHandle}
      >
        <span className="text-base font-semibold mr-2 mt-[2px]">카테고리</span>
        <MdExpandMore size={20} className={arrowDirection.className} />
      </div>
      <div
        className={
          arrowDirection.boolean === false
            ? 'hidden'
            : 'grid grid-cols-3 scrollbar-hide p-2 gap-2 border-y w-full  border-mainColor  items-center justify-center'
        }
      >
        {categoryList.map((category, index) => {
          return (
            <div className=" w-full m-1" key={index}>
              <span
                className={`${
                  active === index ? 'bg-subColor' : 'bg-mainColor'
                } w-full h-full rounded-full text-iconColor flex duration-300  justify-center items-center text-sm py-[5px]`}
                onClick={() => {
                  setActive(category.categoryId);
                }}
              >
                {categoryList[index].name}
              </span>
            </div>
          );
        })}
      </div>
      <div className="-mx-5">
        <HabitWrapperVertical
          habitWrapperTitle={
            doing === 'all'
              ? '전체 습관'
              : doing === 'category'
              ? `${categoryList[active].name} 습관`
              : `${search}에 대한 습관`
          }
          habitWrapperData={searchHabits}
        />
      </div>
      <div ref={setTarget} className="w-full"></div>
    </div>
  );
}

import { useForm } from 'react-hook-form';
import { useState, useEffect, useCallback } from 'react';
import { AiOutlineSearch } from 'react-icons/ai';
import { HabitWrapperVertical } from '../../../components/habitWrapperVertical';
import { useIntersection } from '../../../hooks/useIntersection';
import { MdExpandMore } from 'react-icons/md';
import { LoadingIndicator } from '../../../components/loadingIndicator';

import { useRouter } from 'next/router';
import { ScrollToTopButton } from '../../../components/scrollToTopButton';
export default function SearchHabit() {
  const router = useRouter();

  const [arrowDirection, setArrowDirection] = useState({
    className: '',
    boolean: router.query.categoryId !== undefined ? true : false,
  });
  const upArrow = 'rotate-180 duration-500';
  const downArrow = 'rotate-0';
  const arrowDirectionHandle = () => {
    if (arrowDirection.boolean === false) {
      setArrowDirection({ className: upArrow, boolean: true });
    } else {
      setArrowDirection({ className: downArrow, boolean: false });
    }
  };
  const [search, setSearch] = useState('');
  const [searchHabits, setSearchHabits] = useState([]);
  const [doing, setDoing] = useState('all');
  const [page, setPage] = useState(0);
  const [active, setActive] = useState(
    router.query.categoryId !== undefined ? router.query.categoryId : 0,
  );
  const [url, setUrl] = useState('http://localhost:4000/habitdata');
  const [setTarget] = useIntersection(url, page, setPage, setSearchHabits);
  const categoryListKor = [
    '전체',
    '운동',
    '식습관',
    '학습',
    '일상생활',
    '자기관리',
    '환경',
    '취미',
    '기타',
  ];
  const categoryList = [
    { categoryId: '0', name: 'all' },
    { categoryId: '1', name: 'health' },
    { categoryId: '2', name: 'eat' },
    { categoryId: '3', name: 'study' },
    { categoryId: '4', name: 'life' },
    { categoryId: '5', name: 'self' },
    { categoryId: '6', name: 'environment' },
    { categoryId: '7', name: 'hobby' },
    { categoryId: '8', name: 'extra' },
  ];
  console.log('categoryId : ', router.query.categoryId);
  console.log('active : ', active);
  const onSearchHandle = useCallback((e) => {
    setSearch(e.target.value);
  }, []);
  const onSubmitHandle = () => {
    //키워드 비동기 함수 호출
    if (search === '') {
      setDoing('all');
      setPage(0);
      setSearchHabits([]);
      setUrl('http://localhost:4000/habitdata');
    } else {
      setDoing('search');
      setPage(0);
      setSearchHabits([]);
      setUrl(`http://localhost:4000/categorydata/${search}`);
    }
  };
  useEffect(() => {
    if (active !== '0') {
      setDoing('category');
      setPage(0);
      setSearchHabits([]);
      setUrl(`http://localhost:4000/categorydata/${active}`);
    } else {
      setDoing('all');
      setPage(0);
      setSearchHabits([]);
      setUrl('http://localhost:4000/habitdata');
    }
  }, [active]);

  return (
    <div className=" w-full overflow-y-scroll scrollbar-hide absolute flex flex-col items-center p-4 pb-[100px]">
      <form className="w-4/5 flex justify-center mt-3 mb-6 items-center relative">
        <input
          className="w-full border border-mainColor rounded-full text-sm h-[40px] pl-3 pr-[40px] focus:border-subColor outline-none focus:shadow-[0_0_0.5rem] focus:shadow-subColor focus:outline-[1px] focus:outline-[#379fef];"
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
        className="flex items-center w-full mb-3 pl-4"
        onClick={arrowDirectionHandle}
      >
        <span className="text-base font-semibold mr-2 mt-[2px]">카테고리</span>
        <MdExpandMore size={20} className={arrowDirection.className} />
      </div>
      <div
        className={
          arrowDirection.boolean === false
            ? 'hidden'
            : 'flex w-full flex-wrap  scrollbar-hide p-2 border-y  border-mainColor  items-center justify-center'
        }
      >
        {categoryList.map((category, index) => {
          return (
            <div className=" w-[100px] m-1" key={index}>
              <span
                className={`${
                  active === String(index) ? 'bg-subColor' : 'bg-mainColor'
                } w-full h-full rounded-full text-iconColor flex duration-300  justify-center items-center text-sm py-[5px]`}
                onClick={() => {
                  setActive(category.categoryId);
                }}
              >
                {categoryListKor[index]}
              </span>
            </div>
          );
        })}
      </div>
      <div>
        {searchHabits.length === 0 ? (
          <LoadingIndicator />
        ) : (
          <HabitWrapperVertical
            habitWrapperTitle={
              doing === 'all'
                ? '전체 습관'
                : doing === 'category'
                ? `${categoryListKor[active]} 습관`
                : `${search}에 대한 습관`
            }
            habitWrapperData={searchHabits}
          />
        )}
      </div>
      <div ref={setTarget} className="w-full  h-16"></div>
    </div>
  );
}

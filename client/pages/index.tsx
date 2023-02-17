import { SlideBanner } from '../components/slideBanner';
import { slideData, subSlideData, categoryData } from '../data/homeStaticData';
import { useRouter } from 'next/router';
import { HabitWrapperHorizontal } from '../components/habitWrapperHorizontal';
import Image from 'next/image';
import React from 'react';
import { useAppSelector } from '../ducks/store';
import { Footer } from '../components/footer';
import { useState, useEffect, useCallback } from 'react';
import { getHabitsInHome } from '../module/habitFunctionMoudules';
import { withHead } from '../components/layout/withHead';
interface habitsType {
  body: string;
  habitId: number;
  hostUserId: number;
  isBooked: boolean;
  score: number;
  thumbImgUrl: string;
  title: string;
}

const Home: React.FC = () => {
  const router = useRouter();
  const cateIconClick = (categoryId: string) => {
    router.push({
      pathname: '/habit/search',
      query: {
        categoryId,
      },
    });
  };
  const { userId } = useAppSelector((state) => state.loginIdentity);
  const [habitsInHome1, setHabitsInHome1] = useState<Array<habitsType>>([]);
  const [habitsInHome2, setHabitsInHome2] = useState<Array<habitsType>>([]);

  useEffect(() => {
    getHabitsInHome({
      userId,
      type: 'popularity',
      page: '1',
      size: '10',
    }).then((data) => setHabitsInHome1(data));
    getHabitsInHome({
      userId,
      type: 'recommend',
      page: '1',
      size: '10',
    }).then((data) => setHabitsInHome2(data));
    return openScroll();
  }, []);

  const openScroll = useCallback(() => {
    if (document.body.style.overflow === 'hidden') {
      document.body.style.removeProperty('overflow');
    }
  }, []);
  return (
    <div
      className="-mb-[50px]"
      onWheel={() => {
        openScroll();
      }}
    >
      <SlideBanner bannerCont={slideData} t={2000} pagination={true} />
      <div className="main-category px-[20px]">
        <ul className="main-category-list grid grid-cols-4 my-[20px]">
          {categoryData.map((el, idx) => {
            return (
              <li
                className="main-category-item px-[9px] mt-[6px]"
                onClick={(_) => cateIconClick(el.categoryId)}
                key={el.cateTitle}
              >
                <div className="main-category-icon m-auto w-[70px] h-[70px] bg-slate-50 cursor-pointer rounded-xl flex justify-center items-center">
                  <Image
                    className="w-[50px] h-[50px]"
                    src={el.cateImgUrl}
                    alt="category icon"
                    width={500}
                    height={500}
                  />
                </div>
                <div className="main-category-title text-sm mt-[6px] mb-[13px] font-medium text-center">
                  {el.cateTitle}
                </div>
              </li>
            );
          })}
        </ul>
      </div>
      <div className="border-t-[10px] border-borderColor">
        <HabitWrapperHorizontal
          habitWrapperTitle="실시간 인기 습관"
          habitWrapperData={habitsInHome1}
        />
      </div>
      <div className="py-[20px]">
        <SlideBanner
          bannerCont={subSlideData}
          t={4000}
          pagination={false}
          maxHeight="[&>div]:max-h-[200px]"
        />
      </div>
      <div>
        <HabitWrapperHorizontal
          habitWrapperTitle="나의 또래가 많이하는 습관"
          habitWrapperData={habitsInHome2}
        />
      </div>
      <Footer />
    </div>
  );
};

export default withHead(
  Home,
  '66일 좋은 습관 만들기',
  '66일 동안 습관 만들기! 좋은 습관 만들고싶어? 66일이면 충분해. 66일동안 너 자신을 증명해봐. 야, 너두 할 수 있어',
  '',
  '/image/logo.svg',
);

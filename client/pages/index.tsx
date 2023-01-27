import { SlideBanner } from '../components/slideBanner';
import {
  slideData,
  subSlideData,
  categoryData,
  habitWrapperData,
} from '../data/homeStaticData';
import { useRouter } from 'next/router';
import { HabitWrapperHorizontal } from '../components/habitWrapperHorizontal';
import Image from 'next/image';
import React from 'react';
import { Footer } from '../components/footer';

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

  return (
    <div className="-mb-[50px]">
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
          habitWrapperData={habitWrapperData}
        />
      </div>
      <div className="py-[20px]">
        <SlideBanner bannerCont={subSlideData} t={4000} pagination={false} />
      </div>
      <div>
        <HabitWrapperHorizontal
          habitWrapperTitle="20대 여성이라면 필수!"
          habitWrapperData={habitWrapperData}
        />
      </div>
      <Footer />
    </div>
  );
};

export default Home;

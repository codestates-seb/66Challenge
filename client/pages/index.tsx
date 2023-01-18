import { SlideBanner } from '../components/slideBanner';
import Iframe from 'react-iframe';
import {
  slideData,
  categoryData,
  habitWrapperData,
} from '../data/homeStaticData';
import { useRouter } from 'next/router';
import { HabitWrapperHorizontal } from '../components/habitWrapperHorizontal';
import Image from 'next/image';
import React from 'react';

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
    <div>
      <SlideBanner bannerCont={slideData} t={2000} />
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
      <div className="main-video w-full h-full">
        <Iframe
          className="w-full min-h-[250px]"
          url="https://www.youtube.com/embed/cdZZpaB2kDM"
          title="YouTube video player"
          frameBorder={0}
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
          allowFullScreen
        ></Iframe>
      </div>
      <div>
        <HabitWrapperHorizontal
          habitWrapperTitle="실시간 인기 습관"
          habitWrapperData={habitWrapperData}
        />
        <HabitWrapperHorizontal
          habitWrapperTitle="20대 여성이라면 필수!"
          habitWrapperData={habitWrapperData}
        />
      </div>
    </div>
  );
};

export default Home;

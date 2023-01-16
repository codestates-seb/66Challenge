import { SlideBanner } from '../components/slideBanner';
import {
  slideData,
  categoryData,
  habitWrapperData,
} from '../data/homeStaticData';
import { useRouter } from 'next/router';
import { HabitWrapperHorizontal } from '../components/habitWrapperHorizontal';

export default function Home() {
  const router = useRouter();
  const cateIconClick = (url) => {
    router.push(url);
  };

  return (
    <div>
      <SlideBanner bannerCont={slideData} />
      <div className="main-category px-[20px]">
        <ul className="main-category-list grid grid-cols-4 my-[20px]">
          {categoryData.map((el, idx) => {
            return (
              <li
                className="main-category-item px-[9px] mt-[6px]"
                onClick={(_) => cateIconClick(el.cateLink)}
                key={el.cateTitle}
              >
                <div className="main-category-icon m-auto py-[12px] bg-slate-50 cursor-pointer rounded-xl flex justify-center">
                  <img
                    className="w-[50px]"
                    src={el.cateImgUrl}
                    alt="category icon"
                  />
                </div>
                <div className="main-category-title text-sm mt-[6px] mb-[13px] text-center">
                  {el.cateTitle}
                </div>
              </li>
            );
          })}
        </ul>
      </div>
      <div className="main-video w-full h-full">
        <iframe
          className="w-full min-h-[250px]"
          src="https://www.youtube.com/embed/cdZZpaB2kDM"
          title="YouTube video player"
          frameBorder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
          allowFullScreen
        ></iframe>
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
}

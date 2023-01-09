/* 
* <------  사용하시기 전에 꼭 읽어주세요! ------> *
ToDo 1. 슬라이드 배너를 사용하는 컴포넌트에서 bannerCont를 props로 넘겨주셔야 합니다.
ToDo 2. BannerCont는 배너에 들어갈 bgImgUrl(배경 이미지)과 contText(내용), contSubText(내용), bannerLink(클릭시 이동할 페이지 링크)를 담은 Object입니다.
Todo 3. 슬라이드가 자동으로 넘어가는 시간을 설정하고자 하는 경우에는 해당 시간을 't' attribute로 하여 props로 넘겨주셔야 합니다. (기본값은 2초 입니다.)

* <------  사용하시기 전에 꼭 읽어주세요! ------> *
*/

import { useEffect, useState, useRef } from 'react';
import { useRouter } from 'next/router';

export const SlideBanner = ({ bannerCont, t }) => {
  const [slideIdx, setSlideIdx] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setSlideIdx((slideIdx) => {
        return slideIdx >= bannerCont.length - 1 ? 0 : slideIdx + 1;
      });
    }, t || 2000);
    return () => clearInterval(timer);
  }, []);

  const router = useRouter();
  const bannerClickEvent = (bannerLink) => {
    router.push(bannerLink);
  };

  console.log(slideIdx);

  const slideRef = useRef();

  return (
    <div className="slidebanner-container relative" ref={slideRef}>
      <ul className="slidebanner-wrapper flex">
        {bannerCont.map((el, idx) => {
          return (
            <li
              className="sildebanner-background w-full"
              key={idx}
              onClick={(_) => bannerClickEvent(el.bannerLink)}
            >
              <div className="sildebanner-title">{el.contText}</div>
              <div className="sildebanner-subtitle">{el.contSubText}</div>
            </li>
          );
        })}
      </ul>
      <div className="slidebanner-pagination absolute right-0 bottom-0 bg-black/10 text-center tracking-wider text-xs min-w-[40pdx] p1y-0.5 pr-1 pl-1.5 rounded-xl flex justify-between ">
        <span>{slideIdx + 1}</span>
        <span>/</span>
        <span>{bannerCont.length}</span>
      </div>
    </div>
  );
};

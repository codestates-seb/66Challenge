/* 
* <------  사용하시기 전에 꼭 읽어주세요! ------> *
※ 현재는 무한 슬라이드 구조는 아닙니다. 마지막 슬라이드에 도달했을 때, 처음 슬라이드로 돌아가는 UI를 구현했습니다.
  추후 무한 슬라이드로 수정할 예정입니다. 사용 전 참고 부탁드립니다.

ToDo 1. 슬라이드 배너를 사용하는 컴포넌트에서 bannerCont와 t를 props로 넘겨주셔야 합니다.
ToDo 2. BannerCont는 배너에 들어갈 bgImgUrl(배경 이미지)과 contText(내용), contSubText(내용), bannerLink(클릭시 이동할 페이지 링크)를 담은 Object입니다.
Todo 3. 슬라이드가 자동으로 넘어가는 시간을 설정하고자 하는 경우에는 해당 시간을 't' attribute로 하여 props로 넘겨주셔야 합니다. (기본값은 2초 입니다.)

* <------  사용하시기 전에 꼭 읽어주세요! ------> *
*/

import { useState } from 'react';
import { useRouter } from 'next/router';
import Flicking from '@egjs/react-flicking';
import { AutoPlay } from '@egjs/flicking-plugins';
import '@egjs/flicking-plugins/dist/flicking-plugins.css';
import Image from 'next/image';

interface BannerContType {
  bgImgUrl: string;
  bannerLink: string;
}

interface SlideBannerProps {
  bannerCont: Array<BannerContType>;
  t: number;
  pagination: boolean;
  maxHeight?: string;
}

export const SlideBanner: React.FC<SlideBannerProps> = ({
  bannerCont,
  t,
  pagination,
  maxHeight,
}) => {
  const router = useRouter();
  const bannerClickEvent = (bannerLink: string): void => {
    router.push(bannerLink);
  };

  const [currendIdx, setCurrendIdx] = useState(0);

  const plugins = [
    new AutoPlay({
      duration: t || 2000,
      direction: 'NEXT',
      stopOnHover: true,
    }),
  ];

  const flickingOnChange = (e) => {
    setCurrendIdx(e.index);
  };

  return (
    <div className="slidebanner-container relative overflow-hidden">
      <Flicking
        className={`[&>div]:flex ${maxHeight}`}
        plugins={plugins}
        circular={true}
        horizontal={true}
        onChanged={flickingOnChange}
      >
        {bannerCont.map((el: BannerContType, idx: number) => {
          return (
            <div
              className={`sildebanner-background w-screen max-w-[460px] flex-[0_0_auto] flex flex-col justify-center items-center relative`}
              key={idx}
              onClick={(_) => bannerClickEvent(el.bannerLink)}
            >
              <Image
                src={el.bgImgUrl}
                alt="slide image"
                width="800"
                height="500"
              />
            </div>
          );
        })}
      </Flicking>
      {pagination && (
        <div className="slidebanner-pagination absolute right-2.5 top-2.5 bg-black/50 text-white text-center tracking-wider text-xs min-w-[40pdx] p1y-0.5 pt-[2px] pr-1 pl-1.5 rounded-xl flex justify-between ">
          <span>{currendIdx + 1}</span>
          <span>/</span>
          <span>{bannerCont.length}</span>
        </div>
      )}
    </div>
  );
};

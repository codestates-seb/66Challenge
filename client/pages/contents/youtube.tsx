import Iframe from 'react-iframe';
import { youtubeLink } from '../../data/homeStaticData';
import { useEffect, useState } from 'react';

const youtube = () => {
  const [baseData, setBaseData] = useState(youtubeLink);
  const [activeCategory, isActiveCategory] = useState('all');
  const [data, setData] = useState([]);
  const [page, setPage] = useState(0);
  const [target, setTarget] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);
  const [stop, setStop] = useState(false);

  const getMoreData = () => {
    setIsLoaded(true);
  };

  const onIntersect: IntersectionObserverCallback = ([entry], observer) => {
    if (entry.isIntersecting && !isLoaded) {
      observer.unobserve(entry.target);
      getMoreData();
      observer.observe(entry.target);
    }
  };

  useEffect(() => {
    let observer: IntersectionObserver;
    if (target && !stop) {
      observer = new IntersectionObserver(onIntersect, { threshold: 0 });
      observer.observe(target);
    }
  }, [target, isLoaded, stop]);

  useEffect(() => {
    if (isLoaded && !stop) {
      const startIdx = 5 * page;
      const endIdx = 5 * (page + 1);
      const pageData = baseData.slice(startIdx, endIdx);
      setData((data) => data.concat(pageData));
      setPage((page) => page + 1);
      setIsLoaded(false);

      if (pageData.length < 5) {
        setStop(true);
      }
    }
  }, [isLoaded, stop]);

  useEffect(() => {
    if (activeCategory === 'all') {
      setBaseData(youtubeLink);
    } else {
      setBaseData(youtubeLink.filter((el) => el.category === activeCategory));
    }
  }, [activeCategory]);

  interface NavItemsType {
    title: string;
    category: string;
  }

  const youtubeNavItems: Array<NavItemsType> = [
    {
      title: '전체',
      category: 'all',
    },
    {
      title: '습관형성',
      category: 'habit',
    },
    {
      title: '자기관리',
      category: 'selfcare',
    },
    {
      title: '운동',
      category: 'exercise',
    },
    {
      title: '학습',
      category: 'study',
    },
    {
      title: '식습관',
      category: 'dietary',
    },
    {
      title: '환경',
      category: 'eco',
    },
  ];

  const activeCategoryClassName: string =
    'bg-mainColor text-white border-mainColor';

  return (
    <div className="-mb-[100px]">
      <nav className="w-full sticky top-[56px] z-50 bg-white flex gap-2.5 px-2.5 py-2.5 overflow-x-scroll border-b-[1px] border-borderColor flex-nowrap scrollbar-hide">
        {youtubeNavItems.map((el) => {
          return (
            <div
              className={`flex-[0_0_auto] text-sm px-5 py-1 border-[1px] rounded-[50px] ${
                activeCategory === el.category ? activeCategoryClassName : null
              }`}
              key={el.category}
              onClick={(_) => isActiveCategory(el.category)}
            >
              {el.title}
            </div>
          );
        })}
      </nav>
      <section className="border-t-[9px] border-borderColor">
        {data.map((el) => {
          return (
            <div
              className="main-video w-full h-full border-b-[10px] last:border-0"
              key={el.link}
            >
              <Iframe
                className="w-full min-h-[250px]"
                url={el.link}
                title="YouTube video player"
                frameBorder={0}
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                allowFullScreen
                loading="lazy"
              ></Iframe>
            </div>
          );
        })}
      </section>
      <div ref={setTarget}></div>
    </div>
  );
};

export default youtube;

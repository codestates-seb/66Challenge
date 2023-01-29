import { bookData } from '../../data/bookStaticData';
import Iframe from 'react-iframe';
import { useEffect, useState } from 'react';

const Book = () => {
  const [baseData, setBaseData] = useState(bookData);
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
      const startIdx = 10 * page;
      const endIdx = 10 * (page + 1);
      const pageData = baseData.slice(startIdx, endIdx);
      setData((data) => data.concat(pageData));
      setPage((page) => page + 1);
      setIsLoaded(false);

      if (pageData.length < 5) {
        setStop(true);
      }
    }
  }, [isLoaded, stop]);
  return (
    <div className="min-h-screen -mb-[100px]">
      <h1 className="p-5 text-xl font-semibold">추천도서 📚</h1>
      <section className="border-t-[1px] border-borderColor">
        <div className="grid grid-cols-2">
          {data.map((el) => {
            return (
              <div
                className="main-video w-full h-full border-b-[1px] border-borderColor"
                key={el.link}
              >
                <Iframe
                  className="w-full min-h-[250px] [&>div.coupang]:hidden"
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
        </div>
      </section>
      <div ref={setTarget}></div>
    </div>
  );
};

export default Book;

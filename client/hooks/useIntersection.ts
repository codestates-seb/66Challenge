// useIntersection hook을 사용하실 때,
// Todo 1. 인자로 url과 page에 대한 state 및 setter, 그리고 사용하는 컴포넌트에서 데이터 담아둘 배열에 대한 setter를 넘겨주셔야 합니다.
// Todo 2. 훅을 사용하는 컴포넌트에서 맨 하단에 div 엘리먼트를 만들어주시고, ref의 속성값으로 훅에서 반환되는 setTarget을 넣어주시면 됩니다.
import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import axios from 'axios';

// (추후 수정) setData의 type에서 Array 안에 들어가는 객체의 타입을 지정해줘야 함
export function useIntersection(
  url: string,
  page: number,
  setPage: React.Dispatch<React.SetStateAction<number>>,
  setData: React.Dispatch<React.SetStateAction<Array<unknown>>>,
): Array<React.Dispatch<React.SetStateAction<HTMLElement>>> {
  const [target, setTarget] = useState(null);
  const [isLoaded, setIsLoaded] = useState(false);
  const [stop, setStop] = useState(false);
  const router = useRouter();

  const getMoreItem = () => {
    setIsLoaded(true);
  };

  const onIntersect: IntersectionObserverCallback = async (
    [entry],
    observer,
  ) => {
    if (entry.isIntersecting && !isLoaded) {
      observer.unobserve(entry.target);
      await getMoreItem();

      observer.observe(entry.target);
    }
  };

  useEffect(() => {
    setStop(false);
  }, [url]);

  useEffect(() => {
    let observer: IntersectionObserver;
    if (target && !stop) {
      observer = new IntersectionObserver(onIntersect, {
        threshold: 1,
      });
      observer.observe(target);
    }
    return () => observer && observer.disconnect();
  }, [target, isLoaded, stop]);

  useEffect(() => {
    if (isLoaded && !stop && router.isReady) {
      axios.get(`${url}page=${page}&size=30`).then((res) => {
        setData((data) => data.concat(res.data));
        setPage((page) => page + 1);
        setIsLoaded(false);

        if (res.data.length < 30) {
          setStop(true);
        }
      });
    }
  }, [isLoaded, stop, router.isReady]);

  return [setTarget];
}

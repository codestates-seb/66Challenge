import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import axios from 'axios';

export function useIntersection(
  url: string,
  lastId: number,
  setLastId: React.Dispatch<React.SetStateAction<number>>,
  setData: React.Dispatch<React.SetStateAction<Array<unknown>>>,
  size: number,
  type: string,
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
      axios
        .get(
          `${url}${
            lastId === null ? '' : 'lastId=' + lastId + '&'
          }size=${size}`,
        )
        .then((res) => {
          const newLastItem = res.data[res.data.length - 1];
          let newLastItemId: number | null;
          if (type === 'habit') {
            newLastItemId = newLastItem?.habitId || null;
          } else if (type === 'auth') {
            newLastItemId = newLastItem?.authId || null;
          } else if (type === 'review') {
            newLastItemId = newLastItem?.reviewId || null;
          }

          setData((data) => data.concat(res.data));
          setLastId(newLastItemId);
          setIsLoaded(false);

          if (res.data.length < size) {
            setStop(true);
          }
        });
    }
  }, [isLoaded, stop, router.isReady]);

  return [setTarget];
}

export function useIntersectionPage(
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
      axios.get(`${url}page=${page}&size=10`).then((res) => {
        setData((data) => data.concat(res.data));
        setPage((page) => page + 1);
        setIsLoaded(false);

        if (res.data.length < 10) {
          setStop(true);
        }
      });
    }
  }, [isLoaded, stop, router.isReady]);

  return [setTarget];
}

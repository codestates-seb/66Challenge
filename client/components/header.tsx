import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import Link from 'next/link';
import {
  IoSearch,
  IoSettings,
  IoArrowBack,
  IoShareSocialSharp,
  IoHome,
} from 'react-icons/io5';
import { PushSubscribe } from './pushSubscribe';
import Image from 'next/image';
import logo from '../public/image/66logo.png';
import { FC } from 'react';

const Header: FC = () => {
  const router = useRouter();
  const slicer: RegExp = /\/|\?/;
  const pathArr: string[] = router.asPath.split(slicer).slice(1);

  const [isLogo, setIsLogo] = useState<boolean>(false);
  const [isMyPage, setIsMyPage] = useState<boolean>(false);
  const [isHomeNeed, setIsHomeNeed] = useState<boolean>(false);
  const [pageTitle, setPageTitle] = useState<string | null>(null);

  const titleList: object = {
    login: '로그인',
    signup: '회원가입',
    madehabit: '내가 만든 습관',
    savedhabit: '내가 찜한 습관',
    post: '습관 등록하기',
    search: '습관 검색하기',
    edit: '습관 수정하기',
    detail: '습관 상세보기',
    auth: '습관 인증하기',
    statistic: '습관 통계',
    review: '습관 후기',
  };
  // path별 페이지제목

  useEffect(() => {
    function titleDetect(pathArr): void {
      for (let i = pathArr.length - 1; i >= 0; i--) {
        if (titleList[pathArr[i]]) {
          setPageTitle(titleList[pathArr[i]]);
          return;
        }
      }
      setPageTitle(null);
    }

    function homeDetect(pathArr): void {
      if (pathArr.includes('detail')) {
        setIsHomeNeed(true);
      } else {
        setIsHomeNeed(false);
      }
    }

    function logoDetact(): void {
      if (pageTitle) {
        setIsLogo(false);
      } else {
        setIsLogo(true);
      }
    }

    function settingsDetect(): void {
      if (pathArr.includes('mypage')) {
        setIsMyPage(true);
      } else {
        setIsMyPage(false);
      }
    }

    titleDetect(pathArr);
    homeDetect(pathArr);
    logoDetact();
    settingsDetect();
  }, [pathArr]);

  const LeftSide: FC = () => {
    // 폰트 정해야함 혹은 타이포그래피?
    const Logo: FC = () => {
      return (
        <Link
          className="text-2xl flex flex-row justify-start items-center"
          href="/"
        >
          <Image src={logo} alt="66logo" className="w-10 h-8 ml-2 mr-1" />
          {/* <span className="text-3xl ml-2 mr-1">66</span> */}
          <div className="text-base font-web">CHALLENGE</div>
        </Link>
      );
    };

    const Back: FC = () => {
      return (
        <div className="flex flex-row items-center h-full ml-2">
          <IoArrowBack className="h-6 w-6" onClick={() => router.back()} />
        </div>
      );
    };

    return (
      <div className="basis-1/4 flex flex-row justify-start items-center">
        {isLogo ? <Logo /> : <Back />}
      </div>
    );
  };

  const MiddleSide: FC = () => {
    const Title: FC = () => {
      return <div className="text-lg">{pageTitle}</div>;
    };

    return (
      <div className="ml-2 basis-1/2 flex flex-row justify-center items-center">
        {pageTitle ? <Title /> : null}
      </div>
    );
  };

  const RightSide: FC = () => {
    const Search: FC = () => {
      return (
        <div className="mr-3 flex">
          {isHomeNeed ? (
            <Link href={'/'}>
              <IoHome className="h-6 w-6 mr-2" />
            </Link>
          ) : (
            ''
          )}
          {isMyPage ? (
            <div className="flex">
              <PushSubscribe />
              <IoSettings className="h-6 w-6 ml-2" />
            </div>
          ) : (
            <Link href={'/habit/search'}>
              <IoSearch className="h-6 w-6" />
            </Link>
          )}
        </div>
      );
    };

    return (
      <div className="basis-1/4 flex flex-row justify-end items-center">
        <Search />
      </div>
    );
  };

  return (
    <header className="z-[1] sticky top-0 bg-white">
      <div className="z-40">
        <div>
          <link
            href="https://fonts.googleapis.com/css2?family=Sofia+Sans+Condensed:wght@700&display=swap"
            rel="stylesheet"
          />
        </div>

        <div className="box-border flex flex-row h-14 justify-between ">
          <LeftSide />
          <MiddleSide />
          <RightSide />
        </div>
      </div>
    </header>
  );
};

export { Header };

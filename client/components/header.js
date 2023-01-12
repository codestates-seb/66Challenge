import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import Link from 'next/link';
import {
  IoSearch,
  IoSettings,
  IoArrowBack,
  IoShareSocialSharp,
} from 'react-icons/io5';
import Logo66 from '../public/SvgjsG2027 66x58.svg';

const Header = () => {
  const router = useRouter();
  const pathArr = router.asPath.split('/').slice(1); // path의 요소를 나눔
  const last = pathArr[pathArr.length - 1]; // path의 마지막 요소
  const beforeLast = pathArr[pathArr.length - 2]; // path의 마지막에서 두번째요소

  const [isLogo, setIsLogo] = useState(false);
  const [isMyPage, setIsMyPage] = useState(false);
  const [pageTitle, setPageTitle] = useState(null);
  const [isCanShare, setIsCanShare] = useState(false);

  const titleList = {
    login: '로그인',
    signup: '회원가입',
    edit: '회원 정보 수정',
    madehabit: '내가 만든 습관',
    savehabit: '내가 찜한 습관',
    post: '습관 등록하기',
    search: '습관 검색하기',
    edit: '습관 수정하기',
    detail: '습관 상세보기',
    auth: '습관 인증하기',
  };
  // path별 페이지제목
  useEffect(() => {
    function logoDetact() {
      if (pageTitle) {
        setIsLogo(false);
      } else {
        setIsLogo(true);
      }
    }

    function titleDetect(path1, path2) {
      if (titleList[path1]) {
        setPageTitle(titleList[path1]);
      } else if (titleList[path2]) {
        setPageTitle(titleList[path2]);
      } else {
        setPageTitle(null);
      }
    }

    function settingsDetect() {
      if (pathArr.includes('mypage')) {
        setIsMyPage(true);
      } else {
        setIsMyPage(false);
      }
    }

    function shareDetect() {
      if (pathArr.includes('detail')) {
        // if (pathArr.includes('login')) { // 공유버튼 테스트용
        setIsCanShare(true);
      } else {
        setIsCanShare(false);
      }
    }
    titleDetect(last, beforeLast);
    logoDetact();
    settingsDetect();
    shareDetect();
  }, [last, pageTitle]);

  const LeftSide = () => {
    // 폰트 정해야함
    function Logo() {
      return (
        <Link
          className="text-2xl flex flex-row justify-start items-center"
          href="/"
        >
          <Logo66 className="ml-3 w-max h-max mb-2" />
          <div className="text-base">CHALLENGE</div>
        </Link>
      );
    }

    function Back() {
      return (
        <div className="flex flex-row items-center h-full ml-2">
          <IoArrowBack className="h-9 w-9" onClick={() => router.back()} />
        </div>
      );
    }

    return (
      <div className="basis-1/4 flex flex-row justify-start items-center">
        {isLogo ? <Logo /> : <Back />}
      </div>
    );
  };

  const MiddleSide = () => {
    const Title = () => {
      return <div className="text-lg">{pageTitle}</div>;
    };

    return (
      <div className="ml-2 basis-1/2 flex flex-row justify-center items-center">
        {pageTitle ? <Title /> : null}
      </div>
    );
  };

  const RightSide = () => {
    const Share = () => {
      return <IoShareSocialSharp className="h-8 w-8 mr-2" />;
    };
    const Search = () => {
      return (
        <div className="mr-3">
          {isMyPage ? (
            <IoSettings className="h-8 w-8" />
          ) : (
            <IoSearch className="h-8 w-8" />
          )}
        </div>
      );
    };

    return (
      <div className="basis-1/4 flex flex-row justify-end items-center">
        {isCanShare ? <Share /> : null}
        <Search />
      </div>
    );
  };

  return (
    <header className="sticky top-0 bg-white">
      <div className="box-border flex flex-row h-14 justify-between  border-b border-mainColor border-solid">
        <LeftSide />
        <MiddleSide />
        <RightSide />
      </div>
    </header>
  );
};

export { Header };

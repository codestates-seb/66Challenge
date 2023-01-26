import { useState, useEffect } from 'react';
interface IhabitData {
  habitData?: { title: string; imageUrl: string | null; habitId?: number };
}
declare global {
  interface Window {
    Kakao: any;
  }
}
export function KaKaoShare({ habitData }: IhabitData) {
  const [shareButton, setShareButton] = useState(false);
  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://developers.kakao.com/sdk/js/kakao.js';
    script.async = true;
    document.body.appendChild(script);

    // 스크립트가 로드 된 후 쉐어버튼 렌더링
    script.onload = () => {
      setShareButton(true);
    };

    return () => {
      document.body.removeChild(script);
    };
  }, []);
  const shareKakaoHandle = () => {
    if (window.Kakao) {
      // 카카오 스크립트가 로드된 경우 init
      const kakao = window.Kakao;
      if (!kakao.isInitialized()) {
        kakao.init(process.env.NEXT_PUBLIC_KAKAO_JS_KEY);
      }
      kakao.Link.sendDefault({
        objectType: 'feed',
        content: {
          title: '66 Challenge',
          description:
            habitData === undefined
              ? '이겨내고 나아가든가, 사로잡혀 좌절하든가'
              : habitData.title,
          // imageUrl 이 없으면 동작 안하기 때문에 default 이미지를 준비해 두기
          imageUrl:
            habitData === undefined
              ? 'http://k.kakaocdn.net/dn/HhadR/btrW9mzvrxT/9OvV6JMFtdzi62etK2BP10/kakaolink40_original.png'
              : habitData.imageUrl,
          link: {
            mobileWebUrl:
              habitData === undefined
                ? 'http://localhost:3000'
                : `http://localhost:3000/habit/detail/${habitData.habitId}`,
            webUrl:
              habitData === undefined
                ? 'http://localhost:3000'
                : `http://localhost:3000/habit/detail/${habitData.habitId}`,
          },
        },
        buttons: [
          {
            title: '습관 구경하기!',
            link: {
              mobileWebUrl:
                habitData === undefined
                  ? 'http://localhost:3000'
                  : `http://localhost:3000/habit/detail/${habitData.habitId}`,
              webUrl:
                habitData === undefined
                  ? 'http://localhost:3000'
                  : `http://localhost:3000/habit/detail/${habitData.habitId}`,
            },
          },
        ],
      });
    }
  };
  return (
    <>
      {shareButton && (
        <div className="w-full flex justify-center items-center flex-col">
          <span className="text-base font-semibold mb-3">
            {habitData === undefined ? '친구 초대하기' : 'SNS 공유하기'}
          </span>
          <img
            src="/image/logo/kakao.svg"
            onClick={shareKakaoHandle}
            alt="kakao"
          />
        </div>
      )}
    </>
  );
}

import React, { useEffect } from 'react';

declare global {
  interface Window {
    Kakao: any;
  }
}

export function KaKaoShare() {
  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://developers.kakao.com/sdk/js/kakao.js';
    script.async = true;
    document.body.appendChild(script);
    return () => {
      document.body.removeChild(script);
    };
  }, []);

  const ShareToKatalk = () => {
    if (window.Kakao) {
      const kakao = window.Kakao;
      if (!kakao.isInitialized()) {
        kakao.init(`${process.env.NEXT_PUBLIC_KAKAO_JS_KEY}`);
      }

      kakao.Link.sendDefault({
        objectType: 'feed',
        content: {
          title: '제목',
          description: '내용',
          imageUrl:
            'https://hips.hearstapps.com/hmg-prod/images/running-is-one-of-the-best-ways-to-stay-fit-royalty-free-image-1036780592-1553033495.jpg?crop=0.668xw:1.00xh;0.260xw,0&resize=1200:*',
          link: 'http://localhost:3000',
        },
      });
    }
  };

  return (
    <>
      <button onClick={ShareToKatalk}>sns 공유하기</button>
    </>
  );
}

import { useEffect } from 'react';
import { LoadingIndicator } from '../../../components/loadingIndicator';
import { useRouter } from 'next/router';

export default function ToMobile() {
    const router = useRouter();
  const shareKakaoHandle = () => {
    if (window.Kakao) {
      console.log('a');
      // 카카오 스크립트가 로드된 경우 init
      const kakao = window.Kakao;
      if (!kakao.isInitialized()) {
        kakao.init(process.env.NEXT_PUBLIC_KAKAO_JS_KEY);
      }
      kakao.Link.sendDefault({
        objectType: 'feed',
        content: {
          title: '66 Challenge',
          description: '카카오톡 알림',
          // imageUrl 이 없으면 동작 안하기 때문에 default 이미지를 준비해 두기
          imageUrl:
            'http://k.kakaocdn.net/dn/HhadR/btrW9mzvrxT/9OvV6JMFtdzi62etK2BP10/kakaolink40_original.png',

          link: {
            mobileWebUrl: 'https://66challenge.shop',
            webUrl: 'https://66challenge.shop',
          },
        },
        buttons: [
          {
            title: '구경하러 가기!',
            link: {
              mobileWebUrl: 'https://66challenge.shop',
              webUrl: 'https://66challenge.shop',
            },
          },
        ],
      });
    }
  };

  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://developers.kakao.com/sdk/js/kakao.js';
    script.async = true;
    document.body.appendChild(script);
    setTimeout(() => {
      shareKakaoHandle();
      router.push('/');
    }, 500);
  }, []);
  return (
    <div className="h-screen flex items-center -mb-[100px]">
      <LoadingIndicator />
    </div>
  );
}

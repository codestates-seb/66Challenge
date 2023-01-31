import axios from 'axios';
import { getCookie, setCookie, removeCookie } from './cookies';

interface onSilentRefreshType {
  refreshToken: string;
  isRefresh: boolean;
}

export const onSilentRefresh = () => {
  if (!getCookie('refreshJwtToken')) return;
  const data: onSilentRefreshType = {
    refreshToken: getCookie('refreshJwtToken'),
    isRefresh: false,
  };
  axios
    .post(`${process.env.NEXT_PUBLIC_SERVER_URL}/reissue`, data)
    .then((res) => {
      onLoginSuccess(res.headers.authorization);
      if (res.headers.refresh) {
        removeCookie('refreshJwtToken');
        setCookie('refreshJwtToken', res.headers.refresh, {
          path: '/',
          httpOnly: true,
          secure: true,
        });
      }
    })
    .catch((error) => {
      // TODO 추후 Refresh 토큰을 재발급 받는 형태로 수정 요함
      console.error(error);
    });
};

export const onLoginSuccess = (accessToken: string) => {
  // accessToken 설정
  axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

  // accessToken 만료하기 1분 전에 로그인 연장
  setTimeout(onSilentRefresh, 24 * 3600 * 1000 - 60000);
};

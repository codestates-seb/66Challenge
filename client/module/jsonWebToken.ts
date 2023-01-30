import axios from 'axios';
import { getCookie, setCookie, removeCookie } from './cookies';
import { useAppDispatch } from '../ducks/store';
import { initLoginIdentity } from '../ducks/loginIdentitySlice';

interface onSilentRefreshType {
  refreshToken: string;
  isRefresh: boolean;
}

export const onSilentRefresh = () => {
  const dispatch = useAppDispatch();
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
        setCookie('refreshJwtToken', res.headers.refresh, { path: '/' });
      }
    })
    .catch((error) => {
      // TODO 일시적으로 로그아웃되도록 해놓았음 -> 추후 Refresh 토큰을 재발급 받는 형태로 수정 요함
      dispatch(initLoginIdentity());
      console.error(error);
    });
};

export const onLoginSuccess = (accessToken: string) => {
  // accessToken 설정
  axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

  // accessToken 만료하기 1분 전에 로그인 연장
  setTimeout(onSilentRefresh, 24 * 3600 * 1000 - 60000);
};

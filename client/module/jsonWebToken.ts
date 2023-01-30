import axios from 'axios';

export const onLogin = (email, password) => {
  const data = {
    email,
    password,
  };
  axios
    .post('/login', data)
    .then(onLoginSuccess)
    .catch((error) => {
      // ... 에러 처리
    });
};

export const onSilentRefresh = () => {
  axios
    .post('/silent-refresh')
    .then(onLoginSuccess)
    .catch((error) => {
      // ... 로그인 실패 처리
    });
};

export const onLoginSuccess = (response) => {
  const { accessToken } = response.data;

  // accessToken 설정
  axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

  // accessToken 만료하기 1분 전에 로그인 연장
  setTimeout(onSilentRefresh, 24 * 3600 * 1000 - 60000);
};

import { ReactElement, useEffect } from 'react';
import { setCookie } from '../module/cookies';
import { useRouter } from 'next/router';
import { LoadingIndicator } from '../components/loadingIndicator';
import { useAppDispatch } from '../ducks/store';
import { oauthLogin } from '../ducks/loginIdentitySlice';
import axios from 'axios';

const OauthLogin = () => {
  const router = useRouter();
  const dispatch = useAppDispatch();

  useEffect(() => {
    if (router.isReady) {
      const { access_token, refresh_token, user_id } = router.query;
      // setCookie('accessJwtToken', access_token, { path: '/' });
      axios.defaults.headers.common['Authorization'] = `Bearer ${access_token}`;
      setCookie('refreshJwtToken', refresh_token, { path: '/' });
      dispatch(oauthLogin(user_id));
      router.push('/user/oauthsignup');
    }
  }, [router.isReady]);

  return (
    <div className="h-screen flex items-center -mb-[100px]">
      <LoadingIndicator />
    </div>
  );
};

// OauthLogin.getLayout = function getLayout(page: ReactElement) {
//   return <>{page}</>;
// };

export default OauthLogin;

import '../styles/globals.css';
import { wrapper, persistor } from '../ducks/store';
import { PersistGate } from 'redux-persist/integration/react';
import type { ReactElement, ReactNode } from 'react';
import type { NextPage } from 'next';
import type { AppProps } from 'next/app';
import Layout from '../components/layout/layout';
import { LoadingIndicator } from '../components/loadingIndicator';
import { useEffect } from 'react';
import { onSilentRefresh } from '../module/jsonWebToken';
import { getCookie } from '../module/cookies';
import { useAppDispatch } from '../ducks/store';
import { initLoginIdentity } from '../ducks/loginIdentitySlice';
import { withHead } from '../components/layout/withHead';
export type NextPageWithLayout<P = {}, IP = P> = NextPage<P, IP> & {
  getLayout?: (page: ReactElement) => ReactNode;
};

type AppPropsWithLayout = AppProps & {
  Component: NextPageWithLayout;
};
function MyApp({ Component, pageProps }: AppPropsWithLayout) {
  const getLayout = Component.getLayout || ((page) => page);
  const dispatch = useAppDispatch();

  useEffect(() => {
    if (getCookie('refreshJwtToken')) {
      onSilentRefresh();
    } else {
      dispatch(initLoginIdentity());
    }
  }, []);

  return (
    <PersistGate persistor={persistor} loading={<LoadingIndicator />}>
      {Component.getLayout ? (
        getLayout(<Component {...pageProps} />)
      ) : (
        <Layout>{getLayout(<Component {...pageProps} />)}</Layout>
      )}
    </PersistGate>
  );
}

export default withHead(
  wrapper.withRedux(MyApp),
  '66일 좋은 습관 만들기',
  '66일 동안 습관 만들기! 좋은 습관 만들고싶어? 66일이면 충분해. 66일동안 너 자신을 증명해봐. 야, 너두 할 수 있어',
  '',
  '/image/logo.svg',
);

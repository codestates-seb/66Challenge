import '../styles/globals.css';
import { wrapper, persistor } from '../ducks/store';
import { PersistGate } from 'redux-persist/integration/react';
import type { ReactElement, ReactNode } from 'react';
import type { NextPage } from 'next';
import type { AppProps, AppContext } from 'next/app';
// import App from 'next/app';
import Layout from '../components/layout/layout';
import { LoadingIndicator } from '../components/loadingIndicator';
// import cookies from 'next-cookies';

export type NextPageWithLayout<P = {}, IP = P> = NextPage<P, IP> & {
  getLayout?: (page: ReactElement) => ReactNode;
};

type AppPropsWithLayout = AppProps & {
  Component: NextPageWithLayout;
};
function MyApp({ Component, pageProps }: AppPropsWithLayout) {
  const getLayout = Component.getLayout || ((page) => page);

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

// MyApp.getInitialProps = async (appContext: AppContext) => {
//   const appProps = await App.getInitialProps(appContext);

//   const { ctx } = appContext;
//   const allCookies = cookies(ctx);
//   const accessTokenByCookie = allCookies['accessJwtToken'];
//   console.log(accessTokenByCookie);
//   if(accessTokenByCookie !== undefined) {
//       const refreshTokenByCookie = (allCookies["refreshJwtToken"] || "");
//       setToken(accessTokenByCookie, refreshTokenByCookie)
//   }

//   return { ...appProps };
// };

export default wrapper.withRedux(MyApp);

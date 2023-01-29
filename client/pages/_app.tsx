import '../styles/globals.css';
import { wrapper, persistor } from '../ducks/store';
import { PersistGate } from 'redux-persist/integration/react';
import type { ReactElement, ReactNode } from 'react';
import type { NextPage } from 'next';
import type { AppProps } from 'next/app';
import Layout from '../components/layout/layout';
import { LoadingIndicator } from '../components/loadingIndicator';

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
export default wrapper.withRedux(MyApp);

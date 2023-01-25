import '../styles/globals.css';
import { wrapper, persistor } from '../ducks/store';
import { PersistGate } from 'redux-persist/integration/react';
import type { ReactElement, ReactNode } from 'react';
import type { NextPage } from 'next';
import type { AppProps } from 'next/app';
import Layout from '../components/layout/layout';
import { LoadingIndicator } from '../components/loadingIndicator';
import firebase from 'firebase';
import { useEffect } from 'react';
import { getToken } from '../util/firebase';
const firebaseConfig = {
  apiKey: process.env.NEXT_PUBLIC_FIREBASE_API_KEY,
  authDomain: process.env.NEXT_PUBLIC_FIREBASE_AUTH_DOMAIN,
  projectId: process.env.NEXT_PUBLIC_FIREBASE_PROJECT_ID,
  storageBucket: process.env.NEXT_PUBLIC_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: process.env.NEXT_PUBLIC_FIREBASE_MESSAGING_SENDER_ID,
  appId: process.env.NEXT_PUBLIC_FIREBASE_APP_ID,
  measurementId: process.env.NEXT_PUBLIC_FIREBASE_MEASUREMENT_ID,
};
export type NextPageWithLayout<P = {}, IP = P> = NextPage<P, IP> & {
  getLayout?: (page: ReactElement) => ReactNode;
};

type AppPropsWithLayout = AppProps & {
  Component: NextPageWithLayout;
};
function MyApp({ Component, pageProps }: AppPropsWithLayout) {
  const getLayout = Component.getLayout || ((page) => page);
  if (!firebase.apps.length) {
    firebase.initializeApp(firebaseConfig);
  } else {
    firebase.app();
  }
  useEffect(() => {
    firebaseMessageToken();
  }, []);
  // async 를 사용학 위해 메서드로 따로 분리함
  const firebaseMessageToken = async () => {
    let token = await getToken();
  };
  return (
    <PersistGate persistor={persistor} loading={<LoadingIndicator />}>
      <Layout>{getLayout(<Component {...pageProps} />)}</Layout>
    </PersistGate>
  );
}
export default wrapper.withRedux(MyApp);

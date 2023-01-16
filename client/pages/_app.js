import '../styles/globals.css';

import { BottomNav } from '../components/bottomNav/bottomNav';
import { HomeNav } from '../components/homeNav';

import { Header } from '../components/header';
import { wrapper, persistor } from '../ducks/store';
import { PersistGate } from 'redux-persist/integration/react';

function MyApp({ Component, pageProps }) {
  const getLayout = Component.getLayout || ((page) => page);
  return getLayout(
    <PersistGate persistor={persistor} loading={<div>loading...</div>}>
      <Header />
      <HomeNav />
      <main className="pb-[100px]">
        <Component {...pageProps} />
      </main>
      <BottomNav />
    </PersistGate>,
  );
}
export default wrapper.withRedux(MyApp);

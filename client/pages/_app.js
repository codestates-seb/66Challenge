import '../styles/globals.css';

import { BottomNav } from '../components/bottomNav/bottomNav';
import { HomeNav } from '../components/homeNav';
import { wrapper, persistor } from '../ducks/store';
import { PersistGate } from 'redux-persist/integration/react';
function MyApp({ Component, pageProps }) {
  console.log(wrapper);
  return (
    <PersistGate persistor={persistor} loading={<div>loading...</div>}>
      <HomeNav />
      <main className="pb-[100px]">
        <Component {...pageProps} />
      </main>
      <BottomNav />
    </PersistGate>
  );
}
export default wrapper.withRedux(MyApp);

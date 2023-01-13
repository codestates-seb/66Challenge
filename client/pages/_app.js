import '../styles/globals.css';

import { BottomNav } from '../components/bottomNav/bottomNav';
import { HomeNav } from '../components/homeNav';
import { Provider } from 'react-redux';
import store from '../ducks/store';

function MyApp({ Component, pageProps }) {
  return (
    <Provider store={store}>
      <HomeNav />
      <main className="pb-[100px]">
        <Component {...pageProps} />
      </main>
      <BottomNav />
    </Provider>
  );
}

export default MyApp;

import '../styles/globals.css';

import { BottomNav } from '../components/bottomNav/bottomNav';
import { HomeNav } from '../components/homeNav';

function MyApp({ Component, pageProps }) {
  return (
    <>
      <HomeNav />
      <Component {...pageProps} />
      <BottomNav />
    </>
  );
}

export default MyApp;

import '../styles/globals.css';

import { BottomNav } from '../components/bottomNav/bottomNav';
import { HomeNav } from '../components/homeNav';

function MyApp({ Component, pageProps }) {
  return (
    <>
      <HomeNav />
      <main className="mb-[100px]">
        <Component {...pageProps} />
      </main>
      <BottomNav />
    </>
  );
}

export default MyApp;

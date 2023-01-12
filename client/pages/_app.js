import '../styles/globals.css';

import { BottomNav } from '../components/bottomNav/bottomNav';
import { HomeNav } from '../components/homeNav';
import { Header } from '../components/header';

function MyApp({ Component, pageProps }) {
  return (
    <>
      <Header />
      <HomeNav />
      <main className="pb-[100px]">
        <Component {...pageProps} />
      </main>
      <BottomNav />
    </>
  );
}

export default MyApp;

import '../styles/globals.css';
import { BottomNav } from '../components/bottomNav/bottomNav';

function MyApp({ Component, pageProps }) {
  return (
    <>
      <Component {...pageProps} />
      <BottomNav />
    </>
  );
}

export default MyApp;

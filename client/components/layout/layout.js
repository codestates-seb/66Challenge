import { BottomNav } from '../bottomNav/bottomNav';
import { TopNav } from '../topNav';
import { Header } from '../header';
import { ScrollToTopButton } from '../scrollToTopButton';

export default function Layout({ children }) {
  return (
    <>
      <Header />
      <TopNav />
      <main className="pb-[100px]">{children}</main>
      <ScrollToTopButton />
      <BottomNav />
    </>
  );
}

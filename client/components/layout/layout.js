import { BottomNav } from '../bottomNav/bottomNav';
import { TopNav } from '../topNav';
import { Header } from '../header';

export default function Layout({ children }) {
  return (
    <>
      <Header />
      <TopNav />
      <main className="pb-[100px]">{children}</main>
      <BottomNav />
    </>
  );
}

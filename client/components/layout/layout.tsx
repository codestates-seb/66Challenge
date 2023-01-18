import { BottomNav } from '../bottomNav/bottomNav';
import { TopNav } from '../topNav';
import { Header } from '../header';
import { ScrollToTopButton } from '../scrollToTopButton';
import React from 'react';

type LayoutProps = {
  children: React.ReactNode;
};

const Layout = ({ children }: LayoutProps) => {
  return (
    <>
      <Header />
      <TopNav />
      <main className="pb-[100px]">{children}</main>
      <ScrollToTopButton />
      <BottomNav />
    </>
  );
};

export default Layout;

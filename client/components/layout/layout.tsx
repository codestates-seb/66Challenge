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
    <div className="desktop-section bg-slate-100">
      <div className="app-section min-w-[360px] max-w-[460px] bg-white">
        <Header />
        <TopNav />
        <main className="pb-[100px]">{children}</main>
        <ScrollToTopButton />
        <BottomNav />
      </div>
    </div>
  );
};

export default Layout;

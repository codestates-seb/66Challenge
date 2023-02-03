import { BottomNav } from '../bottomNav/bottomNav';
import { TopNav } from '../topNav';
import { Header } from '../header';
import { ScrollToTopButton } from '../scrollToTopButton';
import React from 'react';
import styled from 'styled-components';
import Image from 'next/image';

type LayoutProps = {
  children: React.ReactNode;
};

const DesktopDiv = styled.div``;

const Layout = ({ children }: LayoutProps) => {
  return (
    <div className="desktop-section bg-slate-100 flex justify-center relative">
      <div className="fixed insert-0 bg-mainBackground bg-no-repeat h-full w-full" />
      <div className="app-section w-full min-h-screen min-w-[360px] max-w-[460px] bg-white z-[1]">
        <Header />
        <TopNav />
        <main className="pb-[100px] z-[1]">{children}</main>
        <ScrollToTopButton />
        <BottomNav />
      </div>
    </div>
  );
};

export default Layout;

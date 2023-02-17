import Head from 'next/head';
import type { NextPageWithLayout } from '../../pages/_app';

export function withHead(
  Component?: NextPageWithLayout,
  title?: string,
  description?: string,
  pageRouter?: string,
  ogImageUrl?: string,
) {
  const App = (props) => {
    const domain = `https://66challenge.shop/${pageRouter}`;

    return (
      <>
        <Head>
          <link rel="canonical" href={domain} />
          <title>66Challenge | {title}</title>
          <meta name="description" content={description} />
          <meta
            name="keyword"
            content="습관,66일,챌린지,challenge,운동,다이어트,일상,식습관,멘탈,공부,스터디,효도"
          />
          <meta property="og:title" content={title} />
          <meta property="og:description" content={description} />
          <meta property="og:image" content={ogImageUrl} />
        </Head>
        {Component === undefined ? null : <Component {...props} />}
      </>
    );
  };
  return App;
}

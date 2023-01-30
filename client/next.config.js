/** @type {import('next').NextConfig} */

const widthPlugins = require('next-compose-plugins');
const withPWA = require('next-pwa')({
  dest: 'public',
});

const nextConfig = {
  reactStrictMode: true,
  webpack(config) {
    config.module.rules.push({
      test: /\.svg$/,
      use: ['@svgr/webpack'],
    });

    return config;
  },
};

const remoteImg = {
  images: {
    remotePatterns: [
      {
        protocol: 'https',
        hostname: 'media.istockphoto.com',
      },
      {
        protocol: 'http',
        hostname: 'challenge66.file.bucket.s3.ap-northeast-2.amazonaws.com',
        port: '',
        pathname: '/images/**',
      },
      {
        protocol: 'http',
        hostname: 's3.ap-northeast-2.amazonaws.com',
        port: '',
        pathname: '/challenge66.file.bucket/images/**',
      },
      {
        protocol: 'http',
        hostname: 'runningmagazine.ca',
        port: '',
        pathname: '/wp-content/**',
      },
    ],
  },
};

module.exports = widthPlugins([nextConfig, withPWA, remoteImg]);

// const withPWA = require('next-pwa')({
//   dest: 'public',
// });

// module.exports = withPWA({});

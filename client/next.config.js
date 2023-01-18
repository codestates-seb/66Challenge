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
    ],
  },
};

module.exports = widthPlugins([nextConfig, withPWA, remoteImg]);

// const withPWA = require('next-pwa')({
//   dest: 'public',
// });

// module.exports = withPWA({});

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

module.exports = widthPlugins([nextConfig, withPWA]);

// const withPWA = require('next-pwa')({
//   dest: 'public',
// });

// module.exports = withPWA({});

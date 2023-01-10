/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './pages/**/*.{js,ts,jsx,tsx}',
    './components/**/*.{js,ts,jsx,tsx}',
  ],
  theme: {
    extend: {
      colors: {
        mainColor: '#222222',
        subColor: '#F89500',
        iconColor: 'white',
        'gray-pink': '#999999',
        'gray-border': '#929292',
        'sky-pink': '#f4caf8',
        'light-green': '#9de3b2',
      },
      fontFamily: {
        sans: ['Noto Sans KR', 'sans-serif'],
      },
      translate: {
        42: '10.5rem',
        56: '14rem',
      },
      keyframes: {
        bookMark: {
          '0% ': { transform: 'scale(0)' },
          '50%': { transform: 'scale(1.25)' },
          '100%': { transform: 'scale(1)' },
        },
        dropDown: {
          '0%': { transform: 'translateY(0)' },
          '100%': { transform: 'translateY(-50)' },
        },
        speechBubble: {
          '0%': { top: '-3px' },
          '60%': { top: '-3px' },
          '70%': { top: '-8px' },
          '80%': { top: '-3px' },
          '90%': { top: '-5px' },
          '100%': { top: '-3px' },
        },
      },
      animation: {
        bookMark: 'bookMark 1s ease-in-out ',
        dropDown: 'dropDown 1s ease',
        speechBubble: 'speechBubble 1.8s ease-in-out Infinite',
      },
    },
  },
  plugins: [
    require('@tailwindcss/line-clamp'),
    require('tailwind-scrollbar-hide'),
  ],
};

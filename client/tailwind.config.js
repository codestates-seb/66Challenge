/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './pages/**/*.{js,ts,jsx,tsx}',
    './components/**/*.{js,ts,jsx,tsx}',
  ],
  theme: {
    extend: {
      colors: {
        mainColor: '#494949',
        subColor: '#F89500',
        iconColor: 'white',
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
      },
      animation: {
        bookMark: 'bookMark 1s ease-in-out ',
        dropDown: 'dropDown 1s ease',
      },
    },
  },
  plugins: [require('@tailwindcss/line-clamp')],
};

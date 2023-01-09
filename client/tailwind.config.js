/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './pages/**/*.{js,ts,jsx,tsx}',
    './components/**/*.{js,ts,jsx,tsx}',
  ],
  theme: {
    extend: {
      colors: {
        mainColor: '#F4CAF8',
        subColor: '#9DE3B2',
        textColor: 'white',
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
      },
      animation: {
        bookMark: 'bookMark 1s ease-in-out ',
      },
    },
  },
  plugins: [],
};

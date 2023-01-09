/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './pages/**/*.{js, jsx, ts, tsx}',
    './components/**/*.{js, jsx, ts, tsx}',
  ],
  theme: {
    extend: {
      fontFamily: {
        sans: ['Noto Sans KR', 'sans-serif'],
      },
    },
  },
  plugins: [require('@tailwindcss/line-clamp')],
};

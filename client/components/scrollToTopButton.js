import { FaArrowUp } from 'react-icons/fa';
import { useState, useEffect, useCallback } from 'react';
export function ScrollToTopButton() {
  const [showTopBtn, setShowTopBtn] = useState(false);
  useEffect(() => {
    const showTopBtnFunc = () => {
      if (window.scrollY > 500) {
        setShowTopBtn(true);
      } else {
        setShowTopBtn(false);
      }
    };
    window.addEventListener('scroll', () => showTopBtnFunc());
    return window.removeEventListener('scroll', () => showTopBtnFunc());
  }, []);
  const scrollToTop = useCallback(() => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  }, []);
  return (
    <div
      className={`${
        showTopBtn === false ? 'hidden' : null
      } w-[40px] h-[40px] rounded-full bg-mainColor flex justify-center items-center z-49 right-4 bottom-16 fixed animate-dropDown`}
      onClick={scrollToTop}
    >
      <FaArrowUp size={25} className="text-iconColor" />
    </div>
  );
}

import { FaArrowUp } from 'react-icons/fa';
export function ScrollToTopButton() {
  const scrollToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };

  return (
    <div
      className="w-[40px] h-[40px] rounded-full bg-mainColor flex justify-center items-center z-49 right-4 bottom-24 fixed"
      onClick={scrollToTop}
    >
      <FaArrowUp size={25} className="text-iconColor" />
    </div>
  );
}

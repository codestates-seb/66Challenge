import { AiOutlineFacebook } from 'react-icons/ai';
import { BsInstagram } from 'react-icons/bs';
import { SlArrowRight } from 'react-icons/sl';

export const Footer = () => {
  return (
    <div className="bg-neutral-100 text-center text-sm">
      <h4 className="text-sm font-bold py-4 dark:text-white">(주)펫프렌즈</h4>
      <div>
        <p className="font-medium text-teal-600">대표이사: 코드스테이츠</p>
        <p className="font-medium text-teal-600">사업자번호: 175-82-00190</p>
        <p className="font-medium text-teal-600">개인정보보호책임자: 배성진</p>
        <div>
          <a className="font-medium text-teal-600 pr-2">이용약관</a>
          <a className="font-medium text-teal-600">개인정보처리방침</a>
        </div>
      </div>
      <div>
        <div className="pt-3">
          <span className="text-teal-600">고객센터: </span>
          <span className="cursor-pointer text-blue-600">031-123-4567</span>
        </div>
        <div>
          <p className="cursor-pointer text-blue-600">codestates@gmail.com </p>
        </div>
      </div>
      <div className="py-2 mx-36">
        <div className="my-2 flex justify-center">
          <span className="cursor-pointer text-gray-pink flex  border-2 rounded-lg px-2">
            제휴 입점 및 기타문의
            <div className="pl-1">
              <SlArrowRight className="w-3 h-3 inline align-middle" />
            </div>
          </span>
        </div>
        <div className="my-2 flex justify-center">
          <span className="cursor-pointer text-gray-pink flex  border-2 rounded-lg px-2">
            펫프렌즈 회사소개
            <div className="pl-1">
              <SlArrowRight className="w-3 h-3 inline align-middle" />
            </div>
          </span>
        </div>
      </div>
      <div className="flex justify-center">
        <AiOutlineFacebook className="cursor-pointer mb-10 mx-2 w-6 h-7 text-gray-pink" />
        <BsInstagram className="cursor-pointer mb-10 mx-2 w-6 h-7 text-gray-pink" />
      </div>
    </div>
  );
};

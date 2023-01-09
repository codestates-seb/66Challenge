import { DropDown } from './dropDown';
import { useEffect } from 'react';
import { AiFillStar } from 'react-icons/ai';
export function ReviewArticle() {
  //필요 데이터 유저네임,score,후기내용,리뷰등록시간, 등록한 유저Id
  useEffect(() => {}, []);
  return (
    <>
      <div className="flex justify-center w-full h-40 py-3 box-border">
        <div className="flex flex-col relative   items-center  w-4/5 h-full  pt-5 pb-1 px-1  ">
          <DropDown className="top-1 " />
          <div className="flex w-3/4 justify-between  mb-2 items-center">
            <span className="text-xs">userName</span>
            <span className="text-xs ">postTime</span>
            <AiFillStar className="text-subColor ml-2" />
            <span className="text-xs ">score</span>
          </div>
          <div className=" w-4/5 h-32 box-border flex flex-wrap p-px border border-mainColor rounded-md py-1 px-1">
            <span className="text-[10px] break-all w-full">
              내 인생은 성공 했어
            </span>
          </div>
        </div>
      </div>
    </>
  );
}

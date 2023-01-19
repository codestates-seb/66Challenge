import { DropDown } from './dropDown';
import { useEffect, useState } from 'react';
import { AiFillStar } from 'react-icons/ai';
import { useAppSelector } from '../ducks/store';
export const ReviewArticle = () => {
  const { userId } = useAppSelector((state) => state.loginIdentity);

  //필요 데이터 유저네임,score,후기내용,리뷰등록시간, 등록한 유저Id
  //등록한 유저 Id와 현재 로그인한 유저 Id가 같다면 editUserBoolean을 true로 만들어 줄 것.
  const [editUserBoolean, setEditUserBoolean] = useState(false);

  useEffect(() => {}, []);
  return (
    <>
      <div className="review-article-container flex justify-center w-full py-5 box-border gap-5">
        <div className="review-article-wrapper flex flex-col relative items-center w-full">
          <div className="review-article-header w-full flex justify-between items-center mb-2 ">
            <div className="flex w-full items-center gap-2.5">
              <span className="text-base">{`userName`}</span>
              <span className="flex gap-1">
                <AiFillStar className="text-subColor" />
                <span className="text-sm ">{`score`}</span>
              </span>
              <span className="text-sm text-[#7d7d7d]">{`postTime`}</span>
            </div>
            <DropDown dropDownType="review" boolean={editUserBoolean} />
          </div>
          <div className=" w-full box-border flex flex-wrap">
            <span className="text-base break-all w-full">
              {`내 인생은 성공 했어`}
            </span>
          </div>
        </div>
      </div>
    </>
  );
};

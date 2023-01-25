import { DropDown } from './dropDown';
import { useEffect, useState } from 'react';
import { AiFillStar } from 'react-icons/ai';
import { useAppSelector } from '../ducks/store';
import { DateFormat } from '../module/dateFormat';

interface reviewArticleProps {
  habitId: number;
  reviewId: number;
  reviewer: string;
  reviewUserId: number;
  body: string;
  createdAt: string;
  score: number;
  lastModifiedAt: string | null;
}

export type { reviewArticleProps };

export const ReviewArticle = ({
  habitId,
  reviewId,
  reviewer,
  reviewUserId,
  body,
  createdAt,
  score,
  lastModifiedAt,
}: reviewArticleProps) => {
  const { userId } = useAppSelector((state) => state.loginIdentity);

  //필요 데이터 유저네임,score,후기내용,리뷰등록시간, 등록한 유저Id
  //등록한 유저 Id와 현재 로그인한 유저 Id가 같다면 editUserBoolean을 true로 만들어 줄 것.
  const [editUserBoolean, setEditUserBoolean] = useState(false);

  useEffect(() => {
    if (userId === reviewUserId) {
      setEditUserBoolean(true);
    } else {
      setEditUserBoolean(false);
    }
  }, []);
  return (
    <>
      <div className="review-article-container flex justify-center w-full py-5 box-border gap-5">
        <div className="review-article-wrapper flex flex-col relative items-center w-full">
          <div className="review-article-header w-full flex justify-between items-center mb-2 ">
            <div className="flex w-full items-center gap-2.5">
              <span className="text-base">{reviewer}</span>
              <span className="flex gap-1">
                <AiFillStar className="text-subColor" />
                <span className="text-sm ">{score}</span>
              </span>
              <span className="text-sm text-[#7d7d7d]">
                {lastModifiedAt
                  ? `${DateFormat(lastModifiedAt)}에 수정됨`
                  : DateFormat(createdAt)}
              </span>
            </div>
            <DropDown
              dropDownType="review"
              boolean={editUserBoolean}
              reviewId={reviewId}
              habitId={habitId}
            />
          </div>
          <div className=" w-full box-border flex flex-wrap">
            <span className="text-base break-all w-full">{body}</span>
          </div>
        </div>
      </div>
    </>
  );
};

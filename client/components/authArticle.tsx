import { DropDown } from './dropDown';
import { useEffect, useState } from 'react';
import Image from 'next/image';
import { useAppSelector } from '../ducks/store';
import { DateFormat } from '../module/dateFormat';

interface authArticleProps {
  habitId: number;
  authId: number;
  author: string;
  authUserId: number;
  body: string;
  createdAt: string;
  authImageUrl: string;
}

export type { authArticleProps };

export function AuthArticle({
  habitId,
  authId,
  author,
  authUserId,
  body,
  createdAt,
  authImageUrl,
}: authArticleProps) {
  const { userId } = useAppSelector((state) => state.loginIdentity);

  //필요 데이터 유저네임,score,후기내용,등록한 유저Id
  //등록한 유저 Id와 현재 로그인한 유저 Id가 같다면 editUserBoolean을 true로 만들어 줄 것.
  const [editUserBoolean, setEditUserBoolean] = useState(false);
  useEffect(() => {
    if (userId === authUserId) {
      setEditUserBoolean(true);
    } else {
      setEditUserBoolean(false);
    }
  }, []);
  return (
    <div className="flex justify-center w-full h-auto py-5 box-border gap-5">
      <div className="flex flex-col relative items-center  w-full">
        <div className="auth-article-header w-full flex justify-between items-center mb-2 ">
          <div className="flex w-full gap-2.5 items-center">
            <span className="text-base">{author}</span>
            <span className="text-sm text-[#7d7d7d]">
              {DateFormat(createdAt)}
            </span>
          </div>
          <DropDown
            dropDownType="auth"
            boolean={editUserBoolean}
            authId={authId}
            habitId={habitId}
          />
        </div>
        <div className="w-full border-solid  mb-2">
          <Image
            className="w-full h-full rounded-md"
            src={authImageUrl}
            alt="auth image"
            width={500}
            height={500}
          />
        </div>
        <div className=" w-full h-1/4 box-border flex flex-wrap p-px">
          <span className="text-base break-all w-full">{body}</span>
        </div>
      </div>
    </div>
  );
}

import { useEffect, useState } from 'react';
import Image from 'next/image';
import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
import { useAppSelector } from '../ducks/store';
import { useRouter } from 'next/router';
import { postBookMark, deleteBookMark } from '../module/habitFunctionMoudules';

interface HabitElementProps {
  thumbImgUrl: string;
  title: string;
  body: string;
  habitId: number;
  isBooked: boolean;
  hostUserId?: number;
}

interface HabitWrapperProps {
  habitWrapperTitle: string;
  habitWrapperData: Array<HabitElementProps>;
}

export type { HabitElementProps, HabitWrapperProps };

export const HabitElement: React.FC<HabitElementProps> = ({
  habitId,
  title,
  body,
  thumbImgUrl,
  isBooked,
  hostUserId,
}) => {
  const router = useRouter();
  const { userId } = useAppSelector((state) => state.loginIdentity);
  const [isBookMark, setIsBookMark] = useState(false);

  const bookMarkHandler = async () => {
    // login 여부 확인 후 false면 로그인 페이지로 경로 설정
    if (!userId) {
      alert('로그인 후에 이용해주세요');
      router.push('/user/login');
      return;
    }
    // 북마크 관련 비동기 요청 함수
    if (isBookMark) {
      await deleteBookMark({ habitId, userId });
    } else {
      await postBookMark({ habitId, userId });
    }

    setIsBookMark(!isBookMark);
  };
  useEffect(() => {
    if (isBooked === true) {
      setIsBookMark(true);
    }
  }, [isBooked]);
  const goDetailPageHandle = () => {
    router.push(`/habit/detail/${habitId}`);
  };
  return (
    <div
      className={`habit-element-wrapper flex flex-col`}
      onClick={goDetailPageHandle}
    >
      <div className="habit-element-image mb-2.5 relative">
        <Image
          src={thumbImgUrl === null ? '/image/running.png' : thumbImgUrl}
          alt="habit image"
          width={500}
          height={500}
          className="w-full aspect-square"
          style={{ objectFit: 'cover' }}
        />
        <div
          className="absolute bottom-0 right-0 p-1"
          onClick={(e) => {
            e.stopPropagation();
            bookMarkHandler();
          }}
        >
          {hostUserId === undefined ||
          hostUserId === userId ? null : isBookMark ? (
            <AiFillHeart className="text-subColor animate-bookMark" />
          ) : (
            <AiOutlineHeart className="text-borderColor" />
          )}
        </div>
      </div>
      <div className="habit-element-title font-bold mb-[5px] text-ellipsis overflow-hidden break-words line-clamp-1">
        {title}
      </div>
      <div className="habit-element-body text-ellipsis overflow-hidden break-words line-clamp-2 ">
        {body}
      </div>
    </div>
  );
};

/* 
* <------  사용하시기 전에 꼭 읽어주세요! ------> *
※ 네트워크 통신을 통해 데이터를 받아오는 방식이 결정되면 아래 코드는 수정될 수 있습니다.
  현재의 코드는 한 번의 네트워크 통신으로 객체 형태의 각 habit에 대한 데이터들이 배열 형태로 server로부터 전달받는 통신 구조를 가정했습니다.

ToDo 1. Habit Element를 사용하는 컴포넌트에서 해당 Habit에 대한 habitImage(이미지), habitTitle, habitBody(body)를 props로 넘겨주셔야 합니다.
ToDo 2. 해당 Element를 사용하는 컴포넌트가 어떤 타입인지에 있어 WrapperType을 props로 넘겨주셔야 합니다.

< example >
<HabitElement
  habitImage="/image/running.png"
  habitTitle="달리기 3km"
  habitBody="매일 아침에 3km 달리기를 실천합니다. 상쾌한 아침을 맞이하며 건강도 챙겨보세요!"
  WrapperType="horizontal"
/>
* <------  사용하시기 전에 꼭 읽어주세요! ------> *
*/
import { useState } from 'react';
import Image from 'next/image';
import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
import { useAppSelector } from '../ducks/store';
import { useRouter } from 'next/router';
import { postBookMark, deleteBookMark } from '../module/habitFunctionMoudules';

interface HabitElementProps {
  habitImage: string;
  title: string;
  body: string;
  habitId: number;
  isBooked: boolean;
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
  habitImage,
  isBooked,
}) => {
  const router = useRouter();
  const { userId, isLogin } = useAppSelector((state) => state.loginIdentity);
  const [isBookMark, setIsBookMark] = useState(isLogin ? isBooked : false);

  const bookMarkHandler = async () => {
    // login 여부 확인 후 false면 로그인 페이지로 경로 설정
    if (!userId) {
      alert('로그인 후에 이용해주세요');
      router.push('/user/login');
    }
    // 북마크 관련 비동기 요청 함수
    if (isBookMark) {
      await deleteBookMark({ habitId, userId });
    } else {
      await postBookMark({ habitId, userId });
    }

    setIsBookMark(!isBookMark);
  };

  return (
    <div className={`habit-element-wrapper`}>
      <div className="habit-element-image mb-2.5 relative">
        <Image src={habitImage} alt="habit image" width={500} height={500} />
        <div
          className="absolute bottom-0 right-0 p-1"
          onClick={bookMarkHandler}
        >
          {isBookMark ? (
            <AiFillHeart className="text-subColor animate-bookMark" />
          ) : (
            <AiOutlineHeart className="text-borderColor" />
          )}
        </div>
      </div>
      <div className="habit-element-title font-bold mb-[5px]">{title}</div>
      <div className="habit-element-body text-ellipsis overflow-hidden break-words line-clamp-2 ">
        {body}
      </div>
    </div>
  );
};

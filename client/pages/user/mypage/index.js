import { useState, useEffect } from 'react';
// import { getCookie } from '../../../module/cookies';
import { useSelector } from 'react-redux';
import axios from 'axios';
import { useRouter } from 'next/router';

export default function MyPage() {
  //   console.log(getCookie('accessJwtToken'));
  const userId = useSelector((state) => state.loginIdentity.userId);
  const [userInfo, setUserInfo] = useState(null);
  const router = useRouter();

  useEffect(() => {
    axios(`${process.env.NEXT_PUBLIC_SERVER_URL}/users/${userId}`).then(
      (data) => {
        setUserInfo(data.data);
        console.log(data.data);
      },
    );
  }, []);

  const handleHabitDetail = (id) => {
    router.push(`/habit/detail/${id}`);
  };
  return (
    <>
      {userInfo && (
        <main className="flex flex-col items-stretch">
          <div className="flex flex-col items-center border-2">
            <div className="mt-2 text-[0.6rem] text-right">
              반가워요!{' '}
              <span className="text-green-600 text-[0.8rem]">
                {userInfo.biggestNumOfChallengeHabitDays}
              </span>
              일째 개근중인
            </div>
            <div className="text-2xl">{userInfo.username} 님</div>
            <div className="text-[0.7rem] text-gray-400 mb-2 ">
              Email : {userInfo.email}
            </div>
          </div>
          <div className="border-2 mt-1">
            <div className="mt-2 ml-2 mb-1 font-semibold">
              나의 습관 진행현황
            </div>
            <div className="border-2 mx-2 h-12 rounded-xl flex flex-nowrap overflow-x-auto">
              {userInfo &&
                userInfo.activeChallenges.map((e) => {
                  // const progress = parseInt((e.authDays / 66) * 100);
                  // const progress = 30;
                  const progress = 114;
                  return (
                    <button
                      onClick={() => handleHabitDetail(e.challengeId)}
                      className="h-[36px] mx-2 rounded-xl my-1 w-36 shrink-0 border p-px border-mainColor flex items-center justify-center max-h-min bg-white relative overflow-hidden z-20"
                    >
                      <div
                        className={` absolute h-[34px] w-[${progress}px] bg-subColor rounded-r-xl left-0 animate-gage z-10 anim`}
                      ></div>
                      <div className="z-30">
                        <span className="text-center ">
                          {e.habitSubTitle}
                          <span className="text-xs">{` (${e.authDays}/66)`}</span>
                        </span>
                      </div>
                    </button>
                  );
                })}
            </div>
          </div>
        </main>
      )}
    </>
  );
}
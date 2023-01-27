import { FC } from 'react';
import { useEffect } from 'react';
import { useRouter } from 'next/router';
import { useForm } from 'react-hook-form';
import { useAppSelector } from '../../../ducks/store';
import { deleteUser } from '../../../module/userFunctionMoudules';

interface WithdrawInfo {}

const Withdraw: FC = () => {
  const userId = useAppSelector((state) => state.loginIdentity.userId);
  const router = useRouter();
  const {} = useForm<WithdrawInfo>();
  useEffect(() => {}, [router.isReady]);
  return (
    <div className="flex-col flex justify-center items-center">
      <img src="/image/logo.svg" className="mt-28 mb-10 h-2/6 w-[50%]" />
      <div className="w-full flex justify-start items-center">
        <input
          type="checkbox"
          onChange={(e) => {
            console.log(e.target.checked);
          }}
          className="ml-20 mr-1"
        />
        <span>정말로 탈퇴하실건가요?</span>
      </div>
      <div className="w-full flex justify-end items-center">
        <span>진짜로 탈퇴하실건가요?</span>
        <input
          type="checkbox"
          onChange={(e) => {
            console.log(e.target.checked);
          }}
          className="mr-20 ml-1"
        />
      </div>
      <span>본인은 탈퇴를 재고 하겠음</span>
      <input type="checkbox" checked={true} />
      <button
        onClick={() => {
          deleteUser({ userId });
        }}
        className="border-2"
      >
        탈퇴하기
      </button>
    </div>
  );
};

export default Withdraw;

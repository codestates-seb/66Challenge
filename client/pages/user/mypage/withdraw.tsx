import { FC, useState } from 'react';
import { useRouter } from 'next/router';
import { useAppSelector, useAppDispatch } from '../../../ducks/store';
import { deleteUser } from '../../../module/userFunctionMoudules';
import { initLoginIdentity } from '../../../ducks/loginIdentitySlice';

const Withdraw: FC = () => {
  const userId = useAppSelector((state) => state.loginIdentity.userId);
  const [withdrawVerify, setWithdrawVerify] = useState([false, false, false]);
  const router = useRouter();
  const dispatch = useAppDispatch();

  return (
    <div className="flex-col flex justify-center items-center">
      <img src="/image/logo.svg" className="mt-28 mb-10 h-2/6 w-[50%]" />
      <div className="w-full flex justify-start items-center">
        <input
          type="checkbox"
          onChange={(e) => {
            const verify = [...withdrawVerify];
            verify[0] = e.target.checked;
            setWithdrawVerify(verify);
          }}
          className="ml-20 mr-1"
        />
        <span>정말로 탈퇴하실건가요?</span>
      </div>
      <div className="w-full flex justify-end items-center mt-4">
        <span>진짜로 탈퇴하실건가요?</span>
        <input
          type="checkbox"
          onChange={(e) => {
            const verify = [...withdrawVerify];
            verify[1] = e.target.checked;
            setWithdrawVerify(verify);
          }}
          className="mr-20 ml-1"
        />
      </div>
      <span className="mt-4">본인은 탈퇴를 다시 생각해보겠음</span>
      <input
        type="checkbox"
        defaultChecked={true}
        onChange={(e) => {
          const verify = [...withdrawVerify];
          verify[2] = !e.target.checked;
          setWithdrawVerify(verify);
        }}
      />
      <button
        onClick={() => {
          deleteUser({ userId }).then(() => {
            dispatch(initLoginIdentity());
            router.push('/');
          });
        }}
        className={`border-2 w-20 h-10 rounded-md mt-[25rem] bg-[#222222] text-white disabled:opacity-20`}
        disabled={withdrawVerify.includes(false)}
      >
        탈퇴하기
      </button>
    </div>
  );
};

export default Withdraw;

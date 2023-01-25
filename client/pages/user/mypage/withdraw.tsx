import { FC } from 'react';
import { useEffect } from 'react';
import { useRouter } from 'next/router';
import { useForm } from 'react-hook-form';
import { useAppSelector } from '../../../ducks/store';

interface WithdrawInfo {}
const Withdraw: FC = () => {
  const userId = useAppSelector((state) => state.loginIdentity.userId);
  const router = useRouter();
  const {} = useForm<WithdrawInfo>();
  useEffect(() => {}, [router.isReady]);
  return (
    <div className="flex-col flex justify-center items-center">
      <img src="/image/logo.svg" className="mt-28 h-2/6 w-[50%]" />
    </div>
  );
};

export default Withdraw;

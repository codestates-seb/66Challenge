import { useForm } from 'react-hook-form';
import React, { useState } from 'react';
import { NextRouter, useRouter } from 'next/router';
import { patchUserInfo } from '../../module/userFunctionMoudules';
import { useAppSelector } from '../../ducks/store';

interface IformValue {
  gender: string;
  age: string;
}
interface IstateValue {
  agreeVerify: string;
  genderVerify: string;
  ageVerify: string;
}
const OauthSignUp: React.FC = () => {
  const router: NextRouter = useRouter();
  const { register, handleSubmit, reset, getValues } = useForm<IformValue>();
  const [verify, setVerify] = useState<IstateValue>({
    agreeVerify: 'fail',
    genderVerify: '',
    ageVerify: '',
  });
  const userId = useAppSelector((state) => state.loginIdentity.userId);

  const inputContainerDefaultClassName: string =
    'flex flex-col w-full h-[80px] mb-5';
  const labelDefaultClassName: string = 'text-base font-semibold mb-1';
  const inputDefaultClassName: string =
    'border h-[35px] text-base w-full rounded-md px-2 pt-[5px] focus:border-mainColor duration-500 outline-0 mb-1';

  const checkHandle = (): void => {
    verify.agreeVerify === 'fail'
      ? setVerify({ ...verify, agreeVerify: 'success' })
      : setVerify({ ...verify, agreeVerify: 'fail' });
  };

  const signUpHandle = (data): void => {
    const { gender, age } = data;
    const formData = new FormData();
    formData.append(
      'body',
      new Blob([JSON.stringify({ gender, age })], {
        type: 'application/json',
      }),
    );

    // console.log({ userId, body: formData });
    console.log(formData.getAll('body'));

    // patchUserInfo({
    //   userId,
    //   body: {
    //     gender,
    //     age,
    //   },
    // });
  };

  return (
    <div className="siginUpContatiner w-full h-screen flex flex-col px-10 items-center justify-center mx-auto">
      <img src="/image/logo.svg" className="mb-[40px]" />
      <form
        className="signUpForm w-full flex flex-col items-center"
        onSubmit={handleSubmit(signUpHandle)}
      >
        <div className={labelDefaultClassName}>추가정보 입력</div>
        <div className="border py-3 flex flex-col items-center mb-6 px-3 rounded-md w-3/4">
          <div id="ageInputForm">
            <label htmlFor="ageInput" className={`${labelDefaultClassName}`}>
              나이 :
            </label>
            <input
              id="ageInput"
              type="number"
              className="border w-16 mx-2 text-center h-6 rounded-md"
              min={1}
              max={120}
              placeholder="입력"
              {...register('age')}
              onChange={(e) => {
                if (
                  Number(e.target.value) < 1 ||
                  Number(e.target.value) > 120
                ) {
                  e.target.value = null;
                  setVerify({ ...verify, ageVerify: 'fail' });
                } else {
                  setVerify({ ...verify, ageVerify: 'success' });
                }
              }}
            />
            <input
              id="genderMale"
              type="radio"
              name="gender"
              value="male"
              onClick={(e) => {
                setVerify({ ...verify, genderVerify: 'success' });
              }}
              className="mx-1 border"
              {...register('gender')}
            />
            <label className="mx-1 mt-1">남성</label>

            <input
              id="genderFemale"
              type="radio"
              name="gender"
              value="female"
              onClick={(e) => {
                setVerify({ ...verify, genderVerify: 'success' });
              }}
              className="mx-1"
              {...register('gender')}
            />
            <label className="mx-1 mt-1">여성</label>
          </div>
        </div>

        <div className="flex items-center w-full mb-6">
          <input
            id="agreeCheck"
            type="checkbox"
            className="w-5 h-5 mr-3 accent-subColor"
            onClick={checkHandle}
          />
          <label
            htmlFor="agreeCheck"
            className="block text-mainColor text-base font-semibold "
          >
            (필수) 본인은 66일 습관 챌린지 준비가 되었다.
          </label>
        </div>
        <input
          type="submit"
          value="Sign Up"
          className="border py-2.5 px-5 text-base font-semibold w-full rounded-md bg-mainColor text-iconColor duration-500 outline-0 mb-1 disabled:opacity-20"
          disabled={
            !Object.values(verify).every(
              (el: string): boolean => el === 'success',
            )
          }
        />
      </form>
      <button onClick={signUpHandle}>dddd</button>
    </div>
  );
};
export default OauthSignUp;

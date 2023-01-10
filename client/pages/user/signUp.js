import { useForm } from 'react-hook-form';
import { useState } from 'react';
import { useRouter } from 'next/router';

export default function SignUp() {
  const router = useRouter();
  const { register, handleSubmit, reset, getValues } = useForm();
  const [verify, setVerify] = useState({
    emailVerify: '',
    userNameVerify: '',
    passwordVerify: '',
    passwordCheckVerify: '',
    agreeVerify: 'fail',
  });

  const [itemsClassName, setItemsClassName] = useState({
    emailInput:
      'border h-[30px] text-[12px] w-full rounded-md px-2 focus:border-mainColor duration-500 outline-0 mb-1',
    userNameInput:
      'border h-[30px] text-[12px] w-full rounded-md px-2 focus:border-mainColor duration-500 outline-0 mb-1',
    passwordInput:
      'border h-[30px] text-[12px] w-full rounded-md px-2 focus:border-mainColor duration-500 outline-0 mb-1',
    passwordCheckInput:
      'border h-[30px] text-[12px] w-full rounded-md px-2 focus:border-mainColor duration-500 outline-0 mb-1',
  });
  const inputDefaultClassName =
    'border h-[30px] text-[12px] w-full rounded-md px-2 focus:border-mainColor duration-500 outline-0 mb-1';
  const inputFailClassName =
    'border border-subColor h-[30px] text-[12px] w-full rounded-md px-2 focus:border-mainColor duration-500 outline-0 mb-1';
  const passwordRegExp =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,12}$/;
  const emailRegExp =
    /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
  const userNameRegExp = /[A-Za-z0-9가-힇]{2,20}/;
  const blurHandle = (verifyBoolean, verifyKey, type) => {
    if (verifyBoolean) {
      //   if (verifyKey === 'emailVerify' || verifyKey === 'userNameVerify') {
      //     //중복검사 호출
      //     //중복 검사 확인 비동기 호출 후 통과하지 못하면
      //     //     setVerify({ ...verify, [verifyKey]: 'overlap' });
      //     //   setItemsClassName({
      //     //     ...itemsClassName,
      //     //     [type]: inputFailClassName,
      //     //   });
      //   } else {
      setVerify({ ...verify, [verifyKey]: 'success' });
      setItemsClassName({
        ...itemsClassName,
        [type]: inputDefaultClassName,
      });
      //   }
    } else {
      setVerify({ ...verify, [verifyKey]: 'fail' });
      setItemsClassName({
        ...itemsClassName,
        [type]: inputFailClassName,
      });
    }
  };
  const checkHandle = () => {
    verify.agreeVerify === 'fail'
      ? setVerify({ ...verify, agreeVerify: 'success' })
      : setVerify({ ...verify, agreeVerify: 'fail' });
  };
  const signUpHandle = (data) => {
    const { userEmail, userName, password } = data;
    //회원 가입 비동기 함수 호출 부분 에러가 없다면 로그인 페이지로 연동할 것 그 후 리셋
    //reset();
    console.log(data);
  };
  const onError = (e) => {
    console.log(e);
  };
  return (
    <div className="siginUpContatiner w-full h-screen flex flex-col items-center justify-center px-5">
      <img src="/image/logo.svg" className="mb-6" />
      <form
        className="signUpForm w-full flex flex-col items-center"
        onSubmit={handleSubmit(signUpHandle, onError)}
      >
        <div className="signUpUserEmailBox flex flex-col w-full h-16 mb-3 ">
          <label
            className="text-[12px] h-[12px] font-semibold  mb-2"
            htmlFor="userEmail"
          >
            이메일
          </label>

          <input
            className={itemsClassName.emailInput}
            id="userEmail"
            placeholder="example@example.com"
            {...register('userEmail', {
              onBlur: () =>
                blurHandle(
                  emailRegExp.test(getValues('userEmail')),
                  'emailVerify',
                  'emailInput',
                ),
            })}
            required
          />
          {verify.emailVerify === 'fail' ? (
            <span className="block text-subColor text-[10px] h-[10px] ">
              올바른 이메일 주소를 입력해주세요.
            </span>
          ) : verify.emailVerify === 'overlap' ? (
            <span className="block text-subColor text-[10px] h-[10px] ">
              중복된 이메일 주소 입니다.
            </span>
          ) : null}
        </div>
        <div className="signUpUserNameBox flex flex-col w-full h-16 mb-3 ">
          <label
            className="text-[12px] h-[12px] font-semibold  mb-2"
            htmlFor="userName"
          >
            닉네임
          </label>

          <input
            className={itemsClassName.userNameInput}
            id="userName"
            placeholder="2~20자 이내로 입력해주세요"
            {...register('userName', {
              onBlur: () =>
                blurHandle(
                  userNameRegExp.test(getValues('userName')),
                  'userNameVerify',
                  'userNameInput',
                ),
            })}
            required
          />
          {verify.userNameVerify === 'fail' ? (
            <span className="block text-subColor text-[10px] h-[10px] ">
              닉네임은 영어/한국어/숫자 중 사용하여 2~20자 입니다.
            </span>
          ) : verify.userNameVerify === 'overlap' ? (
            <span className="block text-subColor text-[10px] h-[10px] ">
              중복된 닉네임 입니다.
            </span>
          ) : null}
        </div>
        <div className="signUpPasswordBox flex flex-col w-full h-16 mb-3 ">
          <label
            className="text-[12px] h-[12px] font-semibold  mb-2"
            htmlFor="password"
          >
            비밀번호
          </label>

          <input
            className={itemsClassName.passwordInput}
            id="password"
            placeholder="영문/숫자/특수문자 혼합 8~12자"
            type="password"
            {...register('password', {
              onBlur: () =>
                blurHandle(
                  passwordRegExp.test(getValues('password')),
                  'passwordVerify',
                  'passwordInput',
                ),
            })}
            required
          />
          {verify.passwordVerify === 'fail' ? (
            <span className="block text-subColor text-[10px] h-[10px] justify-self-end">
              영문/숫자/특수문자 혼합 8~12자 입니다.
            </span>
          ) : null}
        </div>
        <div className="signUpPasswordCheckBox flex flex-col w-full h-16 mb-4">
          <label
            className="text-[12px] h-[12px] font-semibold  mb-2"
            htmlFor="passwordCheck"
          >
            비밀번호 확인
          </label>
          <input
            className={itemsClassName.passwordCheckInput}
            id="passwordCheck"
            placeholder="비밀번호를 한번 더 입력해주세요"
            type="password"
            {...register('passwordCheck', {
              onBlur: () =>
                blurHandle(
                  getValues('password') === getValues('passwordCheck'),
                  'passwordCheckVerify',
                  'passwordCheckInput',
                ),
            })}
            required
          />
          {verify.passwordCheckVerify === 'fail' ? (
            <span className="block text-subColor text-[10px] h-[10px] ">
              비밀번호가 일치하지 않습니다.
            </span>
          ) : null}
        </div>

        <div className="flex items-center w-full mb-6">
          <input
            type="checkbox"
            className="w-5 h-5 rounded-full border mr-3 accent-subColor"
          />
          <span className="block text-mainColor text-[12px] font-semibold ">
            (필수) 본인은 66일 습관 챌린지 준비가 되었다.
          </span>
        </div>
        <input
          type="submit"
          value="Sign Up"
          className="border h-[35px] text-[14px] font-semibold w-full rounded-md px-2 bg-mainColor text-iconColor duration-500 outline-0 mb-1 disabled:opacity-20"
          disabled={!Object.values(verify).every((el) => el === 'success')}
        />
      </form>
    </div>
  );
}

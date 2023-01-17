import { useForm } from 'react-hook-form';
import { useState } from 'react';
import { useRouter } from 'next/router';
import {
  getUserEmailOverlapVerify,
  getUsernameOverlapVerify,
  postUserSignUp,
} from '../../module/userFunctionMoudules';

export default function SignUp() {
  const router = useRouter();
  const { register, handleSubmit, reset, getValues } = useForm();
  const [verify, setVerify] = useState({
    emailVerify: '',
    usernameVerify: '',
    passwordVerify: '',
    passwordCheckVerify: '',
    agreeVerify: 'fail',
  });
  const inputDefaultClassName =
    'border h-[35px] text-base w-full rounded-md px-2 pt-[5px] focus:border-mainColor duration-500 outline-0 mb-1';
  const passwordRegExp =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,12}$/;
  const emailRegExp =
    /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*[.][a-zA-Z]{2,3}$/;
  const usernameRegExp = /[A-Za-z0-9가-힇]{2,20}/;
  const blurHandle = async (verifyBoolean, verifyKey) => {
    if (verifyBoolean) {
      if (verifyKey === 'emailVerify') {
        const response = await getUserEmailOverlapVerify(getValues('email'));
        console.log(response);
        if (response === true) {
          setVerify({ ...verify, [verifyKey]: 'overlap' });
        } else {
          setVerify({ ...verify, [verifyKey]: 'success' });
        }
      } else if (verifyKey === 'usernameVerify') {
        const response = await getUsernameOverlapVerify(getValues('username'));
        if (response === true) {
          setVerify({ ...verify, [verifyKey]: 'overlap' });
        } else {
          setVerify({ ...verify, [verifyKey]: 'success' });
        }
      } else {
        setVerify({ ...verify, [verifyKey]: 'success' });
      }
    } else {
      setVerify({ ...verify, [verifyKey]: 'fail' });
    }
  };
  const checkHandle = () => {
    verify.agreeVerify === 'fail'
      ? setVerify({ ...verify, agreeVerify: 'success' })
      : setVerify({ ...verify, agreeVerify: 'fail' });
  };
  const signUpHandle = async (data) => {
    const { email, username, password, passwordCheck } = data;

    if (emailRegExp.test(email) === false) {
      setVerify({ ...verify, emailVerify: 'fail' });
    }
    if (usernameRegExp.test(username) === false) {
      setVerify({ ...verify, usernameVerify: 'fail' });
    }
    if (passwordRegExp.test(password) === false) {
      setVerify({ ...verify, passwordVerify: 'fail' });
    }
    if (password !== passwordCheck) {
      setVerify({ ...verify, passwordCheckVerify: 'fail' });
    } else {
      //회원 가입 비동기 함수 호출 부분 에러가 없다면 로그인 페이지로 연동할 것 그 후 리셋
      const response = await postUserSignUp({ email, password, username });
      if (response === 201) {
        router.push('/user/login');
      } else {
        alert('준비 중 입니다...');
      }
    }
  };
  const onError = (e) => {
    console.log(e);
  };
  const labelDefaultClassName = 'text-base font-semibold mb-1';
  return (
    <div className="siginUpContatiner w-full h-screen flex flex-col px-10 items-center justify-center mx-auto">
      <img src="/image/logo.svg" className="mb-[40px]" />
      <form
        className="signUpForm w-full flex flex-col items-center"
        onSubmit={handleSubmit(signUpHandle, onError)}
      >
        <div className="signUpemailBox flex flex-col w-full h-[80px] mb-5 ">
          <label className={labelDefaultClassName} htmlFor="email">
            이메일
          </label>
          <input
            className={`${inputDefaultClassName} ${
              verify.emailVerify === 'fail' ? 'border-subColor' : null
            }`}
            id="email"
            placeholder="example@example.com"
            {...register('email', {
              onBlur: () =>
                blurHandle(emailRegExp.test(getValues('email')), 'emailVerify'),
            })}
            required
          />
          {verify.emailVerify === 'fail' ? (
            <span className="block text-subColor text-[13px] h-[13px] ">
              올바른 이메일 주소를 입력해주세요.
            </span>
          ) : verify.emailVerify === 'overlap' ? (
            <span className="block text-subColor text-[13px] h-[13px] ">
              중복된 이메일 주소 입니다.
            </span>
          ) : null}
        </div>
        <div className="signUpusernameBox flex flex-col w-full h-[80px] mb-5 ">
          <label className={labelDefaultClassName} htmlFor="username">
            닉네임
          </label>
          <input
            className={`${inputDefaultClassName} ${
              verify.usernameVerify === 'fail' ? 'border-subColor' : null
            }`}
            id="username"
            placeholder="2~20자 이내로 입력해주세요"
            {...register('username', {
              onBlur: () =>
                blurHandle(
                  usernameRegExp.test(getValues('username')),
                  'usernameVerify',
                ),
            })}
            required
          />
          {verify.usernameVerify === 'fail' ? (
            <span className="block text-subColor text-[13px] h-[13px] ">
              닉네임은 영어/한국어/숫자 중 사용하여 2~20자 입니다.
            </span>
          ) : verify.usernameVerify === 'overlap' ? (
            <span className="block text-subColor text-[13px] h-[13px] ">
              중복된 닉네임 입니다.
            </span>
          ) : null}
        </div>
        <div className="signUpPasswordBox flex flex-col w-full h-[80px] mb-5 ">
          <label className={labelDefaultClassName} htmlFor="password">
            비밀번호
          </label>
          <input
            className={`${inputDefaultClassName} ${
              verify.passwordVerify === 'fail' ? 'border-subColor' : null
            }`}
            id="password"
            placeholder="영문/숫자/특수문자 혼합 8~12자"
            autoComplete="off"
            type="password"
            {...register('password', {
              onBlur: () =>
                blurHandle(
                  passwordRegExp.test(getValues('password')),
                  'passwordVerify',
                ),
            })}
            required
          />
          {verify.passwordVerify === 'fail' ? (
            <span className="block text-subColor text-[13px] h-[13px] justify-self-end">
              영문/숫자/특수문자 혼합 8~12자 입니다.
            </span>
          ) : null}
        </div>
        <div className="signUpPasswordCheckBox flex flex-col w-full h-[80px] mb-4">
          <label className={labelDefaultClassName} htmlFor="passwordCheck">
            비밀번호 확인
          </label>
          <input
            className={`${inputDefaultClassName} ${
              verify.passwordCheckVerify === 'fail' ? 'border-subColor' : null
            }`}
            id="passwordCheck"
            placeholder="비밀번호를 한번 더 입력해주세요"
            autoComplete="off"
            type="password"
            {...register('passwordCheck', {
              onBlur: () =>
                blurHandle(
                  getValues('password') === getValues('passwordCheck'),
                  'passwordCheckVerify',
                ),
            })}
            required
          />
          {verify.passwordCheckVerify === 'fail' ? (
            <span className="block text-subColor text-[13px] h-[13px] ">
              비밀번호가 일치하지 않습니다.
            </span>
          ) : null}
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
          disabled={!Object.values(verify).every((el) => el === 'success')}
        />
      </form>
    </div>
  );
}

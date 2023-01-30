import { useForm } from 'react-hook-form';
import React, { useState } from 'react';
import { NextRouter, useRouter } from 'next/router';
import {
  getUserEmailOverlapVerify,
  getUsernameOverlapVerify,
  postUserSignUp,
  postUserEmailAuth,
} from '../../module/userFunctionMoudules';
import { Modal } from '../../components/modal';
interface IformValue {
  email: string;
  username: string;
  password: string;
  passwordCheck: string;
  gender: string;
  age: string;
}
interface IstateValue {
  emailVerify: string;
  usernameVerify: string;
  passwordVerify: string;
  passwordCheckVerify: string;
  agreeVerify: string;
  genderVerify: string;
  ageVerify: string;
}
const SignUp: React.FC = () => {
  const router: NextRouter = useRouter();
  const { register, handleSubmit, reset, getValues } = useForm<IformValue>();
  const [verify, setVerify] = useState<IstateValue>({
    emailVerify: '',
    usernameVerify: '',
    passwordVerify: '',
    passwordCheckVerify: '',
    agreeVerify: 'fail',
    genderVerify: '',
    ageVerify: '',
  });
  const [authState, setAuthState] = useState('');
  const [emailInputDisable, setEmailInputDisable] = useState(false);
  const [isOpen, setIsOpen] = useState(false);
  const inputContainerDefaultClassName: string =
    'flex flex-col w-full h-[80px] mb-5';
  const labelDefaultClassName: string = 'text-base font-semibold mb-1';
  const inputDefaultClassName: string =
    'border h-[35px] text-base w-full rounded-md px-2 pt-[5px] focus:border-mainColor duration-500 outline-0 mb-1';
  const passwordRegExp: RegExp =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,12}$/;
  const emailRegExp: RegExp =
    /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*[.][a-zA-Z]{2,3}$/;
  const usernameRegExp: RegExp = /[A-Za-z0-9가-힇]{2,20}/;
  const blurHandle = async (verifyBoolean: boolean, verifyKey: string) => {
    if (verifyBoolean) {
      if (verifyKey === 'emailVerify') {
        const response: boolean = await getUserEmailOverlapVerify(
          getValues('email'),
        );
        if (response === true) {
          setVerify({ ...verify, [verifyKey]: 'overlap' });
          setIsOpen(false);
        } else if (response === false) {
          setVerify({ ...verify, [verifyKey]: 'success' });
          setIsOpen(true);
        }
      } else if (verifyKey === 'usernameVerify') {
        const response: boolean = await getUsernameOverlapVerify(
          getValues('username'),
        );
        if (response === true) {
          setVerify({ ...verify, [verifyKey]: 'overlap' });
        } else if (response === false) {
          setVerify({ ...verify, [verifyKey]: 'success' });
        }
      } else {
        setVerify({ ...verify, [verifyKey]: 'success' });
      }
    } else {
      setVerify({ ...verify, [verifyKey]: 'fail' });
    }
  };
  const checkHandle = (): void => {
    verify.agreeVerify === 'fail'
      ? setVerify({ ...verify, agreeVerify: 'success' })
      : setVerify({ ...verify, agreeVerify: 'fail' });
  };
  const signUpHandle = async (data: IformValue): Promise<void> => {
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
      const response: number = await postUserSignUp({
        email,
        password,
        username,
      });
      if (response === 201) {
        router.push('/user/login');
        reset();
      } else if (response === 404) {
        setAuthState('none');
        setTimeout(() => {
          setAuthState('');
        }, 1500);
      } else {
        alert('준비 중 입니다...');
      }
    }
  };

  return (
    <div className="siginUpContatiner w-full h-screen flex flex-col px-10 items-center justify-center mx-auto relative">
      <img src="/image/logo.svg" className="mb-[40px]" />
      <form
        className="signUpForm w-full flex flex-col items-center relative"
        onSubmit={handleSubmit(signUpHandle)}
      >
        <div className={inputContainerDefaultClassName}>
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
              onBlur: (): void => {
                blurHandle(emailRegExp.test(getValues('email')), 'emailVerify');
              },
            })}
            disabled={emailInputDisable === true}
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
        <div className={inputContainerDefaultClassName}>
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
              onBlur: (): void => {
                blurHandle(
                  usernameRegExp.test(getValues('username')),
                  'usernameVerify',
                );
              },
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
        <div className={inputContainerDefaultClassName}>
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
              onBlur: (): void => {
                blurHandle(
                  passwordRegExp.test(getValues('password')),
                  'passwordVerify',
                );
              },
            })}
            required
          />
          {verify.passwordVerify === 'fail' ? (
            <span className="block text-subColor text-[13px] h-[13px] justify-self-end">
              영문/숫자/특수문자 혼합 8~12자 입니다.
            </span>
          ) : null}
        </div>
        <div className={inputContainerDefaultClassName}>
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
              onBlur: (): void => {
                blurHandle(
                  getValues('password') === getValues('passwordCheck'),
                  'passwordCheckVerify',
                );
              },
            })}
            required
          />
          {verify.passwordCheckVerify === 'fail' ? (
            <span className="block text-subColor text-[13px] h-[13px] ">
              비밀번호가 일치하지 않습니다.
            </span>
          ) : null}
        </div>
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
              onBlur={(e) => {
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
      {isOpen === true ? (
        <Modal
          isOpen={isOpen}
          setIsOpen={setIsOpen}
          buttonName="발송하기"
          onClick={async () => {
            await postUserEmailAuth(getValues('email'));
            setIsOpen(false);
            setEmailInputDisable(true);
          }}
        >
          <span>
            작성하신 이메일로 발송된 메일을 인증하셔야 회원가입이 가능합니다.
          </span>
        </Modal>
      ) : null}
      <div
        className={`${
          authState === 'none' ? 'flex' : 'hidden'
        } absolute w-3/4 bg-white border-2 border-subColor top-20 rounded-full justify-center h-10 items-center animate-dropDown`}
      >
        <span className="text-subColor font-semibold">
          이메일 인증을 하셔야 합니다!
        </span>
      </div>
    </div>
  );
};
export default SignUp;

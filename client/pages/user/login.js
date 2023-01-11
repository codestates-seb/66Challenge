import { useForm } from 'react-hook-form';
import { useRouter } from 'next/router';
import { useState } from 'react';
import { BsFillEyeFill, BsFillEyeSlashFill } from 'react-icons/bs';

const Login = () => {
  const { register, handleSubmit, getValues, setFocus } = useForm();
  const router = useRouter();

  const [emailVerify, setEmailVerify] = useState(true);
  const [passwordView, setPasswordView] = useState(false);
  const emailRegExp =
    /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*[.][a-zA-Z]{2,3}$/i;

  const emailVerifyOnBlur = (verifyBoolean) => {
    if (verifyBoolean) {
      setEmailVerify(true);
      setFocus('password');
      return;
    }
    setEmailVerify(false);
  };

  const passwordTypeChange = () => {
    setPasswordView(!passwordView);
  };

  const loginButtonClick = (data) => {
    // ajax를 통해 server에 보낼 데이터
    if (emailVerify && data.email && data.password) {
      console.log(data);
      setEmailVerify(false);
      setPasswordView(false);
    } else if (!emailVerify || !data.email) {
      alert('아이디와 비밀번호를 정확하게 입력해주세요.');
      setFocus('email');
    } else {
      alert('아이디와 비밀번호를 정확하게 입력해주세요.');
      setFocus('password');
    }
  };

  const signupButtonClick = () => {
    router.push('/user/signUp');
  };

  const emailInputElKeyEvent = (key) => {
    if (['Enter', 'Tab'].includes(key)) {
      setFocus('password');
    }
  };

  const speechBubleBefore =
    'before:absolute before:bottom-[-12px] before:border-[5px] before:border-transparent before:left-2/4 before:border-t-[7px] before:border-t-mainColor before:translate-y-[-100%] before:border-b-0 before:translate-x-[-50%]';
  const speechBubleAfter =
    'after:absolute after:bottom-[-12px] after:border-[5px] after:border-transparent after:left-2/4 after:border-t-[7px] after:border-t-mainColor after:translate-y-[-100%] after:border-b-0 after:translate-x-[-50%]';

  return (
    <div className="login-container w-[300px] h-screen mx-auto flex flex-col justify-center items-center pb-[40px]">
      <div className="logo flex justify-center w-full mb-[40px]">
        <img src="/image/logo/logoVertical.svg" />
      </div>
      <div
        className={`speech-bubble relative bg-mainColor text-white px-[14px] py-[6px] text-[12px] leading-[14px] rounded-[12px] animate-speechBubble mb-[10px] ${speechBubleBefore} ${speechBubleAfter}`}
      >
        5초만에 빠른 회원가입
      </div>
      <div className="sns-login w-full flex justify-evenly pb-10 mb-10 border-b border-[#e5e5e5] ">
        <div className="google-login">
          <img src="/image/logo/google.svg" />
        </div>
        <div className="kakao-login">
          <img src="/image/logo/kakao.svg" />
        </div>
        <div className="naver-login">
          <img src="/image/logo/naver.svg" />
        </div>
      </div>
      <div className="login-form-wrapper w-full">
        <form
          className="login-form"
          onSubmit={handleSubmit((data) => {
            try {
              loginButtonClick(data);
            } catch (err) {
              console.error(err);
            }
          })}
        >
          <div className="email-input-wrapper flex flex-col mb-5 h-[80px]">
            <label htmlFor="email" className="text-base font-semibold mb-1">
              이메일
            </label>
            <input
              type="text"
              id="email"
              className={`h-[35px] text-base w-full rounded-md px-2 pt-[5px] ${
                emailVerify ? 'border' : 'border-subColor border-2'
              } focus:border-mainColor duration-500 outline-0 mb-1`}
              placeholder="이메일을 입력해주세요."
              onKeyUp={(e) => {
                emailInputElKeyEvent(e.key);
              }}
              {...register('email', {
                onBlur: () =>
                  emailVerifyOnBlur(emailRegExp.test(getValues('email'))),
              })}
            ></input>
            {emailVerify || (
              <span className="block text-subColor text-[13px] h-[13px]">
                올바른 이메일 주소를 입력해주세요.
              </span>
            )}
          </div>
          <div className="password-input-wrapper flex flex-col mb-5 h-[80px]">
            <label htmlFor="password" className="text-base font-semibold mb-1">
              비밀번호
            </label>
            <div className="relative">
              <input
                type={passwordView ? 'text' : 'password'}
                id="password"
                className={`border h-[35px] text-base w-full rounded-md px-2 pt-[5px] pr-[30px] focus:border-mainColor duration-500 outline-0 mb-1`}
                {...register('password')}
                autoComplete="on"
                placeholder="비밀번호를 입력해주세요"
              />
              <div
                className="password-type-change absolute bottom-[13px] right-[10px]"
                onClick={passwordTypeChange}
              >
                {passwordView ? <BsFillEyeSlashFill /> : <BsFillEyeFill />}
              </div>
            </div>
          </div>
        </form>
        <div className="button-wrapper flex justify-between">
          <button
            className="signup-button w-[120px] text-base py-2.5 px-5 border rounded"
            onClick={signupButtonClick}
          >
            Sign Up
          </button>
          <button
            className="login-button w-[120px] text-base bg-[#222222] text-white py-2.5 px-5 border rounded"
            type="submit"
          >
            Login
          </button>
        </div>
      </div>
    </div>
  );
};

export default Login;

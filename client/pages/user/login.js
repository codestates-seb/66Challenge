import { useForm } from 'react-hook-form';
import { useRouter } from 'next/router';
import { BsFillEyeFill, BsFillEyeSlashFill } from 'react-icons/bs';

const Login = () => {
  const { register, handleSubmit } = useForm();

  const loginButtonClick = () => {};

  const signupButtonClick = () => {};

  return (
    <div className="login-container w-[300px] mx-auto flex flex-col justify-center items-center">
      <div className="logo flex justify-center w-full mb-10">
        <img src="/image/logo/logoVertical.svg" />
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
        <form className="login-form">
          <div className="email-input-wrapper flex flex-col mb-2.5">
            <label htmlFor="email" className="text-l font-bold">
              e-mail
            </label>
            <input
              type="text"
              id="email"
              className="w-full border h-14"
            ></input>
          </div>
          <div className="password-input-wrapper flex flex-col mb-2.5">
            <label htmlFor="password" className="text-l font-bold">
              password
            </label>
            <input type="text" id="password" className=" w-full border h-14" />
          </div>
          <div className="button-wrapper flex justify-between">
            <button
              className="signup-button w-[100px] py-2.5 px-5 border rounded"
              onClick={signupButtonClick}
            >
              Sign Up
            </button>
            <button
              className="login-button w-[100px] bg-[#222222] text-white py-2.5 px-5 border rounded"
              onClick={loginButtonClick}
            >
              Login
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Login;

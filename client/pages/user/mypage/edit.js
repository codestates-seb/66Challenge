import { useForm } from 'react-hook-form';
import { useState } from 'react';
import { useRouter } from 'next/router';
import { MdExpandMore } from 'react-icons/md';

export default function SignUp() {
  const router = useRouter();
  const { register, handleSubmit, reset, getValues } = useForm({
    defaultValues: { userName: '', password: '', passwordCheck: '' },
  });
  const [verify, setVerify] = useState({
    userNameVerify: '',
    passwordVerify: '',
    passwordCheckVerify: '',
    agreeVerify: 'fail',
  });
  const [changeUserInfo, setChangeUserInfo] = useState({
    userName: { boolean: false, className: '' },
    password: { boolean: false, className: '' },
  });
  const inputDefaultClassName =
    'border h-[35px] text-base w-full rounded-md px-2 pt-[5px] focus:border-mainColor duration-500 outline-0 mb-1';
  const labelDefaultClassName =
    'text-base flex w-full font-semibold mb-1 justify-between';
  const passwordRegExp =
    /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,12}$/;
  const userNameRegExp = /[A-Za-z0-9가-힇]{2,20}/;
  const upArrow = 'rotate-180 duration-500';
  const downArrow = 'rotate-0';
  const arrowDirectionHandle = (type) => {
    const verifyBoolean = type + 'Verify';
    if (changeUserInfo[type].boolean === false) {
      setChangeUserInfo({
        ...changeUserInfo,
        [type]: { boolean: true, className: upArrow },
      });
    } else {
      setChangeUserInfo({
        ...changeUserInfo,
        [type]: { boolean: false, className: downArrow },
      });

      setVerify({ ...verify, [verifyBoolean]: '' });
      reset({ [type]: '' });
    }
  };
  const blurHandle = (verifyBoolean, verifyKey) => {
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

      //   }
    } else {
      setVerify({ ...verify, [verifyKey]: 'fail' });
    }
  };
  const checkHandle = () => {
    verify.agreeVerify === 'fail'
      ? setVerify({ ...verify, agreeVerify: 'success' })
      : setVerify({ ...verify, agreeVerify: 'fail' });
  };
  const editUserInfoHandle = (data) => {
    const { userName, password, passwordCheck } = data;
    //회원 가입 비동기 함수 호출 부분 에러가 없다면 로그인 페이지로 연동할 것 그 후 리셋
    if (userName === '') {
      //password만 보낼 것
    } else if (password === '') {
      //userName만 보낼 것
    }
    if (userNameRegExp.test(userName) === false && userName !== '') {
      setVerify({ ...verify, userNameVerify: 'fail' });
    }
    if (passwordRegExp.test(password) === false && password !== '') {
      setVerify({ ...verify, passwordVerify: 'fail' });
    }
    if (password !== passwordCheck) {
      setVerify({ ...verify, passwordCheckVerify: 'fail' });
    } else {
      //회원 가입 비동기 함수 호출 부분 에러가 없다면 로그인 페이지로 연동할 것 그 후 리셋
      //reset();
    }
    console.log(data);
  };
  const onError = (e) => {
    console.log(e);
  };
  return (
    <div className="siginUpContatiner w-full h-screen flex flex-col px-10 items-center pt-20  mx-auto relative pb-[70px]">
      <img src="/image/logo.svg" className="mb-[40px]" />
      <form
        className="signUpForm w-full flex flex-col items-center"
        onSubmit={handleSubmit(editUserInfoHandle, onError)}
      >
        <div
          className={`signUpPasswordBox flex flex-col w-full ${
            changeUserInfo.userName.boolean === false
              ? 'h-auto mb-1'
              : 'h-[80px] mb-5'
          }`}
        >
          <label
            className={labelDefaultClassName}
            onClick={() => arrowDirectionHandle('userName')}
          >
            {changeUserInfo.userName.boolean === false
              ? '닉네임 변경'
              : '새 닉네임'}
            <MdExpandMore className={changeUserInfo.userName.className} />
          </label>
          {changeUserInfo.userName.boolean === false ? null : (
            <input
              className={`${inputDefaultClassName} ${
                verify.userNameVerify === 'fail' ? 'border-subColor' : null
              }`}
              placeholder="2~20자 이내로 입력해주세요"
              {...register('userName', {
                onBlur: () => {
                  blurHandle(
                    userNameRegExp.test(getValues('userName')),
                    'userNameVerify',
                  );
                },
              })}
              required
            />
          )}

          {verify.userNameVerify === 'fail' ? (
            <span className="block text-subColor text-[13px] h-[13px] ">
              닉네임은 영어/한국어/숫자 중 사용하여 2~20자 입니다.
            </span>
          ) : verify.userNameVerify === 'overlap' ? (
            <span className="block text-subColor text-[13px] h-[13px] ">
              중복된 닉네임 입니다.
            </span>
          ) : null}
        </div>
        <div
          className={`signUpPasswordBox flex flex-col w-full ${
            changeUserInfo.password.boolean === false
              ? 'h-auto mb-1'
              : 'h-[80px] mb-5'
          }`}
        >
          <label
            className={labelDefaultClassName}
            onClick={() => arrowDirectionHandle('password')}
          >
            {changeUserInfo.password.boolean === false
              ? '비밀번호 변경'
              : '새 비밀번호'}
            <MdExpandMore className={changeUserInfo.password.className} />
          </label>
          {changeUserInfo.password.boolean === false ? null : (
            <input
              className={`${inputDefaultClassName} ${
                verify.passwordVerify === 'fail' ? 'border-subColor' : null
              }`}
              id="password"
              autoComplete="off"
              placeholder="영문/숫자/특수문자 혼합 8~12자"
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
          )}

          {verify.passwordVerify === 'fail' ? (
            <span className="block text-subColor text-[13px] h-[13px] justify-self-end">
              영문/숫자/특수문자 혼합 8~12자 입니다.
            </span>
          ) : null}
        </div>
        {changeUserInfo.password.boolean === false ? null : (
          <div className="signUpPasswordCheckBox flex flex-col w-full h-[80px] mb-4">
            <label className={labelDefaultClassName}>새 비밀번호 확인</label>
            <input
              className={`${inputDefaultClassName} ${
                verify.passwordCheckVerify === 'fail' ? 'border-subColor' : null
              }`}
              autoComplete="off"
              placeholder="비밀번호를 한번 더 입력해주세요"
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
        )}

        <div className="flex items-center justify-center w-full mb-6">
          <input
            id="agreeCheck"
            type="checkbox"
            className="w-5 h-5 mr-3 accent-subColor "
            onClick={checkHandle}
          />
          <label
            htmlFor="agreeCheck"
            className="block text-mainColor text-base font-semibold "
          >
            회원 정보를 수정하는 것에 대해 동의합니다.
          </label>
        </div>
        <input
          type="submit"
          value="Edit"
          className="border py-2.5 px-5 text-base font-semibold w-full rounded-md bg-mainColor text-iconColor duration-500 outline-0 mb-1 disabled:opacity-20"
          disabled={
            !Object.values(verify).every((el) => el === 'success') &&
            !(
              verify.userNameVerify === 'success' &&
              verify.passwordVerify === '' &&
              verify.passwordCheckVerify === '' &&
              verify.agreeVerify === 'success'
            ) &&
            !(
              verify.userNameVerify === '' &&
              verify.passwordVerify === 'success' &&
              verify.passwordCheckVerify === 'success' &&
              verify.agreeVerify === 'success'
            )
          }
        />
      </form>
    </div>
  );
}

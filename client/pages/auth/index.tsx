import { useState, useEffect, useRef, useCallback } from 'react';
import { AiFillCamera } from 'react-icons/ai';
import { useForm } from 'react-hook-form';
import Image from 'next/image';
import { useAppSelector } from '../../ducks/store';
import { getUserChallenges } from '../../module/challengeFunctionMoudules';
import { useRouter } from 'next/router';
import { postAuth } from '../../module/challengeFunctionMoudules';
import { patchHabitAuth } from '../../module/authFunctionMoudules';
interface IauthFormValue {
  memo: string;
  authImage: File | null;
}
interface IverifyForm {
  imgVerify: string;
  chooseHabitVerify: string;
  memoVerify: string;
  agreeVerify: string;
}
interface IresponseDataValue {
  authIds?: null;
  challengeId?: number;
  challenger?: null;
  habitTitle?: string | null;
  habitSubTitle?: string | null;
  review?: null;
  score?: number;
  status?: string;
  usedWildcard?: number;
}
interface IpropsValue {
  authImageUrl: string;
  authId: number;
  body: string;
  habitId: number;
}
export default function Auth({
  authImageUrl,
  authId,
  body,
  habitId,
}: IpropsValue) {
  const { userId } = useAppSelector((state) => state.loginIdentity);
  const router = useRouter();
  const { register, handleSubmit, reset, getValues, watch, setValue } =
    useForm<IauthFormValue>();
  const memoRegExp: RegExp = /[A-Za-z0-9가-힇ㄱ-ㅎ]{2,20}/;
  const [active, setActive] = useState(-1);
  const [imgFile, setImgFile] = useState(authImageUrl || '');
  const [verify, setVerify] = useState<IverifyForm>({
    imgVerify: 'fail',
    chooseHabitVerify: 'fail',
    memoVerify: '',
    agreeVerify: 'fail',
  });
  const [ingData, setIngData] = useState<IresponseDataValue[]>([]);
  const [authState, setAuthState] = useState('auth');
  const { authImage } = watch();

  const [slide, setSlide] = useState(true);

  const containerRef = useRef<HTMLUListElement>(null);
  const onWheel = (e: any) => {
    lockScroll();
    const { deltaY } = e;
    const el = containerRef.current;
    if (!el) return;
    if (deltaY > 0 && slide === true) {
      setSlide(false);
      el.scrollTo({
        left: el.scrollLeft + deltaY * 2,
        behavior: 'smooth',
      });
      setSlide(true);
    }
    if (deltaY < 0 && slide === true) {
      setSlide(false);
      el.scrollTo({
        left: el.scrollLeft + deltaY * 2,
        behavior: 'smooth',
      });
      setSlide(true);
    }
  };
  const lockScroll = useCallback(() => {
    document.body.style.overflow = 'hidden';
  }, []);

  const openScroll = useCallback(() => {
    if (document.body.style.overflow === 'hidden') {
      document.body.style.removeProperty('overflow');
    }
  }, []);

  const deleteImgHandle = (): void => {
    setImgFile('');
    reset({
      authImage: null,
    });
    setVerify({ ...verify, imgVerify: 'fail' });
  };
  const checkHandle = (): void => {
    verify.agreeVerify === 'fail'
      ? setVerify({ ...verify, agreeVerify: 'success' })
      : setVerify({ ...verify, agreeVerify: 'fail' });
  };
  const blurHandle = (boolean: boolean): void => {
    if (boolean === false) {
      setVerify({ ...verify, memoVerify: 'fail' });
    } else {
      setVerify({ ...verify, memoVerify: 'success' });
    }
  };

  const postAuthHandle = async (data: IauthFormValue): Promise<void> => {
    const { memo } = data;
    if (memoRegExp.test(memo)) {
      const formData = new FormData();
      formData.append(
        'data',
        new Blob([JSON.stringify({ body: memo })], {
          type: 'application/json',
        }),
      );
      formData.append('file', authImage[0]);

      const response: number | any = await postAuth({
        challengeId: active,
        body: formData,
      });
      if (response === 409) {
        setAuthState('exist');
        setTimeout(() => {
          setAuthState('');
        }, 1500);
      } else if (response === 400) {
        setAuthState('overTime');
        setTimeout(() => {
          setAuthState('');
        }, 1500);
      } else if (response === 500) {
        confirm('준비 중 입니다.');
      } else {
        setAuthState('');
        router.push(`habit/detail/${response.habitId}/auth`);
      }
    } else {
      setVerify({ ...verify, memoVerify: 'fail' });
    }
  };
  const patchAuthHandle = async (data: IauthFormValue): Promise<void> => {
    const { memo } = data;
    if (memoRegExp.test(memo)) {
      const formData = new FormData();
      formData.append(
        'data',
        new Blob([JSON.stringify({ body: memo })], {
          type: 'application/json',
        }),
      );

      if (authImage !== undefined) {
        formData.append('file', authImage[0]);
      }

      const response: number | any = await patchHabitAuth({
        habitId,
        authId,
        body: formData,
      });
      if (response === 409) {
        setAuthState('exist');
        setTimeout(() => {
          setAuthState('');
        }, 1500);
      } else if (response === 400) {
        setAuthState('overTime');
        setTimeout(() => {
          setAuthState('');
        }, 1500);
      } else if (response === 500) {
        confirm('준비 중 입니다.');
      } else {
        setAuthState('');
        window.location.reload();
      }
    } else {
      setVerify({ ...verify, memoVerify: 'fail' });
    }
  };
  useEffect((): void => {
    if (authImage && authImage.length > 0) {
      setVerify({ ...verify, imgVerify: 'success' });
      const file = authImage[0];
      setImgFile(URL.createObjectURL(file));
    }
  }, [authImage]);
  useEffect(() => {
    async function axiosFunc() {
      const response: IresponseDataValue[] | number = await getUserChallenges(
        userId,
      );
      if (typeof response === 'number') {
        router.push('/user/login');
      } else {
        setIngData(response);
      }
    }
    axiosFunc();
  }, []);

  useEffect(() => {
    if (authImageUrl !== undefined) {
      setValue('memo', body);
      setVerify({ ...verify, imgVerify: 'success', memoVerify: 'success' });
    }
  }, []);
  return (
    <div
      className={`${
        authImageUrl === undefined ? null : 'absolute -top-20'
      } w-full flex flex-col pt-5 overvflow-y-scroll scrollbar-hide relative items-center -mb-[100px] min-h-screen`}
      onWheel={() => {
        openScroll();
      }}
    >
      <div
        className={`${
          authImageUrl !== undefined ? 'hidden' : 'w-full px-5 pb-4'
        } `}
      >
        <span className="font-bold text-xl">인증할 습관 선택</span>
      </div>
      <div
        className={`${
          authState === 'exist' || authState === 'overTime' ? 'flex' : 'hidden'
        } absolute w-3/4 bg-white border-2 border-subColor top-72 rounded-full justify-center h-10 items-center animate-dropDown`}
      >
        <span className="text-subColor font-semibold">
          {authState === 'exist'
            ? '이미 인증한 습관입니다!'
            : authState === 'overTime'
            ? '인증 가능한 시간이 아닙니다.'
            : null}
        </span>
      </div>
      <ul
        className={`${
          authImageUrl !== undefined
            ? 'hidden'
            : 'flex flex-nowrap w-full overflow-x-scroll scrollbar-hide pl-5 gap-2.5 items-center'
        } `}
        ref={containerRef}
        onWheel={(e) => {
          e.stopPropagation();
          onWheel(e);
        }}
      >
        {ingData.length === 0 ? (
          <span>진행중인 습관이 없습니다.</span>
        ) : (
          ingData.map((el) => {
            return (
              <li
                className={`${
                  active === el.challengeId ? 'bg-subColor' : 'bg-mainColor'
                } flex-[0_0_auto] rounded-full h-[32px] leading-[28px] py-1 px-2.5 text-iconColor duration-300  justify-center items-center text-sm font-semibold last:mr-5`}
                key={el.challengeId}
                onClick={() => {
                  setActive(el.challengeId);
                  setVerify({ ...verify, chooseHabitVerify: 'success' });
                }}
              >
                {el.habitSubTitle}
              </li>
            );
          })
        )}
      </ul>

      <form
        className="file-uploader-container w-full p-5"
        onSubmit={
          authImageUrl === undefined
            ? handleSubmit(postAuthHandle)
            : handleSubmit(patchAuthHandle)
        }
      >
        <span className=" block font-bold text-xl pt-5">인증 사진 등록</span>
        <label
          className="file-uploader-label flex justify-center items-center w-[202px] mx-auto h-[202px] my-5 mt-4 border border-mainColor rounded-[12px] overflow-hidden"
          htmlFor="uploader-input"
        >
          {imgFile !== '' ? (
            <div className="file-uploader-preview w-[200px] h-[200px]">
              <Image
                className="object-contain w-full h-full"
                src={imgFile}
                alt="uploaded image"
                width={500}
                height={500}
                style={{ objectFit: 'cover' }}
              />
            </div>
          ) : (
            <div className="file-uploader-base-image">
              <AiFillCamera size="40" />
            </div>
          )}
        </label>
        <input
          className="file-uploader-input hidden"
          id="uploader-input"
          type="file"
          accept="image/*"
          capture="environment"
          disabled={imgFile !== ''}
          {...register('authImage')}
        />
        {imgFile !== '' ? (
          <div className=" flex justify-center w-full items-center  mb-5">
            <span
              className="text-base text-iconColor w-[200px] bg-mainColor rounded-full h-[40px] flex justify-center items-center"
              onClick={deleteImgHandle}
            >
              삭제하기
            </span>
          </div>
        ) : null}

        <span className=" block font-bold text-xl mb-4 pt-5">
          간단한 메모 기록
        </span>
        <textarea
          className={`w-full h-[35px] resize-none border overflow-auto border-mainColor rounded-md focus:outline-subColor ${
            verify.memoVerify === '' || verify.memoVerify === 'success'
              ? 'mb-3'
              : null
          } p-1 text-sm leading-[25px]`}
          placeholder="2~20자 이내로 기록을 작성해주세요."
          {...register('memo', {
            onBlur: () => {
              blurHandle(memoRegExp.test(getValues('memo')));
            },
          })}
        />
        {verify.memoVerify === 'fail' ? (
          <span className="mb-1 pl-1 text-sm text-subColor">
            2~20자 이내로 간단한 메모를 작성해주세요!
          </span>
        ) : null}

        <div
          className={`${
            authImageUrl !== undefined
              ? 'hidden'
              : 'flex items-center w-full mb-6 pt-5'
          } `}
        >
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
            인증 사진이 업로드 되는 것에 동의합니다.
          </label>
        </div>
        <input
          type="submit"
          value="인증 등록"
          className={`${
            authImageUrl !== undefined
              ? 'hidden'
              : 'fborder py-2.5 px-5 text-base font-semibold w-full rounded-md bg-mainColor text-iconColor duration-500 outline-0 mb-1 disabled:opacity-20'
          }`}
          disabled={
            !Object.values(verify).every((el) => el === 'success') ||
            authState !== 'auth'
          }
        />
        <input
          type="submit"
          value="인증 수정"
          className={`${
            authImageUrl === undefined
              ? 'hidden'
              : 'fborder py-2.5 px-5 text-base font-semibold w-full rounded-md bg-mainColor text-iconColor duration-500 outline-0 mb-1 disabled:opacity-20'
          }`}
          disabled={
            verify.imgVerify !== 'success' || verify.memoVerify !== 'success'
          }
        />
      </form>
    </div>
  );
}

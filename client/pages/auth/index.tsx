import { useState, useEffect, useRef, useCallback } from 'react';
import { AiFillCamera } from 'react-icons/ai';
import { useForm } from 'react-hook-form';
import Image from 'next/image';
import { useAppSelector } from '../../ducks/store';
import { getUserChallenges } from '../../module/challengeFunctionMoudules';
import { useRouter } from 'next/router';
import { postAuth } from '../../module/challengeFunctionMoudules';
import { Modal } from '../../components/modal';
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
  review?: null;
  score?: number;
  status?: string;
  usedWildcard?: number;
}
export default function Auth() {
  //추후 데이터 받아와서 더미데이터 대체해야함
  const { userId } = useAppSelector((state) => state.loginIdentity);
  const router = useRouter();
  const { register, handleSubmit, reset, getValues, watch } =
    useForm<IauthFormValue>();
  const memoRegExp: RegExp = /[A-Za-z0-9가-힇ㄱ-ㅎ]{2,20}/;
  const [active, setActive] = useState(-1);
  const [imgFile, setImgFile] = useState('');
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
        router.push(`habit/detail/${response.habitId}/auth`);
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

  return (
    <div
      className="h-screen w-full px-10 flex flex-col pt-5 overvflow-y-scroll scrollbar-hide relative items-center"
      onWheel={() => {
        openScroll();
      }}
    >
      <div className="mb-4 w-full">
        <span className="font-bold text-base">내가 진행중인 습관</span>
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
        className="flex flex-col flex-wrap w-full  h-1/6 overflow-x-scroll scrollbar-hide p-2 border-y  border-mainColor  items-center"
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
              <div className=" w-[120px] h-1/2 p-2" key={el.challengeId}>
                <span
                  className={`${
                    active === el.challengeId ? 'bg-subColor' : 'bg-mainColor'
                  } w-full h-full rounded-full text-iconColor flex duration-300  justify-center items-center text-base`}
                  onClick={() => {
                    setActive(el.challengeId);
                    setVerify({ ...verify, chooseHabitVerify: 'success' });
                  }}
                >
                  {el.habitTitle === null
                    ? null
                    : el.habitTitle.length > 10
                    ? el.habitTitle.slice(0, 10) + '...'
                    : el.habitTitle}
                </span>
              </div>
            );
          })
        )}
      </ul>

      <form
        className="file-uploader-container w-full"
        onSubmit={handleSubmit(postAuthHandle)}
      >
        <label
          className="file-uploader-label flex justify-center items-center w-full mx-auto h-[202px] my-[20px] border border-mainColor rounded"
          htmlFor="uploader-input"
        >
          {imgFile !== '' ? (
            <div className="file-uploader-preview w-[300px] h-[200px]">
              <Image
                className="object-contain w-full h-full"
                src={imgFile}
                alt="uploaded image"
                width={500}
                height={500}
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
          <div className=" flex justify-center w-full items-center bg-mainColor rounded-full h-[40px] mb-5">
            <span
              className="text-base text-iconColor"
              onClick={deleteImgHandle}
            >
              삭제하기
            </span>
          </div>
        ) : null}

        <span className=" block font-bold text-base mb-3">기록하기</span>
        <textarea
          className={`w-full h-[35px] resize-none border overflow-auto border-mainColor rounded-md focus:outline-subColor ${
            verify.memoVerify === '' || verify.memoVerify === 'success'
              ? 'mb-3'
              : null
          } p-1`}
          placeholder="2~20자 이내로 기록을 작성해주세요."
          {...register('memo', {
            onBlur: () => {
              blurHandle(memoRegExp.test(getValues('memo')));
            },
          })}
        />
        {verify.memoVerify === 'fail' ? (
          <span className="mb-1 text-base text-subColor">
            2~20자 이내로 기록을 작성해주세요.
          </span>
        ) : null}
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
            인증 사진이 업로드 되는 것을 동의합니다.
          </label>
        </div>
        <input
          type="submit"
          value="인증 등록"
          className="border py-2.5 px-5 text-base font-semibold w-full rounded-md bg-mainColor text-iconColor duration-500 outline-0 mb-1 disabled:opacity-20"
          disabled={
            !Object.values(verify).every((el) => el === 'success') ||
            authState !== 'auth'
          }
        />
      </form>
    </div>
  );
}

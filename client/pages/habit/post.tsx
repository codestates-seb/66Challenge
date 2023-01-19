import { useForm } from 'react-hook-form';
import { FileUploader } from '../../components/fileUploader';
import { useState, useRef, useEffect } from 'react';

interface HabitFormValues {
  title: string;
  subtitle: string;
  body: string;
  category: string;
  authEndTime: string;
  authStartTime: string;
  habitImage: any;
  successImage: any;
  failImage: any;
}

export type { HabitFormValues };

const Post = () => {
  const { register, handleSubmit, getValues, setFocus, watch, reset } =
    useForm<HabitFormValues>();

  const { habitImage, successImage, failImage } = watch();
  const [habitImagePreview, setHabitImagePreview] = useState('');
  const [successImagePreview, setSuccessImagePreview] = useState('');
  const [failImagePreview, setFailImagePreview] = useState('');

  useEffect(() => {
    if (habitImage && habitImage.length > 0) {
      setVerify({ ...verify, habitImageVerify: 'success' });
      const file = habitImage[0];
      setHabitImagePreview(URL.createObjectURL(file));
    }
  }, [habitImage]);

  console.log(habitImage);
  console.log(successImage);
  console.log(failImage);

  useEffect(() => {
    if (successImage && successImage.length > 0) {
      setVerify({ ...verify, successImageVerify: 'success' });
      const file = successImage[0];
      setSuccessImagePreview(URL.createObjectURL(file));
    }
  }, [successImage]);

  useEffect(() => {
    if (failImage && failImage.length > 0) {
      setVerify({ ...verify, failImageVerify: 'success' });
      const file = failImage[0];
      setFailImagePreview(URL.createObjectURL(file));
    }
  }, [failImage]);

  const [verify, setVerify] = useState({
    titleVerify: '',
    subtitleVerify: '',
    categoryVerify: '',
    habitImageVerify: '',
    bodyVerify: '',
    authTimeVerify: 'success',
    successImageVerify: '',
    failImageVerify: '',
    agreeVerify: 'fail',
  });

  const titleRegExp = /^[A-Za-z0-9가-힇\s]{5,20}$/;
  const subtitleRegExp = /^[A-Za-z0-9가-힇\s]{5,10}$/;
  const bodyRegExp = /^[A-Za-z0-9가-힇\s`~!@#$%^&*()-_=+]{100,}$/;

  const blurHandle = (verifyBoolean: boolean, verifyKey: string): void => {
    if (verifyBoolean) {
      setVerify({ ...verify, [verifyKey]: 'success' });
    } else {
      setVerify({ ...verify, [verifyKey]: 'fail' });
    }
  };

  const postButtonClick = (data: HabitFormValues) => {
    const { title, subtitle, body, category, authEndTime, authStartTime } =
      data;

    if (titleRegExp.test(title) === false) {
      setVerify({ ...verify, titleVerify: 'fail' });
    } else if (subtitleRegExp.test(subtitle) === false) {
      setVerify({ ...verify, subtitleVerify: 'fail' });
    } else if (category === 'default') {
      setVerify({ ...verify, categoryVerify: 'fail' });
    } else if (habitImagePreview.length === 0) {
      setVerify({ ...verify, habitImageVerify: 'fail' });
    } else if (bodyRegExp.test(body) === false) {
      setVerify({ ...verify, bodyVerify: 'fail' });
    } else if (authStartTime < authEndTime === false) {
      setVerify({ ...verify, authTimeVerify: 'fail' });
    } else {
      // 데이터 통신
      // reset();
      console.log(data);
    }
  };

  type nextInput =
    | 'title'
    | 'subtitle'
    | 'body'
    | 'category'
    | 'authEndTime'
    | 'authStartTime';

  const InputElKeyEvent = (
    e: React.KeyboardEvent<HTMLElement>,
    nextInput?: nextInput,
  ): void => {
    if (e.key === 'Enter') {
      e.preventDefault();
      setFocus(nextInput);
    }
  };

  const checkHandle = () => {
    verify.agreeVerify === 'fail'
      ? setVerify({ ...verify, agreeVerify: 'success' })
      : setVerify({ ...verify, agreeVerify: 'fail' });
  };

  const inputDefaultClassName: string =
    'text-base w-full rounded-md px-2 pt-[2px] border focus:border-mainColor duration-500 outline-0 mb-1';

  return (
    <div className="habit-post-container w-[300px] mx-auto flex flex-col mt-[40px]">
      <form
        className="login-form"
        onSubmit={handleSubmit((data) => {
          try {
            postButtonClick(data);
          } catch (err) {
            console.error(err);
          }
        })}
      >
        <div className="title-input-wrapper flex flex-col mb-5 h-[80px]">
          <label htmlFor="title" className="text-base font-semibold mb-1">
            습관명
          </label>
          <input
            type="text"
            id="title"
            className={`h-[35px] ${inputDefaultClassName}`}
            placeholder="습관명을 5~20자 이내로 입력해주세요."
            onKeyDown={(e) => {
              InputElKeyEvent(e, 'subtitle');
            }}
            {...register('title', {
              onBlur: () => {
                blurHandle(titleRegExp.test(getValues('title')), 'titleVerify');
              },
            })}
          />
          {verify.titleVerify === 'fail' ? (
            <span className="block text-subColor text-[13px] h-[13px] ">
              습관명을 5~20자 이내로 입력해주세요.
            </span>
          ) : null}
        </div>

        <div className="subtitle-input-wrapper flex flex-col mb-5 h-[80px]">
          <label htmlFor="subtitle" className="text-base font-semibold mb-1">
            부제
          </label>
          <input
            type="text"
            id="subtitle"
            className={`h-[35px] ${inputDefaultClassName}`}
            placeholder="부제를 5~10자 이내로 입력해주세요."
            onKeyDown={(e) => {
              InputElKeyEvent(e, 'category');
            }}
            {...register('subtitle', {
              onBlur: () => {
                blurHandle(
                  subtitleRegExp.test(getValues('subtitle')),
                  'subtitleVerify',
                );
              },
            })}
          />
          {verify.subtitleVerify === 'fail' ? (
            <span className="block text-subColor text-[13px] h-[13px] ">
              부제을 5~10자 이내로 입력해주세요.
            </span>
          ) : null}
        </div>

        <div className="category-input-wrapper flex flex-col mb-5 h-[80px]">
          <label htmlFor="category" className="text-base font-semibold mb-1">
            카테고리
          </label>
          <select
            id="category"
            className={`h-[35px] ${inputDefaultClassName} bg-white`}
            defaultValue="default"
            {...register('category', {
              onBlur: () => {
                blurHandle(
                  getValues('category') !== 'default',
                  'categoryVerify',
                );
              },
            })}
          >
            <option value="default" disabled>
              카테고리를 선택해주세요.
            </option>
            <option value="exercise">운동</option>
            <option value="dietary">식습관</option>
            <option value="study">학습</option>
            <option value="life">일상생활</option>
            <option value="hobby">취미</option>
            <option value="selfcare">셀프케어</option>
            <option value="eco">에코</option>
            <option value="mind">마음챙김</option>
            <option value="etc">기타</option>
          </select>
          {verify.categoryVerify === 'fail' ? (
            <span className="block text-subColor text-[13px] h-[13px] ">
              카테고리를 선택해주세요.
            </span>
          ) : null}
        </div>

        <div className="habit-image-input-wrapper flex flex-col mb-5 min-h-[245px]">
          <div className="text-base font-semibold mb-1">습관 메인 사진</div>
          <FileUploader
            imgFilePreview={habitImagePreview}
            register={register('habitImage')}
          />
          {verify.habitImageVerify === 'fail' ? (
            <span className="block text-subColor text-[13px] h-[13px] ">
              습관을 잘 설명할 수 있는 이미지를 업로드 해주세요.
            </span>
          ) : null}
        </div>

        <div className="body-input-wrapper flex flex-col mb-5 min-h-[195px]">
          <label htmlFor="body" className="text-base font-semibold mb-1">
            습관 소개
          </label>
          <textarea
            id="body"
            className={`min-h-[150px] ${inputDefaultClassName}`}
            placeholder="습관에 대한 소개글을 최소 100자 이상 작성해주세요."
            onKeyDown={(e) => {
              InputElKeyEvent(e, 'authStartTime');
            }}
            {...register('body', {
              onBlur: () => {
                blurHandle(bodyRegExp.test(getValues('body')), 'bodyVerify');
              },
            })}
          ></textarea>
          {verify.bodyVerify === 'fail' ? (
            <span className="block text-subColor text-[13px] h-[13px] ">
              습관에 대한 설명글을 작성해주세요.
            </span>
          ) : null}
        </div>

        <div className="auth-time-input-wrapper flex flex-col mb-5 min-h-[117px]">
          <label htmlFor="authTime" className="text-base font-semibold mb-1">
            인증 가능 시간
          </label>
          <div className="flex relative">
            <input
              id="authTime"
              type="time"
              className={`h-[70px] text-base w-full rounded-tl-md text-center bg-slate-50 rounded-bl-md px-2 pt-[30px] border border-r-0 focus:border-mainColor focus:border-r outline-0 mb-1`}
              defaultValue="00:00"
              onKeyDown={(e) => {
                InputElKeyEvent(e, 'authEndTime');
              }}
              {...register('authStartTime', {
                onBlur: () => {
                  blurHandle(
                    getValues('authStartTime') < getValues('authEndTime'),
                    'authTimeVerify',
                  );
                },
              })}
            ></input>
            <input
              type="time"
              className={`h-[70px] text-base w-full rounded-tr-md text-center bg-slate-50 rounded-br-md px-2 pt-[30px] border focus:border-mainColor outline-0 mb-1`}
              defaultValue="23:59"
              onKeyDown={(e) => {
                InputElKeyEvent(e);
              }}
              {...register('authEndTime', {
                onBlur: () => {
                  blurHandle(
                    getValues('authStartTime') < getValues('authEndTime'),
                    'authTimeVerify',
                  );
                },
              })}
            ></input>
            <div className="absolute top-[10px] text-[14px] font-semibold left-0 pl-[12px] ">
              시작시간
            </div>
            <div className="absolute top-[10px] text-[14px] font-semibold left-2/4 pl-[12px]">
              종료시간
            </div>
          </div>
          {verify.authTimeVerify === 'fail' ? (
            <span className="block text-subColor text-[13px] h-[13px] ">
              인증 시작시간은 종료시간 보다 이전이어야 합니다.
            </span>
          ) : null}
        </div>

        <div className="habit-auth-image-input-wrapper flex flex-col mb-5 min-h-[245px]">
          <div className="text-base font-semibold mb-1">습관 인증 사진</div>
          <div className="flex flex-col">
            <div className="text-center text-green-600 py-2.5 text-sm font-bold">
              올바른 인증 사례
            </div>
            <FileUploader
              imgFilePreview={successImagePreview}
              register={register('successImage')}
            />
            <div className="text-center text-rose-600 pt-5 pb-2.5 text-sm font-bold">
              잘못된 인증 사례
            </div>
            <FileUploader
              imgFilePreview={failImagePreview}
              register={register('failImage')}
            />
          </div>
          {verify.successImageVerify === 'fail' ||
          verify.failImageVerify === 'fail' ? (
            <span className="block text-subColor text-[13px] h-[13px] ">
              습관 형성을 위한 인증 사진의 올바른 사례와 잘못된 사례에 대한
              이미지를 모두 업로드 해주세요.
            </span>
          ) : null}
        </div>

        <div className="flex w-full mb-6">
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
            (필수) 본 게시물은 선량한 풍속 및 기타 사회질서를 해치는 내용이
            포함되어 있지 않음을 확인합니다.
          </label>
        </div>

        <div className="button-wrapper flex justify-between pt-[15px]">
          <button
            className="signup-button w-[120px] text-base py-2.5 px-5 border rounded"
            type="button"
          >
            취소하기
          </button>
          <button
            className="login-button w-[120px] text-base bg-[#222222] text-white py-2.5 px-5 border rounded disabled:opacity-20"
            type="submit"
            disabled={!Object.values(verify).every((el) => el === 'success')}
          >
            등록하기
          </button>
        </div>
      </form>
    </div>
  );
};

export default Post;

import { useForm } from 'react-hook-form';
import { FileUploader } from '../../components/fileUploader';

const Post = () => {
  const { register, handleSubmit } = useForm();

  return (
    <div className="habit-post-container w-[300px] h-screen mx-auto flex flex-col my-[40px]">
      <form
        className="login-form"
        onSubmit={handleSubmit((data) => {
          try {
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
            className={`h-[35px] text-base w-full rounded-md px-2 pt-[5px] border focus:border-mainColor duration-500 outline-0 mb-1`}
            placeholder="습관명을 입력해주세요."
            onKeyDown={(e) => {}}
            {...register('title', {
              onBlur: () => {},
            })}
          ></input>
        </div>

        <div className="subtitle-input-wrapper flex flex-col mb-5 h-[80px]">
          <label htmlFor="subTitle" className="text-base font-semibold mb-1">
            부제
          </label>
          <input
            type="text"
            id="subTitle"
            className={`h-[35px] text-base w-full rounded-md px-2 pt-[5px] border focus:border-mainColor duration-500 outline-0 mb-1`}
            placeholder="습관명을 입력해주세요."
            onKeyDown={(e) => {}}
            {...register('subTitle', {
              onBlur: () => {},
            })}
          ></input>
        </div>

        <div className="category-input-wrapper flex flex-col mb-5 h-[80px]">
          <label htmlFor="category" className="text-base font-semibold mb-1">
            카테고리
          </label>
          <select
            id="category"
            className={`h-[35px] text-base w-full rounded-md px-2 pt-[5px] border bg-white focus:border-mainColor duration-500 outline-0 mb-1`}
            defaultValue="default"
            onKeyDown={(e) => {}}
            {...register('category', {
              onBlur: () => {},
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
        </div>

        <div className="habit-image-input-wrapper flex flex-col mb-5">
          <div className="text-base font-semibold mb-1">습관 메인 사진</div>
          <FileUploader />
        </div>

        <div className="body-input-wrapper flex flex-col mb-5 h-[145px]">
          <label htmlFor="body" className="text-base font-semibold mb-1">
            습관 소개
          </label>
          <textarea
            id="body"
            className={`h-[100px] text-base w-full rounded-md px-2 pt-[5px] border focus:border-mainColor duration-500 outline-0 mb-1`}
            placeholder="습관에 대한 소개글을 작성해주세요."
            onKeyDown={(e) => {}}
            {...register('body', {
              onBlur: () => {},
            })}
          ></textarea>
        </div>

        <div className="auth-time-input-wrapper flex flex-col mb-5">
          <label htmlFor="authTime" className="text-base font-semibold mb-1">
            인증 가능 시간
          </label>
          <div className="flex relative">
            <input
              id="authTime"
              type="time"
              className={`h-[70px] text-base w-full rounded-tl-md text-center bg-slate-50 rounded-bl-md px-2 pt-[30px] border border-r-0 focus:border-mainColor focus:border-r outline-0 mb-1`}
              value="00:00"
              onKeyDown={(e) => {}}
              {...register('body', {
                onBlur: () => {},
              })}
            ></input>
            <input
              type="time"
              className={`h-[70px] text-base w-full rounded-tr-md text-center bg-slate-50 rounded-br-md px-2 pt-[30px] border focus:border-mainColor outline-0 mb-1`}
              value="23:59"
              onKeyDown={(e) => {}}
              {...register('body', {
                onBlur: () => {},
              })}
            ></input>
            <div className="absolute top-[10px] left-0 pl-[12px] ">
              시작시간
            </div>
            <div className="absolute top-[10px] left-2/4 pl-[12px]">
              종료시간
            </div>
          </div>
        </div>

        <div className="button-wrapper flex justify-between">
          <button
            className="signup-button w-[120px] text-base py-2.5 px-5 border rounded"
            type="button"
          >
            취소하기
          </button>
          <button
            className="login-button w-[120px] text-base bg-[#222222] text-white py-2.5 px-5 border rounded"
            type="submit"
          >
            등록하기
          </button>
        </div>
      </form>
    </div>
  );
};

export default Post;

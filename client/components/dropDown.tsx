import { AiOutlineStar, AiFillStar } from 'react-icons/ai';
import { useState } from 'react';
import { MdExpandMore } from 'react-icons/md';
import { Modal } from './modal';
import {
  postHabitReport,
  postAuthReport,
  postReviewReport,
} from '../module/reportFunctionMoudules';
import {
  deleteHabitReview,
  patchHabitReview,
} from '../module/reviewFunctionModules';
import { deleteHabit } from '../module/habitFunctionMoudules';
import { deleteHabitAuth } from '../module/authFunctionMoudules';
import { reportData } from '../data/reportData';
import { useAppSelector } from '../ducks/store';
import { KaKaoShare } from '../module/kakaoShare';
import Auth from '../pages/auth';
import { useRouter } from 'next/router';
interface IarrowValue {
  className: string;
  boolean: boolean;
}
interface propsValue {
  dropDownType: string;
  boolean: boolean;
  authId?: number;
  authorUserId?: number;
  habitId?: number;
  hostUserId?: number;
  reviewId?: number;
  reviewerUserId?: number;
  habitData?: {
    title: string;
    imageUrl: string | null;
    habitId: number;
    allChallengers: number;
  };
  score?: number;
  body?: string;
  authImageUrl?: string;
}
export function DropDown({
  dropDownType,
  boolean,
  authId,
  authorUserId,
  habitId,
  hostUserId,
  reviewId,
  reviewerUserId,
  habitData,
  score,
  body,
  authImageUrl,
}: propsValue) {
  const [arrowDirection, setArrowDirection] = useState<IarrowValue>({
    className: '',
    boolean: false,
  });
  const [value, setValue] = useState(body);
  const [patchScore, setPatchScore] = useState(score - 1);
  const upArrow: string = 'rotate-180 duration-500';
  const downArrow: string = 'rotate-0';
  const arrowDirectionHandle = (): void => {
    if (arrowDirection.boolean === false) {
      setArrowDirection({ className: upArrow, boolean: true });
    } else {
      setArrowDirection({ className: downArrow, boolean: false });
    }
  };
  const { userId } = useAppSelector((state) => state.loginIdentity);
  const router = useRouter();
  const [reportType, setReportType] = useState<string>('');
  const [agreeCheck, isAgreeCheck] = useState(false);
  const [isReportOpen, setIsReportOpen] = useState<boolean>(false);
  const [isUpdateOpen, setIsUpdateOpen] = useState<boolean>(false);
  const [isDeleteOpen, setIsDeleteOpen] = useState<boolean>(false);
  const [isShareOpen, setIsShareOpen] = useState<boolean>(false);
  //props로 넘겨받는거에서 인증글이냐,리뷰냐 판단하여 비동기 함수 조건부 호출
  const scoreHandle = (score: number) => {
    setPatchScore(score);
  };
  const max = new Array(5).fill(null);
  const declarationHandle = () => {
    if (reportType.length === 0) {
      alert('신고 사유를 선택해주세요.');
      return;
    }
    if (dropDownType === 'review') {
      const res = postReviewReport({
        habitId,
        reviewId,
        reportType,
        userId,
        reviewerUserId,
      });
      console.log(res);
    } else if (dropDownType === 'auth') {
      postAuthReport({ habitId, authId, reportType, userId, authorUserId });
    } else if (dropDownType === 'habit') {
      postHabitReport({ habitId, userId, reportType, hostUserId });
    }
    setIsReportOpen(false);
  };

  // TODO 수정 관련 모달 부분 구현 필요
  const updateHandle = async () => {
    if (dropDownType === 'review') {
      const response = await patchHabitReview({
        habitId,
        score: patchScore + 1,
        body: value,
        reviewId,
      });
      window.location.reload();
    } else if (dropDownType === 'auth') {
    }
  };

  const deleteHandle = () => {
    if (!agreeCheck) {
      alert('삭제 동의 여부를 체크해주세요');
      return;
    }
    if (dropDownType === 'review') {
      //리뷰 삭제 비동기 함수 호출
      deleteHabitReview({ habitId, reviewId });
      window.location.reload();
    } else if (dropDownType === 'auth') {
      //인증 삭제 비동기 함수 호출
      deleteHabitAuth({ authId });
      window.location.reload();
    } else if (dropDownType === 'habit') {
      //습관 삭제 비동기 함수 호출
      if (habitData.allChallengers > 0) {
        alert('챌린지 참여자가 있으므로 삭제할 수 없습니다.');
      } else {
        deleteHabit({ habitId });
        router.push(`/`);
      }
    }
    setIsDeleteOpen(false);
  };

  const checkHandle = () => {
    if (agreeCheck) {
      isAgreeCheck(false);
    } else {
      isAgreeCheck(true);
    }
  };

  return (
    <div className="flex flex-col w-[100px] items-end relative cursor-pointer">
      <MdExpandMore
        className={arrowDirection.className}
        onClick={arrowDirectionHandle}
      />
      {arrowDirection.boolean === false ? null : (
        <div className="flex flex-col w-full absolute top-[18px] right-0 ">
          {dropDownType === 'habit' ? (
            <span
              className="text-sm border border-[#e5e5e5] border-b-[0] bg-white text-center py-[5px]"
              onClick={(_) => setIsShareOpen(true)}
            >
              공유하기
            </span>
          ) : null}
          <span
            className="text-sm border border-[#e5e5e5]  bg-white text-center py-[5px]"
            onClick={(_) => setIsReportOpen(true)}
          >
            신고하기
          </span>
          {boolean === true ? (
            <>
              <span
                className="text-sm border-x border-b border-[#e5e5e5]  bg-white  text-center py-[5px]"
                onClick={(_) => {
                  if (dropDownType === 'habit') {
                    if (habitData.allChallengers > 0) {
                      alert('챌린지 참여자가 있으므로 수정할 수 없습니다.');
                    } else {
                      router.push(`/habit/edit/${habitId}`);
                    }
                  } else {
                    setIsUpdateOpen(true);
                  }
                }}
              >
                수정하기
              </span>
              <span
                className="text-sm border-x border-b border-[#e5e5e5]  bg-white  text-center py-[5px]"
                onClick={(_) => setIsDeleteOpen(true)}
              >
                삭제하기
              </span>
            </>
          ) : null}
        </div>
      )}
      {isShareOpen && (
        <Modal isOpen={isShareOpen} setIsOpen={setIsShareOpen}>
          <KaKaoShare habitData={habitData} />
        </Modal>
      )}
      {isReportOpen && (
        <Modal
          isOpen={isReportOpen}
          setIsOpen={setIsReportOpen}
          buttonName="신고하기"
          onClick={declarationHandle}
        >
          <fieldset>
            <legend className="text-xl font-semibold w-full text-center pb-5">
              신고 사유를 선택해주세요
            </legend>
            {reportData.map((el) => {
              return (
                <div className="w-full py-2.5 px-7 flex gap-2.5" key={el.value}>
                  <input
                    className="w-[16px]"
                    type="radio"
                    name="report"
                    id={el.value}
                    value={el.value}
                    onChange={(e) => setReportType(e.target.value)}
                  />
                  <label className="w-full" htmlFor={el.value}>
                    {el.explain}
                  </label>
                </div>
              );
            })}
          </fieldset>
        </Modal>
      )}
      {/* TODO 수정하기 관련 모달 구현 필요 */}
      {isUpdateOpen && (
        <Modal
          isOpen={isUpdateOpen}
          setIsOpen={setIsUpdateOpen}
          buttonName={dropDownType === 'review' ? '수정하기' : undefined}
          onClick={updateHandle}
        >
          {dropDownType === 'review' ? (
            <form className="flex flex-col">
              <div className="flex items-center mb-4">
                <span className="text-base font-semibold mr-2">
                  습관 만족도
                </span>
                {max.map((_, idx) => {
                  if (idx > patchScore) {
                    return (
                      <AiOutlineStar
                        key={idx}
                        onClick={() => scoreHandle(idx)}
                        className="text-[20px] mr-1 "
                      />
                    );
                  } else if (idx <= score) {
                    return (
                      <AiFillStar
                        key={idx}
                        onClick={() => scoreHandle(idx)}
                        className="text-subColor text-[20px] mr-1 animate-bookMark"
                      />
                    );
                  }
                })}
              </div>
              <div>
                <label
                  htmlFor="reviewInput"
                  className="block text-base font-semibold mb-2"
                >
                  성공 후기
                </label>
                <textarea
                  id="reviewInput"
                  className="w-full h-40 border border-mainColor rounded-lg focus:outline-subColor p-1 "
                  value={value}
                  onChange={(e) => setValue(e.target.value)}
                />
              </div>
            </form>
          ) : dropDownType === 'auth' ? (
            <Auth
              authImageUrl={authImageUrl}
              authId={authId}
              body={body}
              habitId={habitId}
            />
          ) : null}
        </Modal>
      )}
      {isDeleteOpen && (
        <Modal
          isOpen={isDeleteOpen}
          setIsOpen={setIsDeleteOpen}
          buttonName="삭제하기"
          onClick={deleteHandle}
        >
          <div className="text-xl font-semibold w-full text-center pb-5">
            {dropDownType === 'review' ? '후기 삭제' : '인증글 삭제'}
          </div>
          <div className="flex">
            <input
              className="w-5 h-5 mr-3 accent-subColor"
              type="checkbox"
              id="agreecheck"
              onClick={checkHandle}
            />
            <label
              className="block text-mainColor text-base font-semibold"
              htmlFor="agreecheck"
            >
              삭제된{' '}
              {dropDownType === 'review'
                ? '후기는'
                : dropDownType === 'auth'
                ? '인증글은'
                : '습관 게시물은'}{' '}
              복구할 수 없습니다. 정말 삭제하시겠습니까?
            </label>
          </div>
        </Modal>
      )}
    </div>
  );
}

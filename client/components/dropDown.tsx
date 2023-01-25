import { useEffect, useState } from 'react';
import { MdExpandMore } from 'react-icons/md';
import { Modal } from './modal';
import {
  postHabitReport,
  postAuthReport,
  postReviewReport,
} from '../module/reportFunctionMoudules';
import { deleteHabitReview } from '../module/reviewFunctionModules';
import { reportData } from '../data/reportData';
import { useAppSelector } from '../ducks/store';

interface IarrowValue {
  className: string;
  boolean: boolean;
}
interface propsValue {
  dropDownType: string;
  boolean: boolean;
  authId?: number;
  habitId?: number;
  reviewId?: number;
}
export function DropDown({
  dropDownType,
  boolean,
  authId,
  habitId,
  reviewId,
}: propsValue) {
  const [arrowDirection, setArrowDirection] = useState<IarrowValue>({
    className: '',
    boolean: false,
  });
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
  const [reportType, setReportType] = useState<string>('');
  const [agreeCheck, isAgreeCheck] = useState(false);
  const [isReportOpen, setIsReportOpen] = useState<boolean>(false);
  const [isDeleteOpen, setIsDeleteOpen] = useState<boolean>(false);

  //props로 넘겨받는거에서 인증글이냐,리뷰냐 판단하여 비동기 함수 조건부 호출
  const declarationHandle = () => {
    if (reportType.length === 0) {
      alert('신고 사유를 선택해주세요.');
      return;
    }
    if (dropDownType === 'review') {
      postReviewReport({ habitId, reviewId, reportType, userId });
    } else if (dropDownType === 'auth') {
      postAuthReport({ habitId, authId, reportType, userId });
    } else if (dropDownType === 'habit') {
      postHabitReport({ habitId, userId, reportType });
    }
    setIsReportOpen(false);
  };

  const deleteAuthHandle = () => {
    if (!agreeCheck) {
      alert('삭제 동의 여부를 체크해주세요');
      return;
    }
    if (dropDownType === 'review') {
      //리뷰 삭제 비동기 함수 호출
      deleteHabitReview({ habitId, reviewId });
    } else if (dropDownType === 'auth') {
      //인증 삭제 비동기 함수 호출
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
    <div className="flex flex-col w-[100px] items-end relative">
      <MdExpandMore
        className={arrowDirection.className}
        onClick={arrowDirectionHandle}
      />
      {arrowDirection.boolean === false ? null : (
        <div className="flex flex-col w-full absolute top-[18px] right-0 ">
          <span
            className="text-xs border border-[#e5e5e5]  bg-white text-center py-[5px]"
            onClick={(_) => setIsReportOpen(true)}
          >
            신고하기
          </span>
          {boolean === true ? (
            <span
              className="text-xs border-x border-b border-[#e5e5e5]  bg-white  text-center py-[5px]"
              onClick={(_) => setIsDeleteOpen(true)}
            >
              삭제하기
            </span>
          ) : null}
        </div>
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
      {isDeleteOpen && (
        <Modal
          isOpen={isDeleteOpen}
          setIsOpen={setIsDeleteOpen}
          buttonName="삭제하기"
          onClick={deleteAuthHandle}
        >
          <div className="text-xl font-semibold w-full text-center pb-5">
            {dropDownType === 'review' ? '후기 삭제' : '인증글 삭제'}
          </div>
          <div>
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
              삭제된 {dropDownType === 'review' ? '후기는' : '인증글은'} 복구할
              수 없습니다. 정말 삭제하시겠습니까?
            </label>
          </div>
        </Modal>
      )}
    </div>
  );
}

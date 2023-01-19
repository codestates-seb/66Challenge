import { useEffect, useState } from 'react';
import { MdExpandMore } from 'react-icons/md';

interface IarrowValue {
  className: string;
  boolean: boolean;
}
interface propsValue {
  dropDownType: string;
  boolean: boolean;
}
export function DropDown({ dropDownType, boolean }: propsValue) {
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

  //props로 넘겨받는거에서 인증글이냐,리뷰냐 판단하여 비동기 함수 조건부 호출
  const declarationHandle = () => {
    if (dropDownType === 'review') {
      //리뷰 신고 비동기 함수 호출
    } else if (dropDownType === 'auth') {
      //인증 신고 비동기 함수 호출
    }
  };
  const deleteAuthHandle = () => {
    if (dropDownType === 'review') {
      //리뷰 삭제 비동기 함수 호출
    } else if (dropDownType === 'auth') {
      //인증 삭제 비동기 함수 호출
    }
  };
  useEffect(() => {
    dropDownType;
  }, []);
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
            onClick={declarationHandle}
          >
            신고하기
          </span>
          {boolean === true ? (
            <span
              className="text-xs border-x border-b border-[#e5e5e5]  bg-white  text-center py-[5px]"
              onClick={deleteAuthHandle}
            >
              삭제하기
            </span>
          ) : null}
        </div>
      )}
    </div>
  );
}

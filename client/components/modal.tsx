/* 
* <------  사용하시기 전에 꼭 읽어주세요! ------> *
ToDo 1. 모달창을 사용하는 컴포넌트에서 isOpen에 대한 local state를 선언하여 props로 state와 setter를 넘겨주셔야 합니다.
ToDo 2. 모달창을 사용하는 컴포넌트에서 Modal 컴포넌트의 display 유무를 state를 통해 관리해주세요.
ToDo 3. 모달 안의 Input Element 등의 content는 사용할 때 자식 요소로 직접 작성해주셔야 합니다.
ToDo 4. 모달창 내 하단 버튼을 클릭했을 떄의 Click Event를 onClick={}으로 넣어주셔야 합니다.
        (이때 모달창이 닫히도록 setter에 대한 부분도 Click Event에 포함해주세요)
ToDo 5. 모달창 내 하단 버튼의 이름을 buttonName으로 넣어주셔야 합니다.
< example >
<button className="cursor-pointer" onClick={(_) => setIsOpen(true)}>
  모달창을 열립니다
</button>
{isOpen && (
  <Modal
    isOpen={isOpen}
    setIsOpen={setIsOpen}
    buttonName="Submit"
    onClick={() =>
      console.log('모달창 내 버튼을 통해 실행하고자 하는 함수!')
    }
  >
    <form>
      <input type="checkbox" id="agreecheck" />
      <label htmlFor="agreecheck">
        작성해주신 인증글 내 사진이 인증글 게시판에 업로드 됨에
        동의합니다.
      </label>
    </form>
  </Modal>
)}
* <------  사용하시기 전에 꼭 읽어주세요! ------> *
*/

import React from 'react';
import { IoClose } from 'react-icons/io5';

type PropsWithChildren<P> = P & { children?: React.ReactNode | undefined };
interface ModalProps {
  isOpen: boolean;
  setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
  buttonName: string;
  onClick: () => void;
}
export type { ModalProps };

export const Modal = ({
  isOpen,
  setIsOpen,
  buttonName,
  onClick,
  children,
}: PropsWithChildren<ModalProps>) => {
  const closeModalHandler = () => {
    setIsOpen(!isOpen);
  };

  return (
    <div className="modal-container z-50">
      <div
        className="modal-backdrop inset-0 fixed bg-slate-200/50 flex justify-center items-center"
        onClick={closeModalHandler}
      >
        <div
          className="modal-view w-3/4 bg-white rounded-lg p-5 shadow"
          //
          onClick={(e) => e.stopPropagation()}
        >
          <div className="modal-veiw-close-button h-5 flex justify-end items-center">
            <IoClose
              className="cursor-pointer p-2.5 -mr-2.5 -mt-2.5"
              size="36"
              onClick={closeModalHandler}
            />
          </div>
          <div className="modal-content pt-5 pb-5">{children}</div>
          <div className="modal-veiw-bottom-button-block flex justify-center items-center">
            <button
              className="modal-veiw-bottom-button py-2 px-5 rounded bg-black hover:bg-stone-700 text-white"
              onClick={() => {
                // closeModalHandler();
                onClick && onClick();
              }}
            >
              {buttonName}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

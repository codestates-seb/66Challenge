// <------  사용하시기 전에 꼭 읽어주세요! ------>
// ToDo 1. 버튼을 클릭했을 때의 Click Event를 onClick={}으로 넣어주셔야 합니다.
// ToDo 2. 버튼의 이름을 buttonName으로 넣어주셔야 합니다.

// <예시> 아래의 코드를 주석 해제하고 중괄호를 제거한 뒤 사용해보세요

{
  /* <WantedHabit
  onClick={() => console.log('버튼의 클릭이벤트로 전해지는 함수')}
/> */
}

// <------  사용하시기 전에 꼭 읽어주세요! ------>

import { SlArrowRight } from 'react-icons/sl';

export const WantedHabit = (props) => {
  return (
    <div className="w-80 cursor-pointer" onClick={props.onClick}>
      <div className="w-full only:flex place-content-between border-2 border-black dark:border-white">
        <span className="p-2 font-bold dark:text-white" onClick={props.onClick}>
          찜한 습관
        </span>
        <div className="pr-5">
          <SlArrowRight className="inline align-bottom dark:bg-white" />
        </div>
      </div>
    </div>
  );
};

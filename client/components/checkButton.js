// <------  사용하시기 전에 꼭 읽어주세요! ------>

// ToDo 1. 버튼을 클릭했을 때의 Click Event를 onClick={}으로 넣어주셔야 합니다.
// ToDo 2. 버튼의 이름을 buttonName으로 넣어주셔야 합니다.

// <예시> 아래의 코드를 주석을 해제하고 중괄호를 지운 뒤 사용해보세요

{
  /* <CheckButton
    buttonName="테스트"
    onClick={() => console.log('버튼의 클릭이벤트로 전해지는 함수')}
  /> */
}

// <------  사용하시기 전에 꼭 읽어주세요! ------>

export const CheckButton = (props) => {
  return (
    <button
      onClick={props.onClick}
      className="text-white bg-light-green border-2 rounded p-2"
    >
      {props.buttonName}
    </button>
  );
};

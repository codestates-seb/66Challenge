/* 
* <------  사용하시기 전에 꼭 읽어주세요! ------> *
※ 네트워크 통신을 통해 데이터를 받아오는 방식이 결정되면 아래 코드는 수정될 수 있습니다.
  현재의 코드는 한 번의 네트워크 통신으로 객체 형태의 각 habit에 대한 데이터들이 배열 형태로 server로부터 전달받는 통신 구조를 가정했습니다.

ToDo 1. Habit Element를 사용하는 컴포넌트에서 해당 Habit에 대한 habitImage(이미지), habitTitle, habitBody(body)를 props로 넘겨주셔야 합니다.
ToDo 2. 해당 Element를 사용하는 컴포넌트가 어떤 타입인지에 있어 WrapperType을 props로 넘겨주셔야 합니다.

< example >
<HabitElement
  habitImage="/image/running.png"
  habitTitle="달리기 3km"
  habitBody="매일 아침에 3km 달리기를 실천합니다. 상쾌한 아침을 맞이하며 건강도 챙겨보세요!"
  WrapperType="horizontal"
/>
* <------  사용하시기 전에 꼭 읽어주세요! ------> *
*/

export const HabitElement = ({ habitImage, habitTitle, habitBody }) => {
  return (
    <div className={`habit-element-wrapper`}>
      <div className="habit-element-image mb-2.5">
        <img
          // className={WrapperType === 'horizontal' ? 'w-[140px]' : 'w-full'}
          src={habitImage}
          alt="habit image"
        />
      </div>
      <div className="habit-element-title font-bold mb-[5px]">{habitTitle}</div>
      <div className="habit-element-body text-ellipsis overflow-hidden break-words line-clamp-2 ">
        {habitBody}
      </div>
    </div>
  );
};

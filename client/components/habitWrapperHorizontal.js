/* 
* <------  사용하시기 전에 꼭 읽어주세요! ------> *
※ 네트워크 통신을 통해 데이터를 받아오는 방식이 결정되면 아래 코드는 수정될 수 있습니다.
  현재의 코드는 한 번의 네트워크 통신으로 객체 형태의 각 habit에 대한 데이터들이 배열 형태로 server로부터 전달받는 통신 구조를 가정했습니다.

ToDo 1. HabitWrapper를 사용하는 컴포넌트에서 habitWrapperTitle, habitWrapperData를 props로 넘겨주셔야 합니다.
        (habitWrapperData는 각 habit들에 대한 데이터를 담고 있는 배열이며,
          각각의 배열 요소들은 해당 Habit에 대한 habitImage(이미지), habitTitle, habitBody를 담고 있는 객체입니다.)

< example >
<HabitWrapperHorizontal habitWrapperData={habitWrapperData} />
* <------  사용하시기 전에 꼭 읽어주세요! ------> *
*/

import { HabitElement } from './habitElement';

export const HabitWrapperHorizontal = ({
  habitWrapperTitle,
  habitWrapperData,
}) => {
  return (
    <div className="habit-wrapper-horizontal p-2.5">
      <h3 className="habit-wrapper-title mb-5 text-xl font-semibold">
        {habitWrapperTitle}
      </h3>
      <div className="habit-wrapper-content">
        <ul className="habit-wrapper-list flex overflow-x-scroll flex-nowrap gap-x-2.5">
          {habitWrapperData.map((el) => {
            return (
              <li className="habit-element flex-[0_0_auto] w-[140px] ">
                <HabitElement {...el} />
              </li>
            );
          })}
        </ul>
      </div>
    </div>
  );
};

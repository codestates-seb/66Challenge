import { HabitElement } from './habitElement';
import type { HabitWrapperProps } from './habitElement';

export const HabitWrapperHorizontal: React.FC<HabitWrapperProps> = ({
  habitWrapperTitle,
  habitWrapperData,
}) => {
  return (
    <div className="habit-wrapper-horizontal p-4 pr-0">
      <h3 className="habit-wrapper-title mb-5 text-xl font-semibold">
        {habitWrapperTitle}
      </h3>
      <div className="habit-wrapper-content">
        <ul className="habit-wrapper-list flex overflow-x-scroll flex-nowrap gap-x-4 scrollbar-hide last:pr-4">
          {habitWrapperData.map((el) => {
            return (
              <li
                className="habit-element flex-[0_0_auto] w-[140px] "
                key={el.habitId}
              >
                <HabitElement {...el} />
              </li>
            );
          })}
        </ul>
      </div>
    </div>
  );
};

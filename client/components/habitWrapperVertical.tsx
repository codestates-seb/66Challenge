import { HabitElement } from './habitElement';
import type { HabitWrapperProps } from './habitElement';
export const HabitWrapperVertical: React.FC<HabitWrapperProps> = ({
  habitWrapperTitle,
  habitWrapperData,
}) => {
  return (
    <div className="habit-wrapper-Vertical p-5">
      <h3 className="habit-wrapper-title mb-5 text-xl font-semibold">
        {habitWrapperTitle}
      </h3>
      <div className="habit-wrapper-content">
        <ul className="habit-wrapper-list grid gap-4 grid-cols-2">
          {habitWrapperData.map((el) => {
            return (
              <li className="habit-element" key={el.habitId}>
                <HabitElement {...el} />
              </li>
            );
          })}
        </ul>
      </div>
    </div>
  );
};

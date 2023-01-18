package challenge.server.habit.mapper;

import challenge.server.category.entity.Category;
import challenge.server.habit.dto.HabitDto;
import challenge.server.habit.entity.Habit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HabitMapper {
    // TODO unmapped 필드 관리하기
    Habit habitPostDtoToHabit(HabitDto.Post post);
    Habit habitPatchDtoToHabit(HabitDto.Patch patch);
    List<HabitDto.Response> habitsToHabitResponseDtos(List<Habit> habits);
    HabitDto.ResponseDetail habitToHabitResponseDetailDto(Habit habit);
    Category typeToCategory(String type);
    String CategoryToType(Category category);
}
package challenge.server.domain.habit.service;

import challenge.server.common.exception.BusinessLogicException;
import challenge.server.common.exception.ExceptionCode;
import challenge.server.file.service.FileUploadService;
import challenge.server.domain.habit.dto.HabitDto;
import challenge.server.domain.habit.entity.AgeRatio;
import challenge.server.domain.habit.entity.Habit;
import challenge.server.domain.habit.entity.SexRatio;
import challenge.server.domain.habit.entity.StatusRatio;
import challenge.server.domain.habit.mapper.HabitMapperImpl;
import challenge.server.domain.habit.repository.HabitRepository;
import challenge.server.domain.user.entity.User;
import challenge.server.domain.user.repository.UserRepository;
import challenge.server.common.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HabitService {

    private final CustomBeanUtils<Habit> beanUtils;
    private final HabitRepository habitRepository;
    private final FileUploadService fileUploadService;
    private final HabitMapperImpl habitMapper;
    private final UserRepository userRepository;

    @Transactional
    public Habit createHabit(Habit habit, Long hostId) {
        User user = userRepository.findById(hostId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        habit.setHost(user);

        return habitRepository.save(habit);
    }

    @Transactional
    public HabitDto.ResponseDetail updateHabit(Habit habit, Long userId) {
        Habit findHabit = findVerifiedHabit(habit.getHabitId());
        Habit updatingHabit = beanUtils.copyNonNullProperties(habit, findHabit);
        Habit saveHabit = habitRepository.save(updatingHabit);

        return habitMapper.habitToHabitResponseDetailDto(saveHabit, userId);
    }

    @Transactional
    public Habit updateChallengers(Long habitId, int challengers) {
        Habit findHabit = findVerifiedHabit(habitId);
        findHabit.changeChallengers(challengers);
        return habitRepository.save(findHabit);
    }

    public Habit findHabit(Long habitId) {
        return findVerifiedHabit(habitId);
    }

    // 전체 습관 조회
    public List<Habit> findAll(Long lastHabitId, int size) {
        return habitRepository.findAllNoOffset(lastHabitId, size);
    }

    // 제목 keyword로 검색(조회)
    public List<Habit> findAllByKeyword(Long lastHabitId, String keyword, int size) {
        return habitRepository.findByTitleIsContaining(lastHabitId, keyword, size);
    }

    // 특정 카테고리의 습관 조회
    public List<Habit> findAllByCategory(Long lastHabitId, Long categoryId, int size) {
        return habitRepository.findByCategory(lastHabitId, categoryId, size);
    }

    public List<Habit> findAllByScore(int page, int size) {
        return habitRepository.findAllByScore(PageRequest.of(page - 1, size));
    }

    public List<Habit> findAllByPopularity(int page, int size) {
        return habitRepository.findAllByPopularity(PageRequest.of(page - 1, size));
    }

    public List<Habit> findAllByNewest(Long lastHabitId, int size) {
        return habitRepository.findAllByNewest(lastHabitId, size);
    }

    @Transactional
    public void deleteHabit(Long habitId) {
        Habit habit = findVerifiedHabit(habitId);
        fileUploadService.delete(habit.getThumbImgUrl());
        fileUploadService.delete(habit.getSuccImgUrl());
        fileUploadService.delete(habit.getFailImgUrl());
        habitRepository.delete(habit);
    }

    @Transactional
    public HabitDto.ResponseStatistics makeHabitStatistics(Long habitId) {
        Habit habit = findHabit(habitId);

        if (habit == null) return null;
        AgeRatio ageRatio = AgeRatio.builder().build();
        SexRatio sexRatio = SexRatio.builder().build();
        StatusRatio statusRatio = StatusRatio.builder().build();
        return HabitDto.ResponseStatistics.builder()
                .ageRatio(ageRatio.makeStatistics(habit))
                .statusRatio(statusRatio.makeStatistics(habit))
                .sexRatio(sexRatio.makeStatistics(habit))
                .build();
    }

    public Habit findVerifiedHabit(Long habitId) {
        return habitRepository.findById(habitId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.HABIT_NOT_FOUND));
    }

    @Transactional
    public void calcAvgScore(Long habitId) {
        Habit habit = findVerifiedHabit(habitId);
        habit.calcAvgScore();
    }
}

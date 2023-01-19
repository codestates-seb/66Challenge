package challenge.server.habit.service;

import challenge.server.category.service.CategoryService;
import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.file.service.FileUploadService;
import challenge.server.habit.entity.Habit;
import challenge.server.habit.repository.HabitRepository;
import challenge.server.user.service.UserService;
import challenge.server.utils.CustomBeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HabitService {

    private final CustomBeanUtils<Habit> beanUtils;
    private final HabitRepository habitRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final FileUploadService fileUploadService;

    // TODO Mapper unmapped 필드 매핑 후 매개변수 줄이기 & 불필요 메서드 제거
    @Transactional
    public Habit createHabit(Habit habit, String type, Long userId) {
        habit.setCategory(categoryService.findByType(type));
        habit.setHost(userService.findUser(userId));
        return habitRepository.save(habit);
    }

    @Transactional
    public Habit updateHabit(Habit habit, String type, Long userId) {
        Habit findHabit = findVerifiedHabit(habit.getHabitId());
        Habit updatingHabit = beanUtils.copyNonNullProperties(habit,findHabit);

        updatingHabit.setCategory(categoryService.findByType(type));
        updatingHabit.setHost(userService.findUser(userId));
        return habitRepository.save(updatingHabit);
    }

    public Habit findHabit(Long habitId){
        return findVerifiedHabit(habitId);
    }

    // 전체 습관 조회
    public List<Habit> findAll(int page, int size) {
        return habitRepository.findAll(
                PageRequest.of(page-1, size, Sort.by("habitId").descending())).getContent();
    }

    // 제목 keyword로 검색(조회)
    public List<Habit> findAllByKeyword(String keyword, int page, int size) {
        return habitRepository.findByTitleIsContaining(keyword,
                PageRequest.of(page-1, size)).getContent();
    }

    // 특정 카테고리의 습관 조회
    public List<Habit> findAllByCategory(Long categoryId, int page, int size) {
        return habitRepository.findByCategoryCategoryId(categoryId,
                PageRequest.of(page-1, size, Sort.by("habitId").descending())).getContent();
    }

    // 특정 사용자(작성자)가 만든 습관 조회
    public List<Habit> findAllByUser(Long userId, int page, int size) {
        return habitRepository.findByHostUserId(userId,
                PageRequest.of(page-1,size,Sort.by("habitId").descending())).getContent();
    }

    @Transactional
    public void deleteHabit(Long habitId) {
        Habit habit = findVerifiedHabit(habitId);
        fileUploadService.delete(habit.getThumbImgUrl());
        fileUploadService.delete(habit.getSuccImgUrl());
        fileUploadService.delete(habit.getFailImgUrl());
        habitRepository.delete(habit);
    }

    public Habit findVerifiedHabit(Long habitId) {
        return habitRepository.findById(habitId)
                .orElseThrow(()-> new BusinessLogicException(ExceptionCode.HABIT_NOT_FOUND));
    }
}

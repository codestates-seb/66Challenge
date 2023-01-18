package challenge.server.habit;

import challenge.server.config.TestConfig;
import challenge.server.habit.entity.Habit;
import challenge.server.habit.repository.HabitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(TestConfig.class)
public class HabitRepositoryTest {
    @Autowired
    private HabitRepository habitRepository;

    @DisplayName("특정 키워드를 포함한 모든 습관 조회")
    @Test
    void findHabitsByTitleContains() throws Exception {
        // when
        List<Habit> findHabits1 = habitRepository.findByTitleIsContaining("Walking",PageRequest.of(0,10)).getContent();
        List<Habit> findHabits2 = habitRepository.findByTitleIsContaining("Walking",PageRequest.of(0,10)).getContent();

        // then
        assertEquals("Walking", findHabits1.get(0).getTitle());
        assertEquals(3, findHabits2.size());
    }


    @DisplayName("특정 카테고리의 습관 조회")
    @Test
    void findByCategoryCategoryId() throws Exception {
        // when
        List<Habit> habits1 = habitRepository.findByCategoryCategoryId(1L,PageRequest.of(0,10)).getContent();
        List<Habit> habits2 = habitRepository.findByCategoryCategoryId(2L,PageRequest.of(0,10)).getContent();

        // then
        assertEquals(1,habits1.size());
        assertEquals(2,habits2.size());
    }

    @DisplayName("특정 사용자가 작성한 습관 조회")
    @Test
    void findByHostUserId() throws Exception {
        // when
        List<Habit> habits1 = habitRepository.findByHostUserId(1L,PageRequest.of(0,10)).getContent();
        List<Habit> habits2 = habitRepository.findByHostUserId(2L,PageRequest.of(0,10)).getContent();

        // then
        assertEquals(1, habits1.size());
        assertEquals(2, habits2.size());
    }
}

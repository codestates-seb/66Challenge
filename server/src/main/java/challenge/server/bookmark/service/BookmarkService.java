package challenge.server.bookmark.service;

import challenge.server.bookmark.entity.Bookmark;
import challenge.server.bookmark.repository.BookmarkRepository;
import challenge.server.habit.entity.Habit;
import challenge.server.habit.repository.HabitRepository;
import challenge.server.user.entity.User;
import challenge.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final HabitRepository habitRepository;
    private final UserService userService;

    // 회원이 찜한 습관들의 목록 출력
    // todo 테스트 필요
    public List<Habit> findBookmarkHabits(Long userId, int page, int size) {
        // '현재 로그인한 회원 == 요청 보낸 회원'인지 확인
        Long loggedInUserId = userService.verifyLoggedInUser(userId);

        // Bookmark 테이블에서 해당 회원의 Bookmark 데이터를 다 받아옴
        List<Bookmark> bookmarks = bookmarkRepository.findAllByUserUserId(loggedInUserId, PageRequest.of(page - 1, size, Sort.by("habitId").descending())).getContent();

        List<Habit> habits = new ArrayList<>();

        // DB로부터 받아온, 해당 회원의 Bookmarks 데이터 중 habitId를 통해 해당 Habit 상세 정보를 Habit 테이블로부터 받아옴
        for (int i = 0; i < bookmarks.size(); i++) {
            habits.add(habitRepository.findById(bookmarks.get(i).getHabit().getHabitId()).get());
        }

        return habits;
    }

    // 북마크 추가
//    public Habit createBookmark(Long habitId, Long userId) {
//        // '현재 로그인한 회원 == 요청 보낸 회원'인지 확인
//        Long loggedInUserId = userService.verifyLoggedInUser(userId);
//    }

    // 북마크 삭제
    public void deleteBookmark(Long habitId, Long userId) {

    }


}

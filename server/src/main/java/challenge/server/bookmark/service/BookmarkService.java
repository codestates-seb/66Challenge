package challenge.server.bookmark.service;

import challenge.server.bookmark.entity.Bookmark;
import challenge.server.bookmark.repository.BookmarkRepository;
import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.habit.entity.Habit;
import challenge.server.habit.repository.HabitRepository;
import challenge.server.habit.service.HabitService;
import challenge.server.user.entity.User;
import challenge.server.user.repository.UserRepository;
import challenge.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BookmarkService {
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final HabitRepository habitRepository;
    private final UserService userService;
    private final HabitService habitService;

    // 회원이 찜한 습관들의 목록 출력
    // todo 테스트 필요
    public List<Habit> findBookmarkHabits(Long userId, int page, int size) {
        // '현재 로그인한 회원 == 요청 보낸 회원'인지 확인 = 필요 없음
//        Long loggedInUserId = userService.verifyLoggedInUser(userId);
        User findUser = userService.findVerifiedUser(userId);

        // Bookmark 테이블에서 해당 회원의 Bookmark 데이터를 다 받아옴
        List<Bookmark> bookmarks = bookmarkRepository.findAllByUserUserId(findUser.getUserId(), PageRequest.of(page - 1, size, Sort.by("bookmarkId").descending())).getContent();

        List<Habit> habits = new ArrayList<>();

        // DB로부터 받아온, 해당 회원의 Bookmarks 데이터 중 habitId를 통해 해당 Habit 상세 정보를 Habit 테이블로부터 받아옴
        for (int i = 0; i < bookmarks.size(); i++) {
            habits.add(habitRepository.findById(bookmarks.get(i).getHabit().getHabitId()).get());
        }

        return habits;
    }

    // 북마크 추가
    @Transactional
    public Bookmark createBookmark(Long habitId, Long userId) {
        // 유효한 북마크가 될 수 있는지 확인
        verifyBookmark(habitId, userId);

        User findUser = userRepository.findById(userId).get();
        Habit findHabit = habitRepository.findById(habitId).get();

        Bookmark bookmark = Bookmark.builder()
                .user(findUser)
                .habit(findHabit)
                .build();

        return bookmarkRepository.save(bookmark);
    }

    private void verifyBookmark(Long habitId, Long userId) {
        // 회원 존재하는지 확인
        userService.findVerifiedUser(userId);

        // 습관 존재하는지 확인
        habitService.findVerifiedHabit(habitId); // 추후 예림님 코드 참고해서 사용

        // 해당 회원이 해당 습관에 대해 이미 북마크를 했는지 확인
        verifyExistBookmark(userId, habitId);
    }

    // 북마크 삭제
    @Transactional
    public void deleteBookmark(Long bookmarkId) {
        Bookmark findBookmark = findVerifiedBookmark(bookmarkId);

        bookmarkRepository.delete(findBookmark);
    }

    public Bookmark findVerifiedBookmark(Long bookmarkId) {
        Optional<Bookmark> optionalBookmark = bookmarkRepository.findById(bookmarkId);
        Bookmark findBookmark = optionalBookmark.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOOKMARK_NOT_FOUND));
        return findBookmark;
    }

    public void verifyExistBookmark(Long userId, Long habitId) {
        Optional<Bookmark> optionalBookmark = bookmarkRepository.findByUserUserIdAndHabitHabitId(userId, habitId);
        if (optionalBookmark.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.BOOKMARK_EXISTS);
        }
    }
}

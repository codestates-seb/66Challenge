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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Habit> findBookmarkHabits(Long lastHabitId, Long userId, int size) {
        // '현재 로그인한 회원 == 요청 보낸 회원'인지 확인 = 필요 없음
//        Long loggedInUserId = userService.verifyLoggedInUser(userId);
        User findUser = userService.findVerifiedUser(userId);
        List<Habit> habits  = bookmarkRepository.findAllByUserUserId(lastHabitId, findUser.getUserId(), size);

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

    public Bookmark findBookmarkByUserAndHabit(Long userId, Long habitId) {
        return bookmarkRepository.findByUserUserIdAndHabitHabitId(userId, habitId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOOKMARK_NOT_FOUND));
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

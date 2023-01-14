package challenge.server.bookmark.controller;

import challenge.server.habit.controller.HabitController;
import challenge.server.habit.dto.HabitDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@Api
@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
    /* bookmark(습관 찜하기) 등록 = habit controller에 해당 요청 처리하는 핸들러 메서드 postBookmark() 있음
    userId 및 habitId로 요청,
    비로그인 회원은 로그인 페이지로 이동,
    해당 습관 제작자는 해당 버튼이 보이지 않음
     */
    private final HabitController habitController;

    // 찜하기 수정 = 찜하기 해제/취소 -> todo 이 핸들러 메서드도 habit controller에 있어야 하는지(그럴 경우 end point = /{habit-id}/bookmarks/{bookmark-id}) 검토
    @ApiOperation(value = "찜하기 취소")
    @DeleteMapping("/{bookmark-id}")
    public ResponseEntity deleteBookmark(@PathVariable("bookmark-id") @Positive Long bookmarkId) {
        // API 통신용
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // 회원이 찜한 습관들의 목록 출력
    @ApiOperation(value = "회원이 찜한 습관들의 목록 출력")
    @GetMapping("/{user-id}")
    public ResponseEntity getBookmarks(@PathVariable("user-id") @Positive Long userId) {
        // API 통신용
        List<HabitDto.Response> responses = List.of(habitController.createResponseDto(), habitController.createResponseDto(), habitController.createResponseDto(), habitController.createResponseDto(), habitController.createResponseDto());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}

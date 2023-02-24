package challenge.server.chat.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 1. 채팅방 생성 -> 습관 생성 시 자동 생성으로, 핸들러 메서드 불필요해짐
//    @PostMapping
//    public ResponseEntity createChatRoom(@RequestBody ChatRoomDto.Request chatRoomDto) {
//        // TODO ADVANCED - 메시지 전송 전까지 방 저장 X, 전송 시 초대 메시지 발행 + 해당 메시지 발행
//        // 1. requestDto 넘기기
//        // 2. Service 에서 Entity로 매핑(title) & save
//        // 3. userChatRooms에 dto로부터 participants 리스트에 따라 User와 갓 생성한 chatRoom을 set하여 save
//        // 4. responseDetail 반환. save했던 chatRoom 에서 방제목을 꺼내오고, chatRoomId로 User 목록 받기
//        return new ResponseEntity(chatRoomService.createChatRoom(chatRoomDto), HttpStatus.CREATED);
//    }

    // 2. 채팅방 상세 조회 - 채팅방 정보만 리턴, 채팅목록 정보는 따로 요청
    @GetMapping("chatrooms/{room-id}")
    public ResponseEntity getChatRoom(@PathVariable("room-id") Long roomId) {
        // 1. roomId 넘기기
        // 2. 서비스에서 roomId 로 repo 에서 찾기
        // 3. responseDetail 으로 매핑하기 (participants)
        ChatRoomDto.ResponseDetail response = chatRoomService.findChatRoom(roomId);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    // 4. 채팅방 나가기
    @GetMapping("chatrooms/leave/{room-id}")
    public ResponseEntity leaveRoom(@PathVariable("room-id") Long roomId) {

        // 1. roomId, userId(로그인 한 유저) 넘기기
        // 2. userChatRoom row를 roomId와 userId로 찾아 delete 하기
        // 2-1 if 마지막 한사람이었다면 chatRoomRepo에서 chatRoomId로 찾아 delete 하기,
        //        chatRepo에서 chatRoomId로 찾아 delete 하기
        // 3. ChatService 에서 type LEAVE 메시지 변경하기
        // 4. ~님 나갔습니다 MessageController에서 발행하기

        return new ResponseEntity(HttpStatus.OK);
    }

    // 5. 채팅방 검색
    @GetMapping("chatrooms/search")
    public ResponseEntity searchRooms(@RequestParam("keyword") String keyword) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    // 채팅방 생성 -> repo.save
    // 채팅방 초대 -> room.changeUser
    // 채팅방 나가기 -> room.changeUser

    // 6. 채팅방 입장
    @PostMapping("/habits/{habit-id}/chat/enter") // 엔드포인트 논의
    public ResponseEntity enterChatRoom(@PathVariable("habit-id") Long habitId,
                                        @RequestParam("userId") Long userId) {
        // userId와 habitId로 userChatRoom 저장 및 ChatRoomDto 리턴
        return new ResponseEntity(chatRoomService.enterChatRoom(userId, habitId), HttpStatus.CREATED);
    }
}

package challenge.server.chat.chatroom;

import challenge.server.chat.chatMessage.ChatService;
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
    private final ChatService chatService;

    // 2. 채팅방 상세 조회 - 채팅방 정보만 리턴, 채팅목록 정보는 따로 요청
    @GetMapping("chatrooms/{room-id}")
    public ResponseEntity getChatRoom(@PathVariable("room-id") Long roomId) {
        // 1. roomId 넘기기
        // 2. 서비스에서 roomId 로 repo 에서 찾기
        // 3. responseDetail 으로 매핑하기 (participants)
        ChatRoomDto.ResponseDetail response = chatRoomService.findChatRoom(roomId);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    // 3. 채팅방 입장
    @PostMapping("/habits/{habit-id}/chat/enter") // 엔드포인트 논의
    public ResponseEntity enterChatRoom(@PathVariable("habit-id") Long habitId,
                                        @RequestParam("userId") Long userId) {
        // userId와 habitId로 userChatRoom 저장 및 ChatRoomDto 리턴
        return new ResponseEntity(chatRoomService.enterChatRoom(userId, habitId), HttpStatus.CREATED);
    }

    // 4. 채팅방 나가기
    @GetMapping("chatrooms/{room-id}/leave")
    public ResponseEntity leaveRoom(@PathVariable("room-id") Long chatRoomId,
                                    @RequestParam("userId") Long userId) {
        // 1. roomId, userId로 userChatRoom 조회 및 삭제
        // 1-2. if 마지막 한사람이었다면 chatRoomRepo에서 chatRoomId로 찾아 delete 하기,
        //        chatRepo에서 chatRoomId로 찾아 delete 하기
        chatRoomService.leaveChatRoom(chatRoomId,userId);

        // 3. ChatService 에서 type LEAVE 메시지 변경하기
        // 4. ~님 나갔습니다 MessageController에서 발행하기
        return new ResponseEntity(chatService.findChatRoomsByUserId(userId), HttpStatus.OK);
    }

    // 5. 채팅방 검색
    @GetMapping("chatrooms/search")
    public ResponseEntity searchRooms(@RequestParam("keyword") String keyword) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

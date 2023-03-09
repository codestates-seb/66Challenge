package challenge.server.domain.chat.chatroom;

import challenge.server.domain.chat.chatMessage.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 3. 채팅방 입장 + ws 메시지 전송 요청하기
    @GetMapping("/habits/{habit-id}/chat/enter")
    public ResponseEntity enterChatRoom(@PathVariable("habit-id") Long habitId,
                                        @RequestParam("userId") Long userId) {
        // userId와 habitId로 userChatRoom 저장 및 ChatRoomDto 리턴
        return new ResponseEntity(chatRoomService.enterChatRoom(userId, habitId), HttpStatus.CREATED);
    }

    // 4. 채팅방 나가기 + ws 메시지 전송 요청하기
    @GetMapping("chatrooms/{room-id}/leave")
    public ResponseEntity leaveRoom(@PathVariable("room-id") Long chatRoomId,
                                    @RequestParam("userId") Long userId) {
        // roomId, userId로 userChatRoom 조회 및 삭제
        chatRoomService.leaveChatRoom(chatRoomId,userId);
        return new ResponseEntity(chatService.findChatRoomsByUserId(userId), HttpStatus.OK);
    }

    // TODO 5. 채팅방 검색
    @GetMapping("chatrooms/search")
    public ResponseEntity searchRooms(@RequestParam("keyword") String keyword) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

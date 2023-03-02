package challenge.server.chat.chatMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController // ws 메시지 전송 컨트롤러
public class ChatMessageController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    // 1. 메시지 발행
    @MessageMapping("/message") // "publish/message" 경로로 메시지 발행 시 매핑
    public void sendMessage(ChatMessageDto.Request messageDto) {
        ChatMessageDto.Response response = chatService.saveMessage(messageDto);
        // TODO ENTER, LEAVE일 때 시스템 메시지 형식으로 응답하기
        simpMessagingTemplate.convertAndSend("/subscribe/"+messageDto.getChatRoomId(), response);
    }

    @MessageMapping("/test")
    public ResponseEntity test(ChatMessageDto.Request messageDto) {
        return new ResponseEntity(messageDto,HttpStatus.OK);
    }

    // 2. 전체 채팅 조회
    @GetMapping("/chatrooms/{room-id}/chats")
    public ResponseEntity getChats(@PathVariable("room-id") Long chatRoomId,
                                   @RequestParam(value = "lastChatMessageId",required = false) Long lastChatMessageId,
                                   @RequestParam("size") int size) {
        // 1. chatRoomId를 서비스에 넘긴다.
        // 2. chatRoomId로 ChatMessage 리스트를 반환받는다. desc()
        // 3. 각각을 chatMessageDto.Response 로 매핑한다. (profileImg, nickname 포함)
        return new ResponseEntity(chatService.findAllChatsByChatRoomId(chatRoomId, lastChatMessageId, size), HttpStatus.OK);
    }

    // 4. 채팅방 목록 조회 - 특정 유저가 참여 중인 채팅방
    @GetMapping("/chatrooms")
    public ResponseEntity getChatRoomsByUserId(@RequestParam Long userId) {
        // 1. 챗서비스에서 findAllLastChatsByUserId
        // 2. 챗룸 mapper로 전환하여 리턴
        // Controller -> chatService -> QchatMessageRepo => chatRoomService => userService => 끝
        return new ResponseEntity(chatService.findChatRoomsByUserId(userId), HttpStatus.OK);
    }

}
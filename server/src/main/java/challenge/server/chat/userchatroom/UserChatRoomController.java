package challenge.server.chat.userchatroom;

import challenge.server.chat.chatroom.ChatRoomService;
import challenge.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserChatRoomController {

    private final UserChatRoomService userChatRoomService;
    private final UserService userService;
    private final ChatRoomService chatRoomService;



}

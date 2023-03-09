package challenge.server.domain.chat.userchatroom;

import challenge.server.domain.chat.chatroom.ChatRoomService;
import challenge.server.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserChatRoomController {

    private final UserChatRoomService userChatRoomService;
    private final UserService userService;
    private final ChatRoomService chatRoomService;



}

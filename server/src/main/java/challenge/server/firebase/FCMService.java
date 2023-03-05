package challenge.server.firebase;

import challenge.server.chat.chatroom.ChatRoomService;
import challenge.server.chat.userchatroom.UserChatRoomService;
import challenge.server.user.entity.User;
import challenge.server.user.service.UserService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FCMService {
    private final UserService userService;
    private final NoticeService noticeService;
    private final ChatRoomService chatRoomService;
    private final UserChatRoomService userChatRoomService;

    @Async  // 비동기로 동작하도록 설정
    public void sendNotification(User user, String title, String body, String link) {
        if (user.getFcmToken() == null) return;
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .setImage("https://s3.ap-northeast-2.amazonaws.com/challenge66.file.bucket/images/ed8e8ea1-0b1c-4cda-aba7-bb408fb7837f.png")
                .build();

        Message message = Message.builder()
                .setNotification(notification)
                .setToken(user.getFcmToken())
                .putData("title", title)
                .putData("body", body)
                .putData("link", link)
                .putData("time", LocalDateTime.now().toString())
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);

            // 발송한 알림을 DB에 저장
            Notice notice = Notice.builder()
                    .user(user)
                    .content(body)
                    .link(link)
                    .state(1)
                    .title(title)
                    .build();

            noticeService.save(notice);
        } catch (FirebaseMessagingException e) {
            log.error(user.getEmail() + ": cannot send to member push message. error info: {}", e.getMessage());
        }
    }

    /**
     * Controller에서 호출할 메서드
     * User Entity를 가져와 User에게 알림을 보낸다.
     * 트랜잭션 내부에서 동작하여 User의 FCM 토큰으로 알림을 보낸다.
     */
    @Async
    @Scheduled(cron = "${cron.cron2}")
    public void sendMotivationNotice() {
        List<User> users = userService.findAllByFcmTokenNotNull();
        String title = "66Challenge를 시작해볼까요?";
        String body = "더 나은 나를 만드는 66일, 지금 시작해보세요!";
        String link = "https://66challenge.shop";

        for (User user : users) {
            sendNotification(user, title, body, link);
        }
    }

    @Async
    public void sendReviewNotice(User user, String body, Long habitId, String username) {
        String title = username + "님이 후기를 등록했어요!";
        String link = "https://66challenge.shop/habit/detail/" + habitId + "/review";

        sendNotification(user, title, body, link);
    }

    @Async
    public void sendChatNotice(Long chatRoomId, Long userId, String content) {
        List<User> users = userChatRoomService.findUsersByChatRoomId(chatRoomId);
        String username = userService.findUser(userId).getUsername();
        Long habitId = chatRoomService.findChatRoom(chatRoomId).getHabitId();

        String title = username + "님의 메세지가 도착했습니다.";
        String body = content.substring(10) + "...";
        String link = "https://66challenge.shopchat/rooms/" + chatRoomId
                + " + https://66challenge.shopchat/chatrooms/" + habitId + "/chats";

        // 채팅창에 들어가기 위해서 2개의 api 요청이 필요
        for (User user : users) {
            sendNotification(user, title, body, link);
        }
    }
}

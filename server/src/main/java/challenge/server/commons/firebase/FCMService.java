//package challenge.server.commons.firebase;
//
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.Message;
//import com.google.firebase.messaging.WebpushConfig;
//import com.google.firebase.messaging.WebpushNotification;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//@Service
//public class FCMService {
//
//    /**
//     * 메세지를 보내는 별도의 서비스
//     * NotificationRequest에는 받을 상대의 토큰값, 푸시될 알림 메시지와 제목을 가지고 있다.
//     */
//    private static final Logger logger = LoggerFactory.getLogger(FCMService.class);
//
//    public void send(final NotificationRequest notificationRequest) throws InterruptedException {
//        Message message = Message.builder()
//                .setToken(notificationRequest.getToken())
//                .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
//                        .setNotification(new WebpushNotification(notificationRequest.getTitle(),
//                                notificationRequest.getMessage())).build()).build();
//        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
//        logger.info("Sent message: " + response);
//
//    }
//}

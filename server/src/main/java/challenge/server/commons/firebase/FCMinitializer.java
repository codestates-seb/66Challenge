//package challenge.server.commons.firebase;
//
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.io.InputStream;
//
//@Slf4j
//@Component
//public class FCMinitializer {
//
//    @Value("${fcm.certification}")
//    private String googleApplicationCredentials;
//
//    @PostConstruct
//    public void initialize() throws IOException {
//        ClassPathResource resource = new ClassPathResource(googleApplicationCredentials);
//
//        try (InputStream is = resource.getInputStream()) {
//            FirebaseOptions options = FirebaseOptions.builder()
//                    .setCredentials(GoogleCredentials.fromStream(is))
//                    .build();
//            if (FirebaseApp.getApps().isEmpty()) {
//                FirebaseApp.initializeApp(options);
//                log.info("FirebaseApp initialization complete");
//            }
//        }
//    }
//}

package challenge.server.firebase;

import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import com.google.api.gax.rpc.ApiException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * 비공개 키 파일의 인증정보를 이용해 FirebaseApp을 초기화하도록 FCMInitializer를 구현
 */
@Component
@Slf4j
public class FCMInitializer {
    @Value("${fcm.certification}")
    private String credential;

    @PostConstruct
    public void initialize() {
        ClassPathResource resource = new ClassPathResource(credential);

        try (InputStream stream = resource.getInputStream()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(stream))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("FirebaseApp initialization complete");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }
}

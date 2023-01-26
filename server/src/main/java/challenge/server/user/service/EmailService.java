package challenge.server.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Async
    public void send(String email, String verificationToken) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(email);
        smm.setSubject("[Challenge66] 회원 가입을 위한 인증 메일입니다");
        smm.setText("13.209.179.193:8080/users/email-verifications?email=" + email + "&verificationToken=" + verificationToken);

        javaMailSender.send(smm);
    }
}

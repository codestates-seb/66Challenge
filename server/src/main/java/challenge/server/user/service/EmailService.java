package challenge.server.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@EnableAsync
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Async
    public void send(String email, String verificationCode) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = null;

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("[Challenge66] 회원 가입을 위한 인증 메일입니다");

            String text = "";
            text += "안녕하세요!<br>";
            text += "Challenge66 회원 가입을 위한 이메일 인증입니다.<br>";
            text += "아래 링크를 클릭하여 이메일 주소 인증을 완료해 주세요.<br><br>";
            text += "<a href='13.209.179.193:8080/users/email-verifications?email=" + email + "&verificationCode=" + verificationCode + "' target='_blank'>링크 클릭!</a><br><br>";
            text += "감사합니다!";

            mimeMessageHelper.setText(text, true);

            javaMailSender.send(mimeMessage);
            log.info("Email sent to " + email + " with verificationCode " + verificationCode);
        } catch (Exception e) {
            log.error("Email send error: " + e);
        }
    }
}

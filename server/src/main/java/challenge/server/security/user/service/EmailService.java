package challenge.server.security.user.service;

import challenge.server.helper.email.MailHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
    private final JavaMailSenderImpl mailSender;

    @Async
    public void send(String email, String verificationCode) {
        try {
            // 방법1) 링크가 안 만들어짐
            /*
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = null;

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("[Challenge66] 회원 가입을 위한 인증 메일입니다");

            String text = "";
            text += "안녕하세요!<br>";
            text += "Challenge66 회원 가입을 위한 이메일 인증입니다.<br>";
            text += "아래 링크를 클릭하여 이메일 주소 인증을 완료해 주세요.<br><br>";
            text += "<a href=\"13.209.179.193:8080/users/email-verifications?email=" + email + "&verificationCode=" + verificationCode + "\" target=\"_blank\">링크 클릭!</a><br><br>";
            text += "감사합니다!";

            mimeMessageHelper.setText(text, true);

            javaMailSender.send(mimeMessage);
             */

            // 방법2) https://jee2memory.tistory.com/entry/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%94%84%EB%A0%88%EC%9E%84%EC%9B%8C%ED%81%AC-%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85-%EC%8B%9C-%EC%9D%B4%EB%A9%94%EC%9D%BC-%EC%9D%B8%EC%A6%9D-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0
            MailHandler mailHandler = new MailHandler(javaMailSender);
            mailHandler.setSubject("[Challenge66] 회원 가입을 위한 인증 메일입니다");
            mailHandler.setText("안녕하세요!<br>"
                    + "Challenge66 회원 가입을 위한 이메일 인증입니다.<br>"
                    + "아래 링크를 클릭하여 이메일 주소 인증을 완료해 주세요.<br><br>"
//                    + "<a href='13.209.179.193:8080/users/email-verifications?email=" + email + "&verificationCode=" + verificationCode + "' target='_blank'>링크 클릭!</a><br><br>"
                    + "http://3.38.232.88:8080/users/email-verifications?email=" + email + "&verificationCode=" + verificationCode + "<br><br>"
                    + "감사합니다!");
            mailHandler.setTo(email);
            mailHandler.send();
            log.info("Email sent to " + email + " with verificationCode " + verificationCode);
        } catch (Exception e) {
            log.error("Email send error: " + e);
        }
    }
}

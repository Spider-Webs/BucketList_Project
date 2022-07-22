package bucket.list.service.common;

import bucket.list.memberdto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SendMailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailAddress;

    public void mailSend(MailDto mailDto) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        message.setFrom(emailAddress);
        message.setReplyTo(emailAddress);

        javaMailSender.send(message);
    }

}

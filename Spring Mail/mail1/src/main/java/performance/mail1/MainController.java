package performance.mail1;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private final JavaMailSender mailSender;

    @Value("${target.username}")
    private String targetUserName;

    @Value("${spring.mail.username}")
    private String sender;

    public MainController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping("/")
    public String index() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(targetUserName);
        message.setFrom(sender);

        message.setSubject("제목");
        message.setText("내용");

        mailSender.send(message);

        return "ok";
    }

    @GetMapping("/MimeMessage")
    public String index2() throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(targetUserName);
        helper.setFrom(sender);

        helper.setSubject("제목");
        String html = """
                        <html>
                          <body>
                            <h1>제목</h1>
                            <p>이건 <b>HTML 메일</b>입니다.</p>
                            <a href="https://example.com">링크</a>
                          </body>
                        </html>
                        """;
        helper.setText(html, true);

        mailSender.send(message);

        return "ok";
    }
}

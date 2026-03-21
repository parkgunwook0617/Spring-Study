package performance.seven.component;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import performance.seven.domain.UserEntity;
import performance.seven.repository.UserRepository;

import java.io.IOException;

@Component
public class MagicLinkOneTimeTokenGenerationSuccessHandler implements OneTimeTokenGenerationSuccessHandler {
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String senderEmail;

    private final OneTimeTokenGenerationSuccessHandler redirectHandler =
            new RedirectOneTimeTokenGenerationSuccessHandler("/ott/sent");

    public MagicLinkOneTimeTokenGenerationSuccessHandler(JavaMailSender mailSender, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken) throws IOException, ServletException {
        UriComponentsBuilder builder = ServletUriComponentsBuilder.fromRequest(request)
                .replacePath(request.getContextPath())
                .replaceQuery(null)
                .fragment(null)
                .path("/login/ott")
                .queryParam("token", oneTimeToken.getTokenValue());

        String magicLink = builder.toUriString();

        sendMail(getUserEmail(oneTimeToken.getUsername()), magicLink);

        redirectHandler.handle(request, response, oneTimeToken);
    }

    private void sendMail(String to, String link) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setFrom(senderEmail);
            helper.setSubject("Your login link");

            String html = """
                <html>
                    <body>
                        <h2>Login</h2>
                        <p>Click the button below to login:</p>
                        <a href="%s"
                           style="
                               display:inline-block;
                               padding:10px 20px;
                               background-color:#4CAF50;
                               color:white;
                               text-decoration:none;
                               border-radius:5px;">
                            Login
                        </a>
                        <br><br>
                        <p>Or copy this link:</p>
                        <p>%s</p>
                    </body>
                </html>
                """.formatted(link, link);

            helper.setText(html, true);
            mailSender.send(message);

        } catch (Exception e) {
            //
        }
    }

    private String getUserEmail(String username) {
        UserEntity entity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return entity.getEmail();
    }
}

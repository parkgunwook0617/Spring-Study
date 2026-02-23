package performance.chapter0902.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import performance.chapter0902.service.LoggedUserManagementService;

@Component
@RequestScope
public class LoginProcessor {
    private final LoggedUserManagementService loggedUserManagementService;

    private String username;
    private String password;

    public LoginProcessor(LoggedUserManagementService loggedUserManagementService) {
        this.loggedUserManagementService = loggedUserManagementService;
    }

    public boolean login() {
        String username = this.getUsername();
        String password = this.getPassword();

        if("natalie".equals(username) && "password".equals(password)) {
            loggedUserManagementService.setUsername(username);
            return true;
        } else {
            return false;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

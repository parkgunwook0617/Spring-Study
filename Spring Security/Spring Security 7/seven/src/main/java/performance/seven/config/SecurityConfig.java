package performance.seven.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/logout"));

        http.formLogin(login -> login
                .loginProcessingUrl("/login")
                .loginPage("/login"));

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll()
                .requestMatchers("/join").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/user").hasAnyRole("USER")
                .requestMatchers("/admin").access(customAuthorizationManager())
                .anyRequest().denyAll()
        );

        http.rememberMe(me -> me
                .key("vmfhaltmskdlsvmfhaltmskdlsvmfhaltmskdls")
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(14 * 24 * 60 * 60)
        );

        http.sessionManagement(session -> session.sessionFixation().changeSessionId());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withRolePrefix("ROLE_")
                .role("ADMIN").implies("USER")
                .build();
    }

    private AuthorizationManager<RequestAuthorizationContext> customAuthorizationManager() {
        return (authentication, context) -> {

            boolean allowed = authentication.get().getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            return new AuthorizationDecision(allowed);
        };
    }
}

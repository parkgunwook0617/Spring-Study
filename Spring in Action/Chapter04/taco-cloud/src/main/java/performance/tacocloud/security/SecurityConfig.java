package performance.tacocloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //@Autowired
    //DataSource dataSource;

    //@Autowired
    //private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           UserDetailsService userDetailsService) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/design", "/orders").hasRole("USER")
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/", "/**").permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/design")
                        .permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .userDetailsService(userDetailsService)
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .build();
    }


//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//        UserDetails user1 = User.withUsername("user1")
//                .password(encoder.encode("password1"))
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User.withUsername("user2")
//                .password(encoder.encode("password2"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        return userDetailsManager;
//    }


//    @Autowired
//    public void configureAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
//        authBuilder
//                .ldapAuthentication()
//                .userSearchBase("ou=people")
//                .userSearchFilter("(uid={0})")
//                .groupSearchBase("ou=groups")
//                .groupSearchFilter("(member={0})")
//                .passwordCompare()
//                .passwordEncoder(new NoEncodingPasswordEncoder())
//                .passwordAttribute("userPassword")
//                .and()
//                .contextSource()
//                .root("dc=tacocloud,dc=com")
//                .ldif("classpath:users.ldif");
//    }
}

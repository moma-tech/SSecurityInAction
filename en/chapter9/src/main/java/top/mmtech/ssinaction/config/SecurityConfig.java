package top.mmtech.ssinaction.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import top.mmtech.ssinaction.filters.AuthenticationLogFilter;
import top.mmtech.ssinaction.filters.RequestValidFilter;
import top.mmtech.ssinaction.filters.UserNoPassFilter;

@Configuration
public class SecurityConfig {

    @Value("${spring.profiles.active}")
    private String env;

    @Bean
    public UserDetailsService userDetailsService() {
        final var users = new InMemoryUserDetailsManager();
        final var user1 = User.withUsername("ivan").password("123456")
                .authorities("ADMIN").build();
        final var user2 = User.withUsername("zoffy").password("123456")
                .authorities("USER").build();
        users.createUser(user1);
        users.createUser(user2);
        return users;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http)
            throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable();
        http.authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .permitAll();

        //http.formLogin().defaultSuccessUrl("/main", true);
        http.addFilterBefore(new RequestValidFilter(),
                UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new AuthenticationLogFilter(),
                UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(new UserNoPassFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests().anyRequest().hasAnyAuthority("USER",
                "ADMIN");
        return http.build();
    }
}

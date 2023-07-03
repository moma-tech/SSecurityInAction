package top.mmtech.ssinaction.config;

import java.util.List;
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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import top.mmtech.ssinaction.csrf.ZoffyCsrfService;

@Configuration
public class SecurityConfig {

  @Value("${spring.profiles.active}")
  private String env;

  @Bean
  public UserDetailsService userDetailsService() {
    final var users = new InMemoryUserDetailsManager();
    final var user1 = User.withUsername("ivan").password("123456").authorities("ADMIN").build();
    final var user2 = User.withUsername("zoffy").password("123456").authorities("USER").build();
    users.createUser(user1);
    users.createUser(user2);
    return users;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(
      final HttpSecurity http, final ZoffyCsrfService zoffyCsrfService) throws Exception {
    http.headers().frameOptions().disable();
    http.csrf().disable();
    /* Could use separate class and config properties to define the cors Config */
    http.cors(
        c -> {
          CorsConfigurationSource source =
              request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of("localhost:8080", "127.0.0.1:8080"));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTION"));
                return config;
              };
          c.configurationSource(source);
        });
    http.authorizeHttpRequests()
        .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
        .permitAll();
    http.formLogin().defaultSuccessUrl("/main", true);
    http.authorizeHttpRequests().anyRequest().permitAll();
    return http.build();
  }
}

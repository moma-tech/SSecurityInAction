package top.mmtech.ssinaction.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import top.mmtech.ssinaction.service.ZoffyAuthenticationProvider;

@Configuration
public class SecurityConfig {

  @Value("${spring.profiles.active}")
  private String env;

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SCryptPasswordEncoder sCryptPasswordEncoder() {
    return SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      ZoffyAuthenticationProvider authenticationProvider) throws Exception {
    http.formLogin().defaultSuccessUrl("/main", true);
    http.authenticationProvider(authenticationProvider);
    http.headers().frameOptions().disable();
    http.csrf().disable().authorizeHttpRequests()
        .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
        .permitAll().anyRequest().authenticated();
    return http.build();
  }
}

package top.mmtech.ssinaction.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import top.mmtech.ssinaction.support.entrypoint.CustomEntryPoint;
import top.mmtech.ssinaction.support.handler.CustomSuccessHandler;

@Configuration
public class SecurityConfig {

  @Value("${spring.profiles.active}")
  private String env;

  @Autowired AuthenticationProvider authenticationProvider;

  @Autowired CustomSuccessHandler customSuccessHandler;

  @Bean
  public UserDetailsService userDetailsServiceJdbc(DataSource dataSource) {
    return new JdbcUserDetailsManager(dataSource);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    Map<String, PasswordEncoder> encoders = new HashMap<>();
    encoders.put("noop", NoOpPasswordEncoder.getInstance());
    encoders.put("bcrypt", new BCryptPasswordEncoder());
    encoders.put("scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());
    encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
    return new DelegatingPasswordEncoder("bcrypt", encoders);
  }

  /**
   * need to set before SecurityFilterChain.
   *
   * <p>BasicAuthenticationFilter -> private SecurityContextHolderStrategy
   * securityContextHolderStrategy = SecurityContextHolder .getContextHolderStrategy(); will store
   * the stratege for later create Security Context for each request
   */
  // @Bean
  public InitializingBean securityContextMode() {
    return () ->
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.formLogin(
            c -> {
              c.successHandler(customSuccessHandler);
            })
        .httpBasic(
            c -> {
              c.authenticationEntryPoint(new CustomEntryPoint());
            });
    http.headers().frameOptions().disable();
    http.csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
        .permitAll()
        .anyRequest()
        .authenticated();
    http.authenticationProvider(authenticationProvider);
    return http.build();
  }

  // @Bean
  // public DelegatingSecurityContextAsyncTaskExecutor ssAsyncTaskExecutor(
  // ThreadPoolTaskExecutor delegate) {
  // return new DelegatingSecurityContextAsyncTaskExecutor(delegate);
  // }

}

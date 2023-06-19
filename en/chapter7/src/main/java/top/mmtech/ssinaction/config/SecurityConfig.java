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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

  @Value("${spring.profiles.active}")
  private String env;

  @Bean
  public UserDetailsService userDetailsService() {
    var users = new InMemoryUserDetailsManager();
    var user1 = User.withUsername("ivan").password("123456").roles("ADMIN")
        .authorities("READ", "WRITE").build();
    var user2 = User.withUsername("zoffy").password("123456")
        .authorities("READ","ROLE_USER").build();
    users.createUser(user1);
    users.createUser(user2);
    return users;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
      throws Exception {
    http.formLogin().defaultSuccessUrl("/main", true);
    http.headers().frameOptions().disable();
    http.csrf().disable();
    http.authorizeHttpRequests()
        .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
        .permitAll();
    http.authorizeHttpRequests().anyRequest().hasAuthority("READ");
   //http.authorizeHttpRequests().anyRequest().hasAuthority("WRITE");
    //http.authorizeHttpRequests().anyRequest().hasRole("ADMIN");
    //http.authorizeHttpRequests().anyRequest().hasRole("USER");

    return http.build();
  }
}

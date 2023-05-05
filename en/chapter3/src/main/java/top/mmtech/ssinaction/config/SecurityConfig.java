package top.mmtech.ssinaction.config;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.DefaultLdapUsernameToDnMapper;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import top.mmtech.ssinaction.entity.SimpleUser;
import top.mmtech.ssinaction.service.InMemoryuserDetailsService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    @Value("${spring.profiles.active}")
    private String env;

    @Bean
    public UserDetailsService userDetailsServiceInit(DataSource dataSource) {
        if ("h2".equals(env)) {
            return userDetailsServiceJdbc(dataSource);
        } else if ("h2-cus".equals(env)) {
            return userDetailsServiceJdbcCus(dataSource);
        } else if ("ldap".equals(env)) {
            return userDetailsServiceLdap();
        }
        return userDetailsServiceMemory();
    }

    /**
     * User Details in Memory
     */
    public UserDetailsService userDetailsServiceMemory() {
        var user = new SimpleUser("ivan", "123456", "READ");
        var users = List.of(user);
        return new InMemoryuserDetailsService(users);
    }

    /**
     * User Details in H2
     */
    public UserDetailsService userDetailsServiceJdbc(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    /**
     * User Details in H2 with custom sql
     */
    public UserDetailsService userDetailsServiceJdbcCus(DataSource dataSource) {
        String usersByUsernameQueryString =
                "select username,password,enabled from users where username=?";
        String authoritiesByUsernameQuery =
                "select username,authority from authorities where usernam=?";
        var userDetailsService = new JdbcUserDetailsManager(dataSource);
        userDetailsService.setUsersByUsernameQuery(usersByUsernameQueryString);
        userDetailsService
                .setAuthoritiesByUsernameQuery(authoritiesByUsernameQuery);
        return userDetailsService;
    }

    /**
     * User Details in LDAP
     */
    public UserDetailsService userDetailsServiceLdap() {
        var cs = new DefaultSpringSecurityContextSource(
                "ldap://localhost:8389/dc=mmtech,dc=top");
        cs.afterPropertiesSet();
        var userDetailsService = new LdapUserDetailsManager(cs);
        userDetailsService.setUsernameMapper(
                new DefaultLdapUsernameToDnMapper("ou=people", "uid"));
        userDetailsService.setPasswordAttributeName("userPassword");
        userDetailsService.setGroupSearchBase("ou=people");
        return userDetailsService;
    }

    /** Password */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /** Auth Filter */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http.httpBasic();
        http.headers().frameOptions().disable();
        http.csrf().disable().authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .permitAll().anyRequest().authenticated();
        return http.build();
    }

    /**
     * Use AuthticationManager to Config LDAP User Details Service
     */
    // @Autowired
    // public void configure(AuthenticationManagerBuilder auth) throws Exception {
    // auth.ldapAuthentication().userDnPatterns("uid={0},ou=people")
    // .groupSearchBase("ou=people").contextSource()
    // .url("ldap://localhost:8389/dc=mmtech,dc=top").and()
    // .passwordCompare()
    // .passwordEncoder(NoOpPasswordEncoder.getInstance())
    // .passwordAttribute("userPassword");
    // }
}

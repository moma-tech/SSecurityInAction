package top.mmtech.ssinaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import top.mmtech.ssinaction.entity.ZoffyUserDetails;

@Service
public class ZoffyAuthenticationProvider implements AuthenticationProvider {

  @Autowired @Lazy ZoffyUserDetailsService zoffyUserDetailsService;
  @Autowired @Lazy BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired @Lazy SCryptPasswordEncoder sCryptPasswordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    ZoffyUserDetails zoffyUserDetails = zoffyUserDetailsService.loadUserByUsername(username);

    switch (zoffyUserDetails.getUsers().getAlgorithm()) {
      case BCRYPT:
        return checkPassword(zoffyUserDetails, password, bCryptPasswordEncoder);
      case SCRYPT:
        return checkPassword(zoffyUserDetails, password, sCryptPasswordEncoder);
    }
    throw new BadCredentialsException("Bad Credentials");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }

  private Authentication checkPassword(
      ZoffyUserDetails userDetails, String rawPassword, PasswordEncoder passwordEncoder) {
    if (passwordEncoder.matches(rawPassword, userDetails.getPassword())) {
      return new UsernamePasswordAuthenticationToken(
          userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    } else {
      throw new BadCredentialsException("Bad Credentials");
    }
  }
}

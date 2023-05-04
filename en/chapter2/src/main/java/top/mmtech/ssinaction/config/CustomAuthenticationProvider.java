package top.mmtech.ssinaction.config;

import java.util.Arrays;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String userName = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        if ("ivan".equals(userName) && "12345".equals(password)) {
            return new UsernamePasswordAuthenticationToken(userName, password,
                    Arrays.asList());
        } else {
            throw new AuthenticationCredentialsNotFoundException(
                    "Error in authentication!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authentication);
    }

}

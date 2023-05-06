package top.mmtech.ssinaction.support.password;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Plant Text Password Encoder
 */
public class PlantTextPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }

}

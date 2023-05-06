package top.mmtech.ssinaction.support.password;

import org.springframework.security.crypto.password.PasswordEncoder;
import top.mmtech.ssinaction.support.hash.HashHelper;

public class Sha512PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return HashHelper.hashWithSHA512(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String hashedPassword = encode(rawPassword);
        return encodedPassword.equals(hashedPassword);
    }

}

package top.mmtech.ssinaction.support.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashHelper {


    public static String hashWithSHA512(String input) {
        StringBuilder hashed = new StringBuilder();

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
            byte[] digested = messageDigest
                    .digest(input.getBytes(StandardCharsets.UTF_8));
            for (byte digest : digested) {
                hashed.append(Integer.toHexString(0xFF & digest));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Bad Algorithm");
        }
        return hashed.toString();
    }
}

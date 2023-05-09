package top.mmtech.ssinaction.support;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class EncryptorHelper {

    public static final String SECRET = "secretString";
    public static final String SALT = KeyHelper.skg();


    public static TextEncryptor textEncryptor(int mode) {
        TextEncryptor textEncryptor = Encryptors.noOpText();
        switch (mode) {
            case 1:
                textEncryptor = Encryptors.text(SECRET, SALT);
                break;
            case 2:
                textEncryptor = Encryptors.delux(SECRET, SALT);
                break;
            default:
                textEncryptor = Encryptors.noOpText();

        }
        return textEncryptor;
    }

    public static BytesEncryptor bytesEncryptor(int mode) {
        BytesEncryptor bytesEncryptor = Encryptors.standard(SECRET, SALT);
        switch (mode) {
            case 1:
                break;
            case 2:
                bytesEncryptor = Encryptors.stronger(SECRET, SALT);
                break;
            default:
                bytesEncryptor = Encryptors.standard(SECRET, SALT);
        }
        return bytesEncryptor;
    }

    public static void main(String[] args) {
        String message = "Hello, this is Ivan";
        String textEncode;
        for(int i = 1; i <3;i++){
            textEncode = EncryptorHelper.textEncryptor(i).encrypt(message);
            System.out.println(textEncode);
            System.out.println(EncryptorHelper.textEncryptor(i).decrypt(textEncode));
        }
        byte [] byteEncode;
        byte [] messages = message.getBytes(StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(messages));
        for(int i = 1; i <3;i++){
            byteEncode = EncryptorHelper.bytesEncryptor(i).encrypt(messages);
            System.out.println(Arrays.toString(byteEncode));
            System.out.println((Arrays.toString(EncryptorHelper.bytesEncryptor(i).decrypt(byteEncode))));
        }
    }
}

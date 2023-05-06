package top.mmtech.ssinaction.support;

import java.util.Arrays;
import java.util.Objects;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

public class KeyHelper {

    public static String skg() {
        StringKeyGenerator stringKeyGenerator = KeyGenerators.string();
        return stringKeyGenerator.generateKey();
    }

    public static byte[] bkg(BytesKeyGenerator bytesKeyGenerator) {
        if(Objects.isNull(bytesKeyGenerator)){
            bytesKeyGenerator = KeyGenerators.secureRandom();
        }
        return bytesKeyGenerator.generateKey();
    }

    public static void main(String[] args) {
        System.out.println(KeyHelper.skg());
        BytesKeyGenerator bytesKeyGenerator = KeyGenerators.shared(16);
        System.out.println(Arrays.toString(KeyHelper.bkg(bytesKeyGenerator)));
        System.out.println(Arrays.toString(KeyHelper.bkg(bytesKeyGenerator)));
        System.out.println(KeyHelper.skg().length());
    }

}

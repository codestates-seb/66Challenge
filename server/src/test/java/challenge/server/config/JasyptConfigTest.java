package challenge.server.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JasyptConfigTest {

    @Test
    void jasypt() {
        String access_key = "AKIA2YC2B47XGV7MZ4C7";
        String secret_key = "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";

        System.out.println(jasyptEncoding(access_key));
        System.out.println(jasyptEncoding(secret_key));
    }

    public String jasyptEncoding(String value) {

        String key = "challenge_66_jasypt_key";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }

}
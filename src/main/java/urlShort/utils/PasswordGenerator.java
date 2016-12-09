package urlShort.utils;

import java.security.SecureRandom;

/**
 * Created by admin on 12/4/2016.
 */
public class PasswordGenerator {
    private static final int PASSWORD_LENGTH = 5;
    private static SecureRandom random = new SecureRandom();

    public static String generate() {

        String password = RandomStringGenerator.generate(PASSWORD_LENGTH);
        return  password;
        //return "1";
    }
}

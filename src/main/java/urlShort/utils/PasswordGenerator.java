package urlShort.utils;

import java.security.SecureRandom;

/**
 * Created by admin on 12/4/2016.
 */
public class PasswordGenerator {
    private static final int PASSWORD_LENGTH = 5;
    private static SecureRandom random = new SecureRandom();

    public static String generate() {


        StringBuilder password = new StringBuilder();
        while (password.length() < PASSWORD_LENGTH) {
            char character = (char) random.nextInt(Character.MAX_VALUE);
            if ((character >= 'a' && character <= 'z')
                    || (character >= 'A' && character <= 'Z')
                    || (character >= '0' && character <= '9')) {
                password.append(character);
            }
        }
        return password.toString();
        //return "1";
    }
}

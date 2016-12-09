package urlShort.utils;

import java.security.SecureRandom;

/**
 * Created by admin on 12/9/2016.
 */
public class RandomStringGenerator {
    private static SecureRandom random = new SecureRandom();

    public static String generate(int length) {


        StringBuilder randomString = new StringBuilder();
        while (randomString.length() < length) {
            char character = (char) random.nextInt(Character.MAX_VALUE);
            if ((character >= 'a' && character <= 'z')
                    || (character >= 'A' && character <= 'Z')
                    || (character >= '0' && character <= '9')) {
                randomString.append(character);
            }
        }
        return randomString.toString();
    }
}

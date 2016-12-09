package urlShort.utils;

import urlShort.service.AccountService;

import java.security.SecureRandom;

/**
 * Created by admin on 12/4/2016.
 */
public class PasswordGenerator {
    private static final int PASSWORD_LENGTH = 5;
    private static SecureRandom random = new SecureRandom();

    public static String generate() {

        String password = null;
        while(password == null || AccountService.isPasswordExist(password))
                password = RandomStringGenerator.generate(PASSWORD_LENGTH);
        return  password;
        //return "1";
    }
}

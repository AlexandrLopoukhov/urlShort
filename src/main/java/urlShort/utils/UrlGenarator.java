package urlShort.utils;

import urlShort.service.UrlService;

/**
 * Created by admin on 12/7/2016.
 */
public class UrlGenarator {
    private static final int SHORT_URL_LENGTH = 5;
    public static String generate() {
        String shortUrl = null;
        while (shortUrl == null || UrlService.isShortUrlExist(shortUrl)) {
            shortUrl = RandomStringGenerator.generate(SHORT_URL_LENGTH);
        }
        return shortUrl;

        //return "2";
    }
}

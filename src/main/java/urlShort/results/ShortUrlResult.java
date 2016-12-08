package urlShort.results;

import urlShort.model.ShortUrl;

/**
 * Created by admin on 12/7/2016.
 */
public class ShortUrlResult {
    private String shortUrl;

    public ShortUrlResult(ShortUrl shortUrl) {
        this.shortUrl = shortUrl.getShortUrl();

    }

    public ShortUrlResult(Exception e) {
        this.shortUrl = null;

    }

    public String getShortUrl() {
        return shortUrl;
    }
}

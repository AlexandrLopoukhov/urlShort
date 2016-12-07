package urlShort.model;

import urlShort.utils.UrlGenarator;

/**
 * Created by admin on 12/7/2016.
 */
public class ShortUrl {
    private String url;
    private String shortUrl;
    private int redirectType;

    public ShortUrl(UrlData urlData) {
        this.url = urlData.getUrl();
        this.redirectType = urlData.getResirectType();
        this.shortUrl = UrlGenarator.generate();
    }

    public String getUrl() {
        return url;
    }

    public String getShortUrl() {
        return shortUrl;
    }
}

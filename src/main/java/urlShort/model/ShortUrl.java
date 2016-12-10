package urlShort.model;

import urlShort.utils.UrlGenarator;

/**
 * Created by admin on 12/7/2016.
 */
public class ShortUrl {
    private String url;
    private String shortUrl;
    private int redirectType;
//TODO add redirect type

    public ShortUrl(UrlData urlData) {
        this.url = urlData.getUrl();
        this.redirectType = urlData.getRedirectType();
        this.shortUrl = UrlGenarator.generate();
    }

    public int getRedirectType() {
        return redirectType;
    }

    public String getUrl() {
        return url;
    }

    public String getShortUrl() {
        return shortUrl;
    }
}

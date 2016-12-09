package urlShort.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import urlShort.service.UrlService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by admin on 12/9/2016.
 */
@RestController
public class RedirectController {
    @RequestMapping(value = "r/{path}", method = RequestMethod.GET)
    public void redirect(HttpServletResponse httpServletResponse, @PathVariable String path){
        String url;
        url = UrlService.getUrlByShortUrl(path);
        try {
            httpServletResponse.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

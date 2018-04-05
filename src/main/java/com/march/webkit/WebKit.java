package com.march.webkit;

import java.net.HttpCookie;
import java.util.List;

/**
 * CreateAt : 2018/4/5
 * Describe :
 *
 * @author chendong
 */
public class WebKit {

    public interface Service {

        String getUserAgent();

        List<HttpCookie> getCookies(String url);
    }

    private static Service sService;

    public static Service getService() {
        return sService;
    }

    public static void setService(Service service) {
        sService = service;
    }
}

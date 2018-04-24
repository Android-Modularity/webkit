package com.march.webkit;

import com.tencent.smtt.sdk.QbSdk;

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

    private static Service sService = new Service() {
        @Override
        public String getUserAgent() {
            return null;
        }

        @Override
        public List<HttpCookie> getCookies(String url) {
            return null;
        }
    };

    public static Service getService() {
        return sService;
    }

    public static void setService(Service service) {
        sService = service;
    }

}
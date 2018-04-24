package com.march.webkit;

import java.net.HttpCookie;
import java.util.List;

/**
 * CreateAt : 2018/4/24
 * Describe :
 *
 * @author chendong
 */
public interface WebKitInjector {

    String getUserAgent();

    List<HttpCookie> getCookies(String url);

    WebKitInjector EMPTY = new WebKitInjector() {
        @Override
        public String getUserAgent() {
            return null;
        }

        @Override
        public List<HttpCookie> getCookies(String url) {
            return null;
        }
    };
}

package com.march.webkit.adapter;

import com.march.common.exts.ListX;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

/**
 * CreateAt : 2018/10/10
 * Describe :
 *
 * @author chendong
 */
public interface MetaAdapter {

    String getUserAgent();

    List<HttpCookie> getCookies(String url);

    List<String> getAllowOpenSchemes();

    MetaAdapter EMPTY = new MetaAdapter() {
        @Override
        public String getUserAgent() {
            return "";
        }

        @Override
        public List<HttpCookie> getCookies(String url) {
            return ListX.listOf();
        }

        @Override
        public List<String> getAllowOpenSchemes() {
            return new ArrayList<>();
        }
    };
}

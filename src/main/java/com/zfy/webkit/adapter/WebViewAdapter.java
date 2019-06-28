package com.zfy.webkit.adapter;

/**
 * CreateAt : 2018/10/18
 * Describe :
 *
 * @author chendong
 */
public interface WebViewAdapter {
    WebViewAdapter EMPTY = new WebViewAdapter() {
    };

    default void onReceiveTitle(String title) {
    }

    default void onPageFinished(String url) {

    }

    default boolean shouldOverrideUrlLoading(String url) {
        return false;
    }
}

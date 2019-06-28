package com.zfy.webkit.webview;

import android.content.Context;

/**
 * CreateAt : 2018/4/5
 * Describe :
 *
 * @author chendong
 */
public interface IWebViewSetting {

    void setting(Object webview);

    void syncCookie(Context context, String url);

    void destroyWebView(Object webview);

}

package com.zfy.webkit.js;

import android.app.Activity;

import com.zfy.webkit.webview.IWebView;


/**
 * CreateAt : 2017/6/27
 * Describe :
 *
 * @author chendong
 */
public class JsBridge implements IJsBridge {

    public static final String TAG = JsBridge.class.getSimpleName();

    protected IWebView mWebView;
    protected Activity mActivity;

    public void init(IWebView webView, Activity activity) {
        this.mWebView = webView;
        this.mActivity = activity;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 公用方法
    ///////////////////////////////////////////////////////////////////////////


}

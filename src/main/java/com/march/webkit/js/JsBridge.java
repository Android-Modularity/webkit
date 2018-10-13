package com.march.webkit.js;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.march.common.exts.LogX;
import com.march.common.exts.ToastX;
import com.march.webkit.webview.IWebView;


/**
 * CreateAt : 2017/6/27
 * Describe :
 *
 * @author chendong
 */
public class JsBridge implements IJsBridge {

    public static final String TAG = JsBridge.class.getSimpleName();

    private IWebView mWebView;
    private Activity mActivity;

    public void init(IWebView webView, Activity activity) {
        this.mWebView = webView;
        this.mActivity = activity;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 公用方法
    ///////////////////////////////////////////////////////////////////////////

    @JavascriptInterface
    @Override
    public void toast(String msg) {
        ToastX.show(msg);
    }

    @JavascriptInterface
    @Override
    public void log(String msg) {
        LogX.e(TAG, msg);
    }

    @JavascriptInterface
    @Override
    public void finish() {
        mActivity.finish();
    }

    @JavascriptInterface
    @Override
    public String call(String jsonParam) {
        return null;
    }

    @JavascriptInterface
    @Override
    public boolean openPage(String url, String jsonParam) {
        return false;
    }
}

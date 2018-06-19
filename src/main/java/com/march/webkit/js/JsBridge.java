package com.march.webkit.js;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.march.common.utils.LgUtils;
import com.march.common.utils.ToastUtils;
import com.march.webkit.IWebView;


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
        ToastUtils.show(msg);
    }

    @JavascriptInterface
    @Override
    public void log(String msg) {
        LgUtils.e(TAG, msg);
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

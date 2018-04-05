package com.march.webkit.js;

import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebView;

/**
 * CreateAt : 2017.09.23
 * Describe : 一个进行 js function 转换的类
 *
 * @author chendong
 */
public class JsFunction {

    private static final String JS_FUNC_PREFIX = "javascript:";

    private String mJsFuncSign;

    public JsFunction(String funcName, Object... params) {
        mJsFuncSign = generateJsFunc(funcName, params);
    }

    public void invoke(WebView webView) {
        invoke(webView, null);
    }

    public void invoke(WebView webView, ValueCallback<String> callback) {
        if (webView == null)
            return;
        if (!mJsFuncSign.startsWith(JS_FUNC_PREFIX)) {
            mJsFuncSign = JS_FUNC_PREFIX + mJsFuncSign;
        }
        // api 19
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && callback != null) {
            webView.evaluateJavascript(mJsFuncSign, callback);
        } else {
            webView.loadUrl(mJsFuncSign);
        }
    }

    // 创建一个 js 方法
    private String generateJsFunc(String funcName, Object... params) {
        StringBuilder sb = new StringBuilder(funcName).append("(");
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null) {
                if (params[i] instanceof String) {
                    sb.append("'").append(params[i]).append("'");
                } else {
                    sb.append(params[i]);
                }
                if (i != params.length - 1) {
                    sb.append(",");
                }
            }
        }
        sb.append(")");
        return sb.toString();
    }

}

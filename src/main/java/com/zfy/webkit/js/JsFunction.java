package com.zfy.webkit.js;

import com.zfy.webkit.webview.IWebView;

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

    public void invoke(IWebView webView) {
        invoke(webView, null);
    }

    public void invoke(IWebView webView, ValueCallbackAdapt<String> callback) {
        if (webView == null)
            return;
        if (!mJsFuncSign.startsWith(JS_FUNC_PREFIX)) {
            mJsFuncSign = JS_FUNC_PREFIX + mJsFuncSign;
        }
        webView.postTask(() -> {
            webView.evaluateJavascript(mJsFuncSign, callback);
        });
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

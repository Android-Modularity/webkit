package com.march.webkit.webview.x5;

import android.app.Activity;
import android.view.View;

import com.march.common.exts.LogX;
import com.march.webkit.webview.WebKitUtils;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


/**
 * CreateAt : 2017/11/6
 * Describe :
 *
 * @author luyuan
 */
public class X5WebViewClient extends WebViewClient {

    private X5WebView mMyWebView;
    private Activity  mActivity;

    public X5WebViewClient(Activity activity, X5WebView myWebView) {
        mMyWebView = myWebView;
        mActivity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (!WebKitUtils.handleBySystemIntent(mActivity, url) && !mMyWebView.mWebViewAdapter.shouldOverrideUrlLoading(url)) {
            view.loadUrl(url);
        }
        return true;
    }


    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
        mMyWebView.mWebViewSetting.syncCookie(webView.getContext(), url);
        return super.shouldInterceptRequest(webView, url);
    }


    @Override
    public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String url) {
        super.onPageFinished(webView, url);
        mMyWebView.getProgressBar().setVisibility(View.GONE);
        mMyWebView.mWebViewAdapter.onPageFinished(url);
    }

    @Override
    public void onReceivedError(WebView webView, int i, String s, String s1) {
        super.onReceivedError(webView, i, s, s1);
        LogX.all("onReceivedError", i, s, s1);
    }

    @Override
    public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        super.onReceivedError(webView, webResourceRequest, webResourceError);
        LogX.all("onReceivedError", webResourceError.getDescription());
    }

    @Override
    public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
        super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
        LogX.all("onReceivedHttpError");

    }

    @Override
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        super.onReceivedSslError(webView, sslErrorHandler, sslError);
        LogX.all("onReceivedSslError");
        sslErrorHandler.proceed();
    }

}

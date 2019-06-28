package com.zfy.webkit.webview.sys;

import android.app.Activity;
import android.net.http.SslError;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.march.common.x.LogX;
import com.zfy.webkit.webview.IWebView;
import com.zfy.webkit.webview.LoadModel;
import com.zfy.webkit.webview.WebKitUtils;


/**
 * CreateAt : 2017/11/6
 * Describe :
 *
 * @author luyuan
 */
public class SysWebViewClient extends android.webkit.WebViewClient {

    private SysWebView mMyWebView;
    private Activity   mActivity;

    public SysWebViewClient(Activity activity, SysWebView myWebView) {
        mMyWebView = myWebView;
        mActivity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (!WebKitUtils.handleBySystemIntent(mActivity, url) && !mMyWebView.mWebViewAdapter.shouldOverrideUrlLoading(url)) {
            if (view instanceof IWebView) {
                ((IWebView) view).load(LoadModel.netOf(url));
            }
        }
        return true;
    }


    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
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
        LogX.all("onReceivedError", webResourceError.toString());
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

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
        mMyWebView.mWebViewSetting.syncCookie(webView.getContext(), url);
        return super.shouldInterceptRequest(webView, url);
    }

}

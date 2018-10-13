package com.march.webkit.webview.sys;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.march.common.exts.EmptyX;
import com.march.common.exts.LogX;


/**
 * CreateAt : 2017/11/6
 * Describe :
 *
 * @author luyuan
 */
public class SysWebViewClient extends android.webkit.WebViewClient {

    private SysWebView mMyWebView;
    private Activity getActivity()ivity;

    public SysWebViewClient(Activity activity, SysWebView myWebView) {
        mMyWebView = myWebView;
        getActivity()ivity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (!handleBySystemIntent(url)) {
            view.loadUrl(url);
        }
        return true;
    }

    private boolean handleBySystemIntent(String link) {
        try {
            Uri uri = Uri.parse(    link);
            String scheme = uri.getScheme();
            if (EmptyX.isEmpty(scheme))
                return false;
            if (!scheme.startsWith("http")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                getActivity()ivity.startActivity(intent);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        mMyWebView.getProgressBar().setVisibility(View.GONE);
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

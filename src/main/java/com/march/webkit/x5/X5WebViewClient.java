package com.march.webkit.x5;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.march.common.utils.CheckUtils;
import com.march.common.utils.LogUtils;
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
    private Activity mActivity;

    public X5WebViewClient(Activity activity, X5WebView myWebView) {
        mMyWebView = myWebView;
        mActivity = activity;
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
            String url = link.replace("//", "");
            Uri uri = Uri.parse(url);
            String scheme = uri.getScheme();
            if (CheckUtils.isEmpty(scheme))
                return false;
            if (scheme.startsWith("tel")
                    || scheme.startsWith("sms")
                    || scheme.startsWith("mailto")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                mActivity.startActivity(intent);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String s) {
        super.onPageFinished(webView, s);
        mMyWebView.getProgressBar().setVisibility(View.GONE);
    }

    @Override
    public void onReceivedError(WebView webView, int i, String s, String s1) {
        super.onReceivedError(webView, i, s, s1);
        LogUtils.all("onReceivedError", i, s, s1);
    }

    @Override
    public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        super.onReceivedError(webView, webResourceRequest, webResourceError);
        LogUtils.all("onReceivedError", webResourceError.getDescription());
    }

    @Override
    public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
        super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
        LogUtils.all("onReceivedHttpError");

    }

    @Override
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        super.onReceivedSslError(webView, sslErrorHandler, sslError);
        LogUtils.all("onReceivedSslError");
        sslErrorHandler.proceed();
    }
}

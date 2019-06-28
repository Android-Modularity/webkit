package com.zfy.webkit.webview.x5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.march.common.x.EmptyX;
import com.zfy.webkit.WebKit;
import com.zfy.webkit.webview.IWebViewSetting;
import com.tencent.smtt.sdk.WebSettings;

import java.net.HttpCookie;
import java.util.List;

/**
 * CreateAt : 2018/4/5
 * Describe :
 *
 * @author chendong
 */
public class X5WebViewSetting implements IWebViewSetting {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void setting(Object webview) {
        if (!(webview instanceof X5WebView)) {
            return;
        }
        X5WebView x5WebView = (X5WebView) webview;
        WebSettings webSetting = x5WebView.getSettings();

        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setTextZoom(100);
        webSetting.setUserAgent(WebKit.getMetaAdapter().getUserAgent());
    }

    @Override
    public void syncCookie(Context context, String url) {
        if (EmptyX.isEmpty(url)) {
            return;
        }
        List<HttpCookie> cookies = WebKit.getMetaAdapter().getCookies(url);
        if (EmptyX.isEmpty(cookies)) {
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(context);
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();// 移除
        cookieManager.removeAllCookie();
        StringBuilder sbCookie = new StringBuilder();
        //为url设置cookie
        for (HttpCookie cookie : cookies) {
            sbCookie.append(cookie.getName()).append("=").append(cookie.getValue());
            sbCookie.append(";domain=").append(cookie.getDomain());
            sbCookie.append(";path=").append(cookie.getPath());
            String cookieValue = sbCookie.toString();
            cookieManager.setCookie(url, cookieValue);
        }
        CookieSyncManager.getInstance().sync();//同步cookie
    }

    @Override
    public void destroyWebView(Object obj) {
        com.tencent.smtt.sdk.WebView webView = (com.tencent.smtt.sdk.WebView) obj;
        if (webView == null) {
            return;
        }
        try {
            try {
                ((ViewGroup) webView.getParent()).removeView(webView);
            } catch (Exception ignore) {

            }
            webView.stopLoading();
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearAnimation();
            webView.loadUrl("about:blank");
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

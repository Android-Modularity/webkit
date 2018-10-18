package com.march.webkit.webview.sys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.march.common.exts.EmptyX;
import com.march.common.exts.WebViewX;
import com.march.webkit.WebKit;
import com.march.webkit.webview.IWebViewSetting;

import java.net.HttpCookie;
import java.util.List;

/**
 * CreateAt : 2018/4/5
 * Describe :
 *
 * @author chendong
 */
public class SysWebViewSetting implements IWebViewSetting {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void setting(Object webview) {

        if (!(webview instanceof SysWebView)) {
            return;
        }
        SysWebView sysWebView = (SysWebView) webview;
        //支持获取手势焦点
        sysWebView.requestFocusFromTouch();
        // 触觉反馈，暂时没发现用处在哪里
        sysWebView.setHapticFeedbackEnabled(false);

        WebSettings webSettings = sysWebView.getSettings();
        // 支持插件
        webSettings.setPluginState(WebSettings.PluginState.ON);
        // 允许js交互
        webSettings.setJavaScriptEnabled(true);
        // 设置WebView是否可以由 JavaScript 自动打开窗口，默认为 false
        // 通常与 JavaScript 的 window.open() 配合使用。
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 允许中文编码
        webSettings.setDefaultTextEncodingName("UTF-8");
        // 使用大视图，设置适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // 支持多窗口
        webSettings.setSupportMultipleWindows(false);
        // 隐藏自带缩放按钮
        webSettings.setBuiltInZoomControls(false);
        // 支持缩放
        webSettings.setSupportZoom(true);
        //设置可访问文件
        webSettings.setAllowFileAccess(true);
        //当WebView调用requestFocus时为WebView设置节点
        webSettings.setNeedInitialFocus(true);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        // 指定WebView的页面布局显示形式，调用该方法会引起页面重绘。
        // NORMAL,SINGLE_COLUMN 过时, NARROW_COLUMNS 过时 ,TEXT_AUTOSIZING
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 从Lollipop(5.0)开始WebView默认不允许混合模式，https当中不能加载http资源，需要设置开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setUserAgentString(webSettings.getUserAgentString() + WebKit.getMetaAdapter().getUserAgent());
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
        cookieManager.removeSessionCookie(); // 移除
        cookieManager.removeAllCookie();
        StringBuilder sbCookie = new StringBuilder();
        // 为url设置cookie
        for (HttpCookie cookie : cookies) {
            sbCookie.append(cookie.getName()).append("=").append(cookie.getValue());
            sbCookie.append(";domain=").append(cookie.getDomain());
            sbCookie.append(";path=").append(cookie.getPath());
        }
        cookieManager.setCookie(url,  sbCookie.toString());
        CookieSyncManager.getInstance().sync();//同步cookie
    }

    @Override
    public void destroyWebView(Object obj) {
        WebView webView = (WebView) obj;
        WebViewX.destroyWebView(webView);
    }
}

package com.march.webkit.sys;

import android.annotation.SuppressLint;
import android.os.Build;
import android.webkit.WebSettings;

/**
 * CreateAt : 2018/4/5
 * Describe :
 *
 * @author chendong
 */
public class SysWebViewSetting {

    @SuppressLint("SetJavaScriptEnabled")
    public static void setting(SysWebView webView) {
        //支持获取手势焦点
        webView.requestFocusFromTouch();
        // 触觉反馈，暂时没发现用处在哪里
        webView.setHapticFeedbackEnabled(false);

        WebSettings settings = webView.getSettings();
        // 支持插件
        settings.setPluginState(WebSettings.PluginState.ON);
        // 允许js交互
        settings.setJavaScriptEnabled(true);
        // 设置WebView是否可以由 JavaScript 自动打开窗口，默认为 false
        // 通常与 JavaScript 的 window.open() 配合使用。
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 允许中文编码
        settings.setDefaultTextEncodingName("UTF-8");
        // 使用大视图，设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        // 支持多窗口
        settings.setSupportMultipleWindows(false);
        // 隐藏自带缩放按钮
        settings.setBuiltInZoomControls(false);
        // 支持缩放
        settings.setSupportZoom(true);
        //设置可访问文件
        settings.setAllowFileAccess(true);
        //当WebView调用requestFocus时为WebView设置节点
        settings.setNeedInitialFocus(true);
        //支持自动加载图片
        settings.setLoadsImagesAutomatically(true);
        // 指定WebView的页面布局显示形式，调用该方法会引起页面重绘。
        // NORMAL,SINGLE_COLUMN 过时, NARROW_COLUMNS 过时 ,TEXT_AUTOSIZING
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 从Lollipop(5.0)开始WebView默认不允许混合模式，https当中不能加载http资源，需要设置开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }
}

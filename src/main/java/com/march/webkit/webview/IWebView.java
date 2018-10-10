package com.march.webkit.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ProgressBar;

import com.march.webkit.js.JsBridge;

/**
 * CreateAt : 2018/4/5
 * Describe :
 *
 * @author chendong
 */
public interface IWebView {

    String JS_INVOKE_NAME = "native";

    int PROGRESS_HEIGHT = 3;

    int SOURCE_ASSETS = 1;
    int SOURCE_NET = 2;
    int SOURCE_LOCAL = 3;

    Context getContext();

    void loadPage(String path, int source);

    void loadPage(String path);

    boolean onBackPressed();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void addJsBridge(JsBridge jsBridge, String name);

    void setWebViewClientAdapter(Object webViewClient);

    void setWebChromeClientAdapter(Object webChromeClient);

    ProgressBar getProgressBar();
}

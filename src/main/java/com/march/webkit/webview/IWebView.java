package com.march.webkit.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ProgressBar;

import com.march.common.able.Destroyable;
import com.march.webkit.adapter.WebViewAdapter;
import com.march.webkit.js.JsBridge;

/**
 * CreateAt : 2018/4/5
 * Describe :
 *
 * @author chendong
 */
public interface IWebView extends Destroyable {

    String JS_INVOKE_NAME = "native";

    int PROGRESS_HEIGHT = 3;

    int SOURCE_ASSETS = 1;
    int SOURCE_NET    = 2;
    int SOURCE_LOCAL  = 3;

    Context getContext();

    void loadPage(String path, int source);

    void loadPage(String path);

    void refresh();

    void attachActivity(Activity activity);

    boolean onBackPressed();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void addJsBridge(JsBridge jsBridge, String name);

    ProgressBar getProgressBar();


    void setWebViewAdapter(WebViewAdapter webViewAdapter);
}
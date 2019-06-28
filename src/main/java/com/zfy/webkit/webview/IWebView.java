package com.zfy.webkit.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ProgressBar;

import com.march.common.able.Destroyable;
import com.zfy.webkit.adapter.WebViewAdapter;
import com.zfy.webkit.js.JsBridge;
import com.zfy.webkit.js.ValueCallbackAdapt;

/**
 * CreateAt : 2018/4/5
 * Describe :
 *
 * @author chendong
 */
public interface IWebView extends Destroyable {

    String JS_INVOKE_NAME = "native";

    int PROGRESS_HEIGHT = 3;

    Context getContext();

    void load(LoadModel model);

    void refresh();

    void attachActivity(Activity activity);

    boolean onBackPressed();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void addJsBridge(JsBridge jsBridge, String name);

    ProgressBar getProgressBar();

    void evaluateJavascript(String jsFunc, ValueCallbackAdapt<String> callback);

    void setWebViewAdapter(WebViewAdapter webViewAdapter);

    void postTask(Runnable runnable);

    String getCurUrl();
}

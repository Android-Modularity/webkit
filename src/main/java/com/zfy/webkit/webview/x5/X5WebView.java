package com.zfy.webkit.webview.x5;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.zfy.webkit.R;
import com.zfy.webkit.adapter.WebViewAdapter;
import com.zfy.webkit.js.JsBridge;
import com.zfy.webkit.js.ValueCallbackAdapt;
import com.zfy.webkit.webview.IWebView;
import com.zfy.webkit.webview.IWebViewSetting;
import com.zfy.webkit.webview.LoadModel;
import com.tencent.smtt.sdk.WebView;

public class X5WebView extends WebView implements IWebView {

    IWebViewSetting mWebViewSetting;
    WebViewAdapter  mWebViewAdapter = WebViewAdapter.EMPTY;

    private ProgressBar mProgressBar;
    private Activity    mActivity;
    private LoadModel   mLoadModel;


    public X5WebView(Context context) {
        this(context, null);
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override
    public void attachActivity(Activity activity) {
        initProgressBar();
        initWebView(activity);
    }

    private void initProgressBar() {
        if (isInEditMode())
            return;
        mProgressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(AbsoluteLayout.LayoutParams.MATCH_PARENT, PROGRESS_HEIGHT);
        mProgressBar.setLayoutParams(params);
        mProgressBar.setProgressDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.webview_progress_color)));
        mProgressBar.setIndeterminate(false);
        mProgressBar.setProgress(0);
        addView(mProgressBar);
    }

    private void initWebView(Activity activity) {
        mActivity = activity;
        mWebViewSetting = new X5WebViewSetting();
        mWebViewSetting.setting(this);
        setBackgroundColor(Color.WHITE);
        setWebViewClient(new X5WebViewClient(activity, this));
        setWebChromeClient(new X5WebChromeClient(activity, this));
        addJsBridge(new JsBridge(), null);
    }


    @Override
    public void load(LoadModel model) {
        mLoadModel = model;
        switch (model.getLoadType()) {
            case LoadModel.ASSETS:
                loadUrl("file:///android_asset/" + model.getPath(), model.getHeaders());
                break;
            case LoadModel.FILE:
                loadUrl("file://" + model.getPath(), model.getHeaders());
                break;
            case LoadModel.DATA:
                if (model.getBaseUrl() == null || model.getBaseUrl().length() == 0) {
                    loadData(model.getData(), "text/html", "utf-8");
                } else {
                    loadDataWithBaseURL(model.getBaseUrl(), model.getData(), "text/html", "utf-8", null);
                }
                break;
            default:
                loadUrl(model.getPath(), model.getHeaders());
                break;
        }
    }

    @Override
    public void evaluateJavascript(String jsFunc, ValueCallbackAdapt<String> callback) {
        evaluateJavascript(jsFunc, data -> {
            if (callback != null) {
                callback.onReceiveValue(data);
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        if (canGoBack()) {
            goBack();
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    public void addJsBridge(JsBridge jsBridge, String name) {
        getSettings().setJavaScriptEnabled(true);
        jsBridge.init(this, mActivity);
        if (TextUtils.isEmpty(name)) {
            name = JS_INVOKE_NAME;
        }
        addJavascriptInterface(jsBridge, name);
    }

    @Override
    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    @Override
    public void refresh() {
        if (mLoadModel != null) {
            load(mLoadModel);
        }
    }

    @Override
    public void setWebViewAdapter(WebViewAdapter webViewAdapter) {
        mWebViewAdapter = webViewAdapter;
    }

    @Override
    public void onDestroy() {
        mWebViewAdapter = WebViewAdapter.EMPTY;
        mWebViewSetting.destroyWebView(this);
    }

    @Override
    public void postTask(Runnable runnable) {
        post(runnable);
    }

    @Override
    public String getCurUrl() {
        return this.getUrl();
    }
}

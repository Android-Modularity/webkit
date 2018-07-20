package com.march.webkit.x5;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.march.common.utils.CheckUtils;
import com.march.common.utils.LgUtils;
import com.march.webkit.IWebView;
import com.march.webkit.R;
import com.march.webkit.common.IWebViewSetting;
import com.march.webkit.js.JsBridge;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class X5WebView extends WebView implements IWebView {

    private ProgressBar mProgressBar;
    private Activity mActivity;
    IWebViewSetting mWebViewSetting;


    public X5WebView(Activity activity) {
        this(activity, null);
    }

    public X5WebView(Activity activity, AttributeSet attributeSet) {
        this(activity, attributeSet, 0);
    }

    public X5WebView(Activity activity, AttributeSet attributeSet, int i) {
        super(activity, attributeSet, i);
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
        setWebViewClientAdapter(new X5WebViewClient(activity, this));
        setWebChromeClientAdapter(new X5WebChromeClient(activity, this));
        addJsBridge(new JsBridge(), null);
    }

    @Override
    public void loadPage(String path, int source) {
        if (mActivity == null) {
            LgUtils.e("please invoke initWebView() first");
            return;
        }
        if(CheckUtils.isEmpty(path)){
            return;
        }
        switch (source) {
            case SOURCE_LOCAL:
                loadUrl("file://" + path);
                break;
            case SOURCE_NET:
                loadUrl(path);
                break;
            case SOURCE_ASSETS:
                loadUrl("file:///android_asset/" + path);
                break;
            default:
                loadUrl(path);
                break;
        }
    }

    @Override
    public void loadPage(String path) {
        loadPage(path, SOURCE_NET);
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
    public void setWebViewClientAdapter(Object webViewClient) {
        if (webViewClient instanceof WebViewClient) {
            setWebViewClient((WebViewClient) webViewClient);
        } else {
            LgUtils.e("setWebViewClientAdapter param error, use <WebViewClient>");
        }
    }

    @Override
    public void setWebChromeClientAdapter(Object webChromeClient) {
        if (webChromeClient instanceof WebChromeClient) {
            setWebChromeClient((WebChromeClient) webChromeClient);
        } else {
            LgUtils.e("setWebChromeClientAdapter param error, use <WebChromeClient>");
        }
    }

    @Override
    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

}

package com.march.webkit.x5;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.march.common.utils.DrawableUtils;
import com.march.common.utils.LogUtils;
import com.march.webkit.IWebView;
import com.march.webkit.R;
import com.march.webkit.js.JsBridge;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class X5WebView extends WebView implements IWebView {

    private ProgressBar mProgressBar;
    private Activity mActivity;

    public X5WebView(Context context) {
        this(context, null);
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initProgressBar();
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


    @Override
    public void initWebView(Activity activity) {
        mActivity = activity;
        setBackgroundColor(Color.WHITE);
        X5WebViewSetting.setting(this);
        setWebViewClientAdapter(new X5WebViewClient(activity, this));
        setWebChromeClientAdapter(new X5WebChromeClient(activity, this));
        addJsBridge(new JsBridge(), null);
    }

    @Override
    public void loadPage(String path, int source) {
        if (mActivity == null) {
            LogUtils.e("please invoke initWebView() first");
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
            LogUtils.e("setWebViewClientAdapter param error, use <WebViewClient>");
        }
    }

    @Override
    public void setWebChromeClientAdapter(Object webChromeClient) {
        if (webChromeClient instanceof WebChromeClient) {
            setWebChromeClient((WebChromeClient) webChromeClient);
        } else {
            LogUtils.e("setWebChromeClientAdapter param error, use <WebChromeClient>");
        }
    }

    @Override
    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

}

package com.march.webkit.sys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.march.common.utils.CheckUtils;
import com.march.common.utils.DrawableUtils;
import com.march.common.utils.LgUtils;
import com.march.webkit.IWebView;
import com.march.webkit.R;
import com.march.webkit.common.IWebViewSetting;
import com.march.webkit.js.JsBridge;


/**
 * CreateAt : 2017/11/6
 * Describe :
 * 系统自带的webview
 *
 * @author chendong
 */
public class SysWebView extends android.webkit.WebView implements IWebView {

    public static final int WEB_REQ_CODE = 0x123;

    public SysWebView(Activity activity) {
        this(activity, null);
    }

    public SysWebView(Activity activity, AttributeSet attrs) {
        this(activity, attrs, 0);
    }

    public SysWebView(Activity activity, AttributeSet attrs, int defStyleAttr) {
        super(activity, attrs, defStyleAttr);
        initProgressBar();
        initWebView(activity);
    }

    private Activity mActivity;
    private ProgressBar mProgressBar;
    ValueCallback<Uri[]> mFilePathCallback;
    IWebViewSetting mWebViewSetting;

    private void initProgressBar() {
        if (isInEditMode())
            return;
        mProgressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, PROGRESS_HEIGHT));
        mProgressBar.setProgressDrawable(DrawableUtils.getDrawable(getContext(), R.drawable.webview_progress_drawable));
        mProgressBar.setIndeterminate(false);
        mProgressBar.setProgress(0);
        addView(mProgressBar);
    }


    private void initWebView(Activity activity) {
        mActivity = activity;
        setBackgroundColor(Color.WHITE);
        mWebViewSetting = new SysWebViewSetting();
        mWebViewSetting.setting(this);
        initDownloadListener();
        setWebViewClientAdapter(new SysWebViewClient(activity, this));
        setWebChromeClientAdapter(new SysWebChromeClient(activity, this));
        addJsBridge(new JsBridge(), null);
    }

    private void initDownloadListener() {
        setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,
                    String contentDisposition,
                    String mimeType,
                    long contentLength) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String downLoadUrl = url;
                if (!downLoadUrl.contains("http://")) {
                    downLoadUrl = "http://" + downLoadUrl;
                }
                intent.setData(Uri.parse(downLoadUrl));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
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
        if (requestCode != WEB_REQ_CODE)
            return;
        Uri[] uris;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            uris = WebChromeClient.FileChooserParams.parseResult(resultCode, data);
        } else {
            uris = new Uri[]{data.getData()};
        }
        mFilePathCallback.onReceiveValue(uris);
        mFilePathCallback = null;
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

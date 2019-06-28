package com.zfy.webkit.webview.sys;

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
import android.widget.ProgressBar;

import com.march.common.x.ResourceX;
import com.march.common.x.WebViewX;
import com.zfy.webkit.R;
import com.zfy.webkit.adapter.WebViewAdapter;
import com.zfy.webkit.js.JsBridge;
import com.zfy.webkit.js.ValueCallbackAdapt;
import com.zfy.webkit.webview.IWebView;
import com.zfy.webkit.webview.IWebViewSetting;
import com.zfy.webkit.webview.LoadModel;


/**
 * CreateAt : 2017/11/6
 * Describe :
 * 系统自带的webview
 *
 * @author chendong
 */
public class SysWebView extends android.webkit.WebView implements IWebView {

    public static final int WEB_REQ_CODE = 0x123;

    ValueCallback<Uri[]> mFilePathCallback;
    IWebViewSetting      mWebViewSetting;
    WebViewAdapter       mWebViewAdapter = WebViewAdapter.EMPTY;

    private Activity    mActivity;
    private ProgressBar mProgressBar;
    private LoadModel   mLoadModel;


    public SysWebView(Context context) {
        this(context, null);
    }

    public SysWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SysWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void attachActivity(Activity activity) {
        initProgressBar();
        initWebView(activity);
    }

    private void initProgressBar() {
        if (isInEditMode()) {
            return;
        }
        mProgressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, PROGRESS_HEIGHT));
        mProgressBar.setProgressDrawable(ResourceX.getDrawable(getContext(), R.drawable.webview_progress_drawable));
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
        setWebViewClient(new SysWebViewClient(activity, this));
        setWebChromeClient(new SysWebChromeClient(activity, this));
        addJsBridge(new JsBridge(), JS_INVOKE_NAME);
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
    public void evaluateJavascript(String jsFunc, ValueCallbackAdapt<String> callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            evaluateJavascript(jsFunc, new ValueCallbackAdapt<String>() {
                @Override
                public void onReceiveValue(String data) {
                    if (callback != null) {
                        callback.onReceiveValue(data);
                    }
                }
            });
        } else {
            loadUrl(jsFunc);
        }
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
    public void refresh() {
        if (mLoadModel != null) {
            load(mLoadModel);
        }
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
    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    @Override
    public void setWebViewAdapter(WebViewAdapter webViewAdapter) {
        mWebViewAdapter = webViewAdapter;
    }

    @Override
    public void onDestroy() {
        mWebViewAdapter = WebViewAdapter.EMPTY;
        WebViewX.destroyWebView(this);
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

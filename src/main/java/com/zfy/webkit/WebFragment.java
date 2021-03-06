package com.zfy.webkit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.march.common.x.LogX;
import com.march.common.funcs.Consumer;
import com.zfy.webkit.webview.IWebView;
import com.zfy.webkit.webview.LoadModel;


/**
 * CreateAt : 2018/4/24
 * Describe :
 *
 * @author chendong
 */
public class WebFragment extends Fragment {

    private IWebView           mIWebView;
    private String             mUrl;
    private Consumer<IWebView> mWebViewInitConsumer;

    public WebFragment() {

    }

    public static WebFragment newInst(Bundle args) {
        WebFragment webFragment = new WebFragment();
        webFragment.setArguments(args);
        return webFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIWebView = WebKit.createWebView(getActivity());
        mIWebView.attachActivity(getActivity());
        if (mWebViewInitConsumer != null) {
            mWebViewInitConsumer.accept(mIWebView);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mUrl = getArguments().getString(WebKit.KEY_URL);
            LogX.e("URL ======> " + mUrl);
        }
        return (View) mIWebView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!TextUtils.isEmpty(mUrl)) {
            mIWebView.load(LoadModel.netOf(mUrl));
        }
    }

    public boolean onBackPressed() {
        return mIWebView.onBackPressed();
    }

    public IWebView getIWebView() {
        return mIWebView;
    }

    public void setWebViewInitConsumer(Consumer<IWebView> webViewInitConsumer) {
        mWebViewInitConsumer = webViewInitConsumer;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIWebView.onDestroy();
    }
}

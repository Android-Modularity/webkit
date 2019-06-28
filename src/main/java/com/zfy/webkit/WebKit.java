package com.zfy.webkit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.zfy.webkit.adapter.MetaAdapter;
import com.zfy.webkit.webview.IWebView;
import com.zfy.webkit.webview.sys.SysWebView;
import com.zfy.webkit.webview.x5.X5WebView;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsVideo;

/**
 * CreateAt : 2018/4/5
 * Describe :
 *
 * @author chendong
 */
public class WebKit {

    public static final String KEY_URL = "WEBKIT_KEY_URL";

    public static final int CORE_SYS = 0;
    public static final int CORE_X5  = 1;

    private static int         sCoreType;
    private static MetaAdapter sMetaAdapter = MetaAdapter.EMPTY;

    public static void init(Application app, int type, MetaAdapter adapter) {
        sCoreType = type;
        if (adapter != null) {
            sMetaAdapter = adapter;
        }
        if (sCoreType == CORE_X5) {
            QbSdk.initX5Environment(app, new QbSdk.PreInitCallback() {
                @Override
                public void onCoreInitFinished() {

                }

                @Override
                public void onViewInitFinished(boolean b) {

                }
            });
        }
    }

    public static IWebView createWebView(Activity activity) {
        IWebView iWebView;
        if (WebKit.sCoreType == WebKit.CORE_SYS) {
            iWebView = new SysWebView(activity);
        } else {
            iWebView = new X5WebView(activity);
        }
        return iWebView;
    }

    public static IWebView createWebView(Activity activity, int core) {
        IWebView iWebView;
        if (core == WebKit.CORE_SYS) {
            iWebView = new SysWebView(activity);
        } else {
            iWebView = new X5WebView(activity);
        }
        return iWebView;
    }


    public static int getCoreType() {
        return sCoreType;
    }

    public static MetaAdapter getMetaAdapter() {
        return sMetaAdapter;
    }

    public static void openVideo(Context context, String url) {
        boolean b = TbsVideo.canUseTbsPlayer(context);
        if (b) {
            TbsVideo.openVideo(context, url);
        }
    }
}
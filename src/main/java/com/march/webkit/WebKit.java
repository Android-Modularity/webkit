package com.march.webkit;

import android.app.Activity;
import android.app.Application;

import com.march.webkit.sys.SysWebView;
import com.march.webkit.x5.X5WebView;
import com.tencent.smtt.sdk.QbSdk;

/**
 * CreateAt : 2018/4/5
 * Describe :
 *
 * @author chendong
 */
public class WebKit {

    static class WebKitConfig {
        int webkitType = CORE_SYS;
    }

    public static final String KEY_URL = "key-url";
    public static final int CORE_SYS = 0;
    public static final int CORE_X5 = 1;

    private static WebKitConfig sWebKitConfig;
    private static WebKitInjector sWebKitInjector = WebKitInjector.EMPTY;

    public static WebKitInjector getInjector() {
        return sWebKitInjector;
    }

    public static WebKitConfig getWebKitConfig() {
        return sWebKitConfig;
    }

    public static IWebView createWebView(Activity activity) {
        IWebView iWebView;
        if (WebKit.getWebKitConfig().webkitType == WebKit.CORE_SYS) {
            iWebView = new SysWebView(activity);
        } else {
            iWebView = new X5WebView(activity);
        }
        return iWebView;
    }

    public static void init(Application application, int type, WebKitInjector injector) {
        if (injector != null) {
            sWebKitInjector = injector;
        }
        sWebKitConfig = new WebKitConfig();
        sWebKitConfig.webkitType = type;
        QbSdk.initX5Environment(application, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {

            }
        });
    }
}
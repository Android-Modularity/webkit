package com.march.webkit;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;

/**
 * CreateAt : 2018/4/5
 * Describe :
 *
 * @author chendong
 */
public class WebKit {

    private static WebKitInjector sWebKitInjector = WebKitInjector.EMPTY;

    public static WebKitInjector getInjector() {
        return sWebKitInjector;
    }

    public static void init(Application application, WebKitInjector injector) {
        if(injector!=null) {
            sWebKitInjector = injector;
        }
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
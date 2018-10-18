package com.march.webkit.webview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.march.common.exts.EmptyX;
import com.march.webkit.WebKit;

/**
 * CreateAt : 2018/10/18
 * Describe :
 *
 * @author chendong
 */
public class WebKitUtils {

    public static boolean handleBySystemIntent(Activity activity, String url) {
        try {
            Uri uri = Uri.parse(url);
            String scheme = uri.getScheme();
            if (EmptyX.isEmpty(scheme)) {
                return false;
            }
            if (WebKit.getMetaAdapter().getAllowOpenSchemes().contains(scheme)
                    || scheme.startsWith("tel")
                    || scheme.startsWith("sms")
                    || scheme.startsWith("mailto")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                activity.startActivity(intent);
                return true;
            }
            // 只有 http 的才加载
            return !url.startsWith("http");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

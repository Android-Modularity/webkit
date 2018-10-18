package com.march.webkit.webview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.march.common.exts.EmptyX;

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
            if (scheme.startsWith("tel")
                    || scheme.startsWith("sms")
                    || scheme.startsWith("mailto")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                activity.startActivity(intent);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}

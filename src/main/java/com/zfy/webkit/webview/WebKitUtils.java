package com.zfy.webkit.webview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.march.common.x.EmptyX;
import com.march.common.x.ToastX;
import com.zfy.webkit.WebKit;

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
            if (scheme == null) {
                return false;
            }
            if (WebKit.getMetaAdapter().getAllowOpenSchemes().contains(scheme)
                    || scheme.startsWith("tel")
                    || scheme.startsWith("sms")
                    || scheme.startsWith("mailto")) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    activity.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                    ToastX.show("应用未安装～");
                }
                return true;
            }
            // 只有 http 的才加载
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

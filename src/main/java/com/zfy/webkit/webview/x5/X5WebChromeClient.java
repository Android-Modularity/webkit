package com.zfy.webkit.webview.x5;

import android.app.Activity;
import android.view.View;

import com.tencent.smtt.sdk.WebChromeClient;

/**
 * CreateAt : 2017/11/6
 * Describe :
 *
 * @author luyuan
 */
public class X5WebChromeClient extends WebChromeClient {

    private X5WebView mMyWebView;
    private Activity  mActivity;

    public X5WebChromeClient(Activity activity, X5WebView myWebView) {
        mMyWebView = myWebView;
        mActivity = activity;
    }

    @Override
    public void onReceivedTitle(com.tencent.smtt.sdk.WebView webView, String title) {
        super.onReceivedTitle(webView, title);
        mMyWebView.mWebViewAdapter.onReceiveTitle(title);
    }

    //
//    @Override
//    public boolean onShowFileChooser(WebView webView,
//            ValueCallback<Uri[]> filePathCallback,
//            FileChooserParams fileChooserParams) {
//        if (mActivity == null)
//            return false;
//        /*
//        How to use:
//        1. Build an intent using {@link #createIntent}
//        2. Fire the intent using {@link android.app.Activity#startActivityForResult}.
//        3. Check for ActivityNotFoundException and take a user friendly action if thrown.
//        4. Listen the result using {@link android.app.Activity#onActivityResult}
//        5. Parse the result using {@link #parseResult} only if media capture was not requested.
//        6. Send the result using mFilePathCallback of {@link WebChromeClient#onShowFileChooser}
//        * */
//        Intent intent;
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            intent = fileChooserParams.createIntent();
//        } else {
//            intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("*/*");
//        }
//        mActivity.startActivityForResult(intent, SysWebView.WEB_REQ_CODE);
//        mMyWebView.mFilePathCallback = filePathCallback;
//        return true;
//    }
//
//    // 响应提示
//    @Override
//    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
//        new AlertDialog.Builder(view.getContext())
//                .setCancelable(false)
//                .setMessage(message)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.confirm();
//                        dialog.dismiss();
//                    }
//                })
//                .create()
//                .show();
//        return true;
//    }
//
//
//    @Override
//    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
//        new AlertDialog.Builder(view.getContext())
//                .setCancelable(false)
//                .setMessage(message)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.confirm();
//                        dialog.dismiss();
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.cancel();
//                        dialog.dismiss();
//                    }
//                })
//                .create()
//                .show();
//        return true;
//    }
//
//    // 响应用户输入
//    @Override
//    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
//        final EditText et = new EditText(view.getContext());
//        et.setText(message);
//        et.setSelection(message.length());
//        et.setTextSize(16);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
//        et.setLayoutParams(lp);
//
//        new AlertDialog.Builder(view.getContext())
//                .setView(et)
//                .setCancelable(false)
//                .setMessage(message)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.confirm(et.getText().toString().trim());
//                        dialog.dismiss();
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.cancel();
//                        dialog.dismiss();
//                    }
//                })
//                .create()
//                .show();
//        return true;
//    }
//

    @Override
    public void onProgressChanged(com.tencent.smtt.sdk.WebView webView, int newProgress) {
        super.onProgressChanged(webView, newProgress);
        if (newProgress == 100) {
            mMyWebView.getProgressBar().setVisibility(View.GONE);
        } else {
            if (mMyWebView.getProgressBar().getVisibility() == View.GONE) {
                mMyWebView.getProgressBar().setVisibility(View.VISIBLE);
            }
            mMyWebView.getProgressBar().setProgress(newProgress);
        }
    }


//
//
//    // settings.setSupportMultipleWindows(false);
//    // 兼容 _blank 点击
//    @Override
//    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
//        WebView.HitTestResult result = view.getHitTestResult();
//        String data = result.getExtra();
//        WebView.WebViewTransport transport =
//                (WebView.WebViewTransport) resultMsg.obj;
//        transport.setWebView(view);
//        resultMsg.sendToTarget();
//        return true;
//    }

}

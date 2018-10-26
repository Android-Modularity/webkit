WebView

> 使用系统内核和腾讯X5内核封装



```java
// 实现刷新逻辑
HViewX.initSmartRefresh(mRefreshSrl, null, v -> {
    if (mWebFragment.getIWebView() != null) {
        mWebFragment.getIWebView().refresh();
    }
});

// 初始化 Fragment
mWebFragment = WebFragment.newInst(getIntent().getExtras());
1
// 设置 WebViewAdapter
mWebFragment.setWebViewInitConsumer(webview -> {
    webview.setWebViewAdapter(new WebViewAdapter() {
        @Override
        public void onReceiveTitle(String title) {
            mTitleView.getCenterView().setText(title);
        }
        @Override
        public void onPageFinished(String url) {
            mRefreshSrl.finishRefresh();
        }
    });
});

// 加入 页面
getSupportFragmentManager().beginTransaction().add(R.id.container_fl, mWebFragment, "web")
        .show(mWebFragment).commit();
```

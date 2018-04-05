package com.march.webkit.js;

/**
 * CreateAt : 7/18/17
 * Describe : JsBridge 接口，声明共有方法
 *
 * @author chendong
 */
interface IJsBridge {

    void toast(String msg);

    void log(String msg);

    void finish();

    String call(String jsonParam);

    boolean openPage(String url, String jsonParam);
}

package com.zfy.webkit.webview;

import java.util.HashMap;
import java.util.Map;

/**
 * CreateAt : 2019/6/3
 * Describe :
 *
 * @author chendong
 */
public class LoadModel {

    public static final int ASSETS = 1;
    public static final int NET    = 2;
    public static final int FILE   = 3;
    public static final int DATA   = 4;

    public Map<String, String> headers = new HashMap<>();

    // 加载类型
    private int loadType;

    // load data
    private String baseUrl;
    private String data;
    // load url
    private String path;


    public static LoadModel assetsOf(String path) {
        LoadModel loadModel = new LoadModel();
        loadModel.loadType = ASSETS;
        loadModel.path = path;
        return loadModel;
    }

    public static LoadModel netOf(String path) {
        LoadModel loadModel = new LoadModel();
        loadModel.loadType = NET;
        loadModel.path = path;
        return loadModel;
    }

    public static LoadModel fileOf(String path) {
        LoadModel loadModel = new LoadModel();
        loadModel.loadType = FILE;
        loadModel.path = path;
        return loadModel;
    }

    public static LoadModel dataOf(String baseUrl, String data) {
        LoadModel loadModel = new LoadModel();
        loadModel.loadType = DATA;
        loadModel.baseUrl = baseUrl;
        loadModel.data = data;
        return loadModel;
    }

    public Map<String, String> getHeaders() {
        if (headers == null) {
            headers = new HashMap<>();
        }
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public int getLoadType() {
        return loadType;
    }

    public void setLoadType(int loadType) {
        this.loadType = loadType;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

package com.lifang.client;

import java.util.Map;

public class BaseClient {
    public BaseClient() {}
    public BaseClient(String serverURL) {}
    public BaseClient(String serverURL, int soTimeout) {}
    public String doGet(String url) { return ""; }
    public String doPost(String url, String params) { return ""; }
    public String httpPost(Map<String, Object> map, String action) { return ""; }
    public void setActionSuffix(String suffix) {}
    public void setApp(String app) {}
}

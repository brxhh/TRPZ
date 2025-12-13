package com.edu.web.browserapp.requestHandle;

import javafx.scene.web.WebView;

public class RequestContext {
    private final WebView webView;
    private final String url;
    private boolean isHandled = false;

    public RequestContext(WebView webView, String url) {
        this.webView = webView;
        this.url = url;
    }

    public WebView getWebView() {
        return this.webView;
    }

    public String getUrl() {
        return this.url;
    }

    public boolean isHandled() {
        return this.isHandled;
    }

    public void setHandled() {
        this.isHandled = true;
    }

}

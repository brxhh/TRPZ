package com.edu.web.browserapp.requestHandle.handler;

import com.edu.web.browserapp.requestHandle.RequestContext;

public class NetworkHandler extends BaseRequestHandler {

    @Override
    public void handle(RequestContext requestContext) {
        requestContext.getWebView()
                .getEngine()
                .load(requestContext.getUrl());

        requestContext.setHandled();
    }
}

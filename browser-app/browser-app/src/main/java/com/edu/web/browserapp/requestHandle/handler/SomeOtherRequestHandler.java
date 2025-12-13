package com.edu.web.browserapp.requestHandle.handler;

import com.edu.web.browserapp.requestHandle.RequestContext;

public class SomeOtherRequestHandler extends BaseRequestHandler {

    @Override
    public void handle(RequestContext requestContext) {
        passToNext(requestContext);
    }
}

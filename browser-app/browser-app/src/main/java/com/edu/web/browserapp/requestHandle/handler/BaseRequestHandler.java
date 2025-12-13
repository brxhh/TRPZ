package com.edu.web.browserapp.requestHandle.handler;

import com.edu.web.browserapp.requestHandle.RequestContext;
import com.edu.web.browserapp.requestHandle.IRequestHandler;

abstract class BaseRequestHandler implements IRequestHandler {
    protected IRequestHandler next;

    @Override
    public IRequestHandler setNext(IRequestHandler IRequestHandler) {
        this.next = IRequestHandler;
        return IRequestHandler;
    }

    protected void passToNext(RequestContext  requestContext) {
        if (next != null && !requestContext.isHandled()) {
            next.handle(requestContext);
        }
    }
}

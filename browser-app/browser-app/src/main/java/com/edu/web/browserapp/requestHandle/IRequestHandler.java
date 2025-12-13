package com.edu.web.browserapp.requestHandle;

public interface IRequestHandler {
    IRequestHandler setNext(IRequestHandler IRequestHandler);
    void handle(RequestContext requestContext);
}

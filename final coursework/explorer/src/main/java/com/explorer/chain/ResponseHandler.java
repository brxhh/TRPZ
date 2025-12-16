package com.explorer.chain;

import com.explorer.connection.Response;

abstract class ResponseHandler implements IResponseHandler {
    protected IResponseHandler nextHandler;

    @Override
    public void setNextHandler(IResponseHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected Response passToNext(Response response) {
        if (nextHandler != null) {
            return nextHandler.handle(response);
        }
        return response;
    }
}
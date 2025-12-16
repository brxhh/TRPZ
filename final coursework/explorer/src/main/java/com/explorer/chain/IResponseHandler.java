package com.explorer.chain;


import com.explorer.connection.Response;

public interface IResponseHandler {
    void setNextHandler(IResponseHandler nextHandler);
    Response handle(Response response);
}
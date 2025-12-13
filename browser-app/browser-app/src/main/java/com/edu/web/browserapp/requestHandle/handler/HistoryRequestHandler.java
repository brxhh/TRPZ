package com.edu.web.browserapp.requestHandle.handler;

import com.edu.web.browserapp.requestHandle.RequestContext;
import com.edu.web.browserapp.service.IHistoryService;
import com.edu.web.browserapp.service.impl.ClientHistoryService;

public class HistoryRequestHandler extends BaseRequestHandler {

    private final IHistoryService historyService = ClientHistoryService.getInstance();

    @Override
    public void handle(RequestContext requestContext) {
        this.historyService.saveToHistory(requestContext.getUrl());
        passToNext(requestContext);
    }
}

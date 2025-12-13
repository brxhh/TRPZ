package com.edu.web.restservicewebbrowser.factory.resource;

import com.edu.web.restservicewebbrowser.domain.resource.JsResource;
import com.edu.web.restservicewebbrowser.domain.resource.Resource;

public class JsResourceFactory extends ResourceFactory {

    @Override
    protected byte[] postProcessData(byte[] data) {
        System.out.println(" -> [JS] Перевірка скрипта на віруси...");
        return data;
    }

    @Override
    protected Resource createResource(String url, byte[] rawData) {
        return new JsResource(url, new String(rawData));
    }
}
package com.edu.web.restservicewebbrowser.factory.resource;

import com.edu.web.restservicewebbrowser.domain.resource.CssResource;
import com.edu.web.restservicewebbrowser.domain.resource.Resource;

public class CssResourceFactory extends ResourceFactory {

    @Override
    protected boolean isValidUrl(String url) {
        return url.endsWith(".css");
    }

    @Override
    protected byte[] postProcessData(byte[] data) {
        System.out.println(" -> [CSS] Виконую мініфікацію CSS-коду (видалення пробілів)...");
        String content = new String(data);
        return (content + " [MINIFIED]").getBytes();
    }

    @Override
    protected Resource createResource(String url, byte[] rawData) {
        return new CssResource(url, new String(rawData));
    }
}
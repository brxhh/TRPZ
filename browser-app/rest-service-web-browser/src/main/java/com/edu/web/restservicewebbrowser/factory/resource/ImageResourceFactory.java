package com.edu.web.restservicewebbrowser.factory.resource;

import com.edu.web.restservicewebbrowser.domain.resource.ImageResource;
import com.edu.web.restservicewebbrowser.domain.resource.Resource;

public class ImageResourceFactory extends ResourceFactory {

    @Override
    protected byte[] postProcessData(byte[] data) {
        System.out.println(" -> [Image] Оптимізація зображення не потрібна, повертаємо як є.");
        return data;
    }

    @Override
    protected Resource createResource(String url, byte[] rawData) {
        return new ImageResource(url, rawData);
    }
}
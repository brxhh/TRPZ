package com.edu.web.restservicewebbrowser.factory.resource;

import com.edu.web.restservicewebbrowser.domain.resource.Resource;

public abstract class ResourceFactory {

    public final Resource processResource(String url) {
        System.out.println("\n[Template Method] Початок обробки: " + url);

        if (!isValidUrl(url)) {
            throw new IllegalArgumentException("Невалідний URL або тип файлу: " + url);
        }

        byte[] rawData = downloadFromUrl(url);

        byte[] processedData = postProcessData(rawData);

        Resource resource = createResource(url, processedData);

        System.out.println("[Template Method] Ресурс успішно створено.");
        return resource;
    }

    protected boolean isValidUrl(String url) {
        return true;
    }

    private byte[] downloadFromUrl(String url) {
        System.out.println(" -> Завантаження байтів з мережі...");
        return ("Content of " + url).getBytes();
    }

    protected abstract byte[] postProcessData(byte[] data);

    protected abstract Resource createResource(String url, byte[] data);
}
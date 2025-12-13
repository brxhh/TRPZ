package com.edu.web.restservicewebbrowser.domain.resource;

import com.edu.web.restservicewebbrowser.visitor.resource.IResourceVisitor;

public class ImageResource extends Resource {
    private final byte[] imageData;

    public ImageResource(String url, byte[] imageData) {
        super(url);
        this.imageData = imageData;
    }

    public byte[] getImageData() {
        return imageData;
    }

    @Override
    public void visit(IResourceVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}

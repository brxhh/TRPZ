package com.edu.web.restservicewebbrowser.domain.resource;

import com.edu.web.restservicewebbrowser.visitor.resource.IResourceVisitor;

public abstract class Resource {
    protected String url;

    public abstract void visit(IResourceVisitor visitor) throws Exception;

    public Resource(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [url=" + url + "]";
    }
}

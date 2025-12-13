package com.edu.web.restservicewebbrowser.domain.resource;

import com.edu.web.restservicewebbrowser.visitor.resource.IResourceVisitor;

public class CssResource extends Resource {

    private final String cssContent;

    public CssResource(String url, String cssContent) {
        super(url);
        this.cssContent = cssContent;
    }

    public String getCssContent() {
        return cssContent;
    }

    @Override
    public void visit(IResourceVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}

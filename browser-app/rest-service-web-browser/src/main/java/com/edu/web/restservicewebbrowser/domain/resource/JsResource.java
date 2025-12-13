package com.edu.web.restservicewebbrowser.domain.resource;

import com.edu.web.restservicewebbrowser.visitor.resource.IResourceVisitor;

public class JsResource extends Resource {
    private final String jsContent;

    public JsResource(String url, String jsContent) {
        super(url);
        this.jsContent = jsContent;
    }

    public String getJsContent() {
        return jsContent;
    }

    @Override
    public void visit(IResourceVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}

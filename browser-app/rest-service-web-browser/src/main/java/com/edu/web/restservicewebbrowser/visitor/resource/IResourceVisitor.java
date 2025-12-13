package com.edu.web.restservicewebbrowser.visitor.resource;

import com.edu.web.restservicewebbrowser.domain.resource.CssResource;
import com.edu.web.restservicewebbrowser.domain.resource.ImageResource;
import com.edu.web.restservicewebbrowser.domain.resource.JsResource;

public interface IResourceVisitor {
    void visit(CssResource resource) throws Exception;
    void visit(JsResource resource) throws Exception;
    void visit(ImageResource resource) throws Exception;
}

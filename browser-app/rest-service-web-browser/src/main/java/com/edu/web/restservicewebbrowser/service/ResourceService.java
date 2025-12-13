package com.edu.web.restservicewebbrowser.service;

import com.edu.web.restservicewebbrowser.domain.resource.Resource;

public interface ResourceService {
    void saveResource(Resource resource, int webPageId);
}

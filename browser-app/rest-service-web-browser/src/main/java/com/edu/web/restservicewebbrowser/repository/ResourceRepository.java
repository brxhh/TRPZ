package com.edu.web.restservicewebbrowser.repository;

import com.edu.web.restservicewebbrowser.domain.resource.Resource;

public interface ResourceRepository {
    void save(Resource resource, int webPageId);
}

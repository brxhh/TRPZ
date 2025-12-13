package com.edu.web.restservicewebbrowser.service.impl;

import com.edu.web.restservicewebbrowser.domain.resource.Resource;
import com.edu.web.restservicewebbrowser.repository.ResourceRepository;
import com.edu.web.restservicewebbrowser.service.ResourceService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ResourceServiceImpl implements ResourceService {

    @Inject
    private ResourceRepository resourceRepository;

    public void saveResource(Resource resource, int webPageId) {
        resourceRepository.save(resource, webPageId);
    }
}

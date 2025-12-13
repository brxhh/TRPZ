package com.edu.web.restservicewebbrowser.service.impl;

import com.edu.web.restservicewebbrowser.domain.resource.Resource;
import com.edu.web.restservicewebbrowser.factory.resource.CssResourceFactory;
import com.edu.web.restservicewebbrowser.factory.resource.ImageResourceFactory;
import com.edu.web.restservicewebbrowser.factory.resource.JsResourceFactory;
import com.edu.web.restservicewebbrowser.factory.resource.ResourceFactory;
import com.edu.web.restservicewebbrowser.repository.WebPageRepository;
import com.edu.web.restservicewebbrowser.service.ParsingService;
import com.edu.web.restservicewebbrowser.service.ResourceService;
import jakarta.ejb.Asynchronous;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


@ApplicationScoped
public class ParsingServiceImpl implements ParsingService {

    @Inject
    private WebPageRepository webPageRepository;

    @Inject
    private ResourceService resourceService;

    @Override
    @Asynchronous
    public void parseAndSaveAll(String pageUrl) {
        System.out.println("Розпочато асинхронний парсинг для: " + pageUrl);
        try {
            Document doc = Jsoup.connect(pageUrl).get();

            int webPageId = webPageRepository.saveAndGetId(pageUrl, doc.html());

            Elements cssLinks = doc.select("link[rel=stylesheet]");
            for (Element link : cssLinks) {
                processResource(link.absUrl("href"), webPageId);
            }

            Elements jsLinks = doc.select("script[src]");
            for (Element link : jsLinks) {
                processResource(link.absUrl("src"), webPageId);
            }

            Elements imgLinks = doc.select("img[src]");
            for (Element link : imgLinks) {
                processResource(link.absUrl("src"), webPageId);
            }

            System.out.println("Парсинг та збереження для " + pageUrl + " (ID=" + webPageId + ") завершено.");

        } catch (Exception e) {
            System.err.println("Помилка парсингу: " + e.getMessage());
        }
    }

    private void processResource(String url, int webPageId) {
        try {
            ResourceFactory factory;

            if (url.endsWith(".css")) {
                factory = new CssResourceFactory();
            } else if (url.endsWith(".js")) {
                factory = new JsResourceFactory();
            } else if (url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".svg")) {
                factory = new ImageResourceFactory();
            } else {
                return;
            }

            Resource resource = factory.processResource(url);

            resourceService.saveResource(resource, webPageId);

        } catch (Exception e) {
            System.err.println("Не вдалося обробити ресурс: " + url + " | " + e.getMessage());
        }
    }
}
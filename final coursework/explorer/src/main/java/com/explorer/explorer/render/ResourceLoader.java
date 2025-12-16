package com.explorer.explorer.render;

import com.explorer.connection.DataFetcher;
import com.explorer.explorer.render.model.TagEntity;
import com.explorer.explorer.render.model.PageItem;
import com.explorer.connection.UrlResolver;

import java.util.HashMap;
import java.util.Map;

public class ResourceLoader implements ItemVisitor {

    private final DataFetcher dataFetcher;
    private final String baseUrl;
    private final Map<String, String> loadedScripts = new HashMap<>();
    private int inlineScriptCounter = 0;

    public ResourceLoader(DataFetcher dataFetcher, String baseUrl) {
        this.dataFetcher = dataFetcher;
        this.baseUrl = baseUrl;
    }

    public Map<String, String> getLoadedScripts() {
        return loadedScripts;
    }

    @Override
    public void head(PageItem pageItem) {
        if (!(pageItem instanceof TagEntity tagEntity)) {
            return;
        }

        String tagName = tagEntity.tagName().toLowerCase();

        System.out.println("tagName: " + tagName);
        System.out.println("attr: " + tagEntity.attr("rel"));
        System.out.println("Attributes: " + tagEntity.attributes());



        if (tagName.equals("link") && "stylesheet".equalsIgnoreCase(tagEntity.getAttribute("rel"))) {
            String href = tagEntity.getAttribute("href");
            if (!href.isEmpty()) {
                try {
                    String absoluteUrl = UrlResolver.resolve(baseUrl, href);
                    System.out.println("Fetching CSS: " + absoluteUrl);
                    String cssText = dataFetcher.loadResource(absoluteUrl);
                    if (!cssText.isEmpty()) {
                        CssStorage.addGlobalStyles(cssText);
                    }
                } catch (Exception e) {
                    System.err.println("Failed to load CSS: " + href);
                }
            }
        }
        else if (tagName.equals("img")) {
            String src = tagEntity.getAttribute("src");
            if (!src.isEmpty()) {
                try {
                    String absoluteUrl = UrlResolver.resolve(baseUrl, src);
                    tagEntity.attributes().put("src", absoluteUrl);
                } catch (Exception ignored) {}
            }
        }
        else if (tagName.equals("script")) {
            String src = tagEntity.getAttribute("src");
            System.out.println("Fetching script: " + src);

            if (src != null && !src.isEmpty()) {
                try {
                    String absoluteUrl = UrlResolver.resolve(baseUrl, src);
                    System.out.println("Fetching JS: " + absoluteUrl);
                    String jsCode = dataFetcher.loadResource(absoluteUrl);
                    if (!jsCode.isEmpty()) {
                        loadedScripts.put(absoluteUrl, jsCode);
                    }
                } catch (Exception e) {
                    System.err.println("Failed to load JS: " + src);
                }
            } else {
                String inlineCode = tagEntity.text();

                System.out.println("--- Processing Inline Script ---");
                System.out.println("Raw text inside script tag: '" + inlineCode + "'");

                if (!inlineCode.isEmpty()) {
                    String type = tagEntity.getAttribute("type");
                    if (type == null || type.isEmpty() || type.equals("text/javascript") || type.equals("application/javascript")) {
                        loadedScripts.put("Inline Script #" + (++inlineScriptCounter), inlineCode);
                    } else {
                        System.out.println("Skipping script with type: " + type);
                    }
                }
            }
        }
    }

    @Override
    public void tail(PageItem pageItem) {}
}
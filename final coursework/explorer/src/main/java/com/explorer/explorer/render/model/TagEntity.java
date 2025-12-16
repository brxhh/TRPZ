package com.explorer.explorer.render.model;

import java.util.ArrayList;
import java.util.List;

public class TagEntity extends PageItem {
    private final String tagName;
    private final Attributes attributes = new Attributes();
    private final List<PageItem> children = new ArrayList<>();

    public TagEntity(String tagName) {
        this.tagName = tagName.toLowerCase();
    }

    public String tagName() {
        return tagName;
    }

    public String attr(String key) {
        return attributes.get(key).toString();
    }

    public String getAttribute(String key) {
        return attributes.getAttr(key);
    }

    public Attributes attributes() {
        return attributes;
    }

    public List<PageItem> children() {
        return children;
    }

    public void addChild(PageItem child) {
        this.children.add(child);
    }

    public String text() {
        StringBuilder sb = new StringBuilder();
        for (PageItem child : children) {
            if (child instanceof TextPageItem) {
                sb.append(((TextPageItem) child).getText());
            } else if (child instanceof TagEntity) {
                sb.append(((TagEntity) child).text());
            }
        }
        return sb.toString();
    }
}
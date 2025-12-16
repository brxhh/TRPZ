package com.explorer.explorer.render.model;

import java.util.Collections;
import java.util.List;


public class PageStructure {
    private final TagEntity root;
    private String title = "";

    public PageStructure(TagEntity root) {
        this.root = root;
    }

    public List<PageItem> children() {
        return root != null ? root.children() : Collections.emptyList();
    }

    public String title() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TagEntity getRoot() {
        return root;
    }
}
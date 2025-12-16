package com.explorer.explorer.render.model;

public class TextPageItem extends PageItem {
    private String text;

    public TextPageItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TextNode{'" + text + "'}";
    }
}
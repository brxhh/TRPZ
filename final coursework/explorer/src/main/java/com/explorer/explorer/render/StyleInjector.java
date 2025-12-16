package com.explorer.explorer.render;

import com.explorer.explorer.render.model.TagEntity;
import com.explorer.explorer.render.model.PageItem;

public class StyleInjector implements ItemVisitor {

    public static final String STYLE_CONTEXT_KEY = "style_context";

    @Override
    public void head(PageItem pageItem) {
        if (!(pageItem instanceof TagEntity)) {
            return;
        }

        TagEntity tagEntity = (TagEntity) pageItem;
        StyleContext context = new StyleContext();
        String tagName = tagEntity.tagName().toLowerCase();

        String tagStyle = CssStorage.getStyleForTag(tagName);
        parseAndSet(context, tagStyle);

        String classAttr = tagEntity.attr("class");
        if (!classAttr.isEmpty()) {
            String[] classes = classAttr.split("\\s+");
            for (String className : classes) {
                String classStyle = CssStorage.getStyleForClass(className);
                if (!classStyle.isEmpty()) {
                    parseAndSet(context, classStyle);
                }
            }
        }

        String inlineStyle = tagEntity.attr("style");
        if (!inlineStyle.isEmpty()) {
            parseAndSet(context, inlineStyle);
        }

        tagEntity.attributes().userData(STYLE_CONTEXT_KEY, context);
    }

    @Override
    public void tail(PageItem pageItem) {
    }

    private void parseAndSet(StyleContext context, String cssString) {
        if (cssString == null || cssString.isEmpty()) {
            return;
        }

        String[] rules = cssString.split(";");
        for (String rule : rules) {
            String[] parts = rule.split(":", 2);
            if (parts.length == 2) {
                context.setProperty(parts[0].trim(), parts[1].trim());
            }
        }
    }
}
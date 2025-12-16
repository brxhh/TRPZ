package com.explorer.explorer.render;

import com.explorer.explorer.render.model.PageStructure;
import com.explorer.explorer.render.model.TagEntity;
import com.explorer.explorer.render.model.PageItem;
import com.explorer.explorer.render.model.TextPageItem;

import java.util.Map;
import java.util.Set;

public class LayoutComposer {

    private static final Set<String> IGNORED_TAGS = Set.of(
            "head", "script", "style", "meta", "link", "title", "noscript", "iframe", "svg", "path"
    );

    public RenderNode build(PageStructure doc) {
        return convert(doc.getRoot());
    }

    private RenderNode convert(PageItem pageItem) {
        if (pageItem instanceof TextPageItem) {
            RenderNode rn = new RenderNode();
            rn.type = RenderNode.Type.TEXT;
            rn.text = ((TextPageItem) pageItem).getText();
            return rn;
        }

        if (!(pageItem instanceof TagEntity)) return null;
        TagEntity el = (TagEntity) pageItem;
        String tagName = el.tagName();

        if (IGNORED_TAGS.contains(tagName)) return null;

        RenderNode rn = new RenderNode();
        rn.style = extractStyles(el);

        switch (tagName) {
            case "html", "body", "div", "p", "blockquote", "pre", "center", "hr", "form", "h1", "h2", "h3", "h4", "h5",
                 "h6", "table", "thead", "tbody", "tfoot", "tr", "td", "th", "main", "header", "footer", "nav",
                 "section", "article", "aside", "figure", "figcaption", "ul", "ol", "li", "dl", "dt", "dd" -> rn.type = RenderNode.Type.BLOCK;

            case "img" -> {
                rn.type = RenderNode.Type.IMAGE;
                rn.src = el.attr("src");
            }

            default -> rn.type = RenderNode.Type.INLINE;
        }

        if (tagName.equals("a")) {
            rn.style.put("href", el.attr("href"));
            if (!rn.style.containsKey("-fx-text-fill") && !rn.style.containsKey("color")) {
                rn.style.put("color", "blue");
                rn.style.put("text-decoration", "underline");
            }
        }

        for (PageItem child : el.children()) {
            RenderNode childRn = convert(child);
            if (childRn != null) {
                rn.children.add(childRn);
            }
        }

        return rn;
    }

    private Map<String, String> extractStyles(TagEntity el) {
        Object contextObj = el.attributes().userData(StyleInjector.STYLE_CONTEXT_KEY);
        if (contextObj instanceof StyleContext) {
            return ((StyleContext) contextObj).getStyleProperties();
        }
        return new java.util.HashMap<>();
    }
}
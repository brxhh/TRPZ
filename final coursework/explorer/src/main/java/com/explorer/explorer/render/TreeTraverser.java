package com.explorer.explorer.render;

import com.explorer.explorer.render.model.TagEntity;
import com.explorer.explorer.render.model.PageItem;

public class TreeTraverser {
    public static void traverse(PageItem pageItem, ItemVisitor visitor) {
        if (pageItem == null) return;

        visitor.head(pageItem);

        if (pageItem instanceof TagEntity tagEntity) {
            for (PageItem child : tagEntity.children()) {
                traverse(child, visitor);
            }
        }

        visitor.tail(pageItem);
    }
}
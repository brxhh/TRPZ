package com.explorer.explorer.render;

import com.explorer.explorer.render.model.PageItem;

public interface ItemVisitor {
    void head(PageItem element);
    void tail(PageItem element);
}
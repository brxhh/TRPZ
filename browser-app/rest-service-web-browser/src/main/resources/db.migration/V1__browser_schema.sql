CREATE TABLE HistoryItem (
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    visit_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE WebPages (
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    html_content TEXT,
    parsed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE CssResources (
    id SERIAL PRIMARY KEY,
    web_page_id INT NOT NULL REFERENCES WebPages(id) ON DELETE CASCADE,
    url TEXT NOT NULL,
    css_content TEXT
);


CREATE TABLE JsResources (
    id SERIAL PRIMARY KEY,
    web_page_id INT NOT NULL REFERENCES WebPages(id) ON DELETE CASCADE,
    url TEXT NOT NULL,
    js_content TEXT
);

CREATE TABLE ImageResources (
                                id SERIAL PRIMARY KEY,
                                web_page_id INT NOT NULL REFERENCES WebPages(id) ON DELETE CASCADE,
                                url TEXT NOT NULL,
                                image_data BYTEA
);


CREATE INDEX idx_cssresources_web_page_id ON CssResources(web_page_id);
CREATE INDEX idx_jsresources_web_page_id ON JsResources(web_page_id);
CREATE INDEX idx_imageresources_web_page_id ON ImageResources(web_page_id);
package entities;

public class Bookmark {
    private String url;
    private String title;

    public Bookmark(String url, String title) {
        this.url = url;
        this.title = title;
    }
    @Override
    public String toString() { return "Закладка: " + title + " (" + url + ")"; }
}
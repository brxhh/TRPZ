package ui;

public class Main {
    static void main() {
        BrowserUI browser = new BrowserUI();
        browser.navigateTo("google.com");
        browser.addToBookmarks();

        browser.navigateTo("kpi.ua");

        browser.showHistory();
    }
}
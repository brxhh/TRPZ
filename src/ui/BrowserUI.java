package ui;

import database.DatabaseContext;
import entities.Bookmark;
import entities.HistoryItem;
import modules.INetworkModule;
import modules.CachingProxy;
import modules.RenderingEngine;
import repositories.BookmarkRepository;
import repositories.HistoryRepository;

public class BrowserUI {
    private BookmarkRepository bookmarkRepo;
    private HistoryRepository historyRepo;
    private INetworkModule network;
    private RenderingEngine renderer;
    private String currentUrl;

    public BrowserUI() {
        DatabaseContext db = new DatabaseContext();
        this.bookmarkRepo = new BookmarkRepository(db);
        this.historyRepo = new HistoryRepository(db);
        this.network = new CachingProxy();
        this.renderer = new RenderingEngine();
        System.out.println("Браузер запущено.");
    }

    public void navigateTo(String url) {
        System.out.println("\n--- Сценарій: Відкриття " + url + " ---");
        this.currentUrl = url;
        String html = network.sendRequest(url);
        renderer.render(html);
        historyRepo.add(new HistoryItem(url));
    }

    public void addToBookmarks() {
        System.out.println("\n--- Сценарій: Додавання закладки ---");
        if (currentUrl != null) {
            Bookmark b = new Bookmark(currentUrl, "Заголовок сторінки");
            bookmarkRepo.add(b);
            System.out.println("Закладку успішно додано!");
        } else {
            System.out.println("Немає відкритої сторінки.");
        }
    }

    public void showHistory() {
        System.out.println("\n--- Історія переглядів ---");
        for (HistoryItem item : historyRepo.getAll()) {
            System.out.println(item);
        }
    }
}
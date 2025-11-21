package database;
import entities.Bookmark;
import entities.HistoryItem;
import java.util.ArrayList;
import java.util.List;

public class DatabaseContext {
    public List<Bookmark> bookmarksTable = new ArrayList<>();
    public List<HistoryItem> historyTable = new ArrayList<>();

    public void saveChanges() {
        // Імітація запису на диск
        System.out.println("[Database] Зміни збережено у локальний файл (SQLite).");
    }
}
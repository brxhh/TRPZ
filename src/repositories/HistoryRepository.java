package repositories;
import database.DatabaseContext;
import entities.HistoryItem;
import java.util.List;

public class HistoryRepository implements IRepository<HistoryItem> {
    private DatabaseContext db;

    public HistoryRepository(DatabaseContext db) { this.db = db; }

    @Override
    public void add(HistoryItem entity) {
        db.historyTable.add(entity);
        db.saveChanges();
    }

    @Override
    public List<HistoryItem> getAll() { return db.historyTable; }
}
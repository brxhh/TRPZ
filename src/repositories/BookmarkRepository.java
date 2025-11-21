package repositories;
import database.DatabaseContext;
import entities.Bookmark;
import java.util.List;

public class BookmarkRepository implements IRepository<Bookmark> {
    private DatabaseContext db;

    public BookmarkRepository(DatabaseContext db) { this.db = db; }

    @Override
    public void add(Bookmark entity) {
        db.bookmarksTable.add(entity);
        db.saveChanges();
    }

    @Override
    public List<Bookmark> getAll() { return db.bookmarksTable; }
}
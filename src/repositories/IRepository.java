package repositories;
import java.util.List;

public interface IRepository<T> {
    void add(T entity);
    List<T> getAll();
}
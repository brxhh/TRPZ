package entities;
import java.util.Date;

public class HistoryItem {
    private String url;
    private Date visitTime;

    public HistoryItem(String url) {
        this.url = url;
        this.visitTime = new Date();
    }
    @Override
    public String toString() { return "[" + visitTime + "] Відвідано: " + url; }
}
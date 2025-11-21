package modules;

public class NetworkModule {
    public String sendGetRequest(String url) {
        System.out.println("[Network] Надсилання GET-запиту на: " + url);

        // Імітація затримки
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        System.out.println("[Server] Відповідь: 200 OK");
        return "<html><body>Контент сторінки " + url + "</body></html>";
    }
}
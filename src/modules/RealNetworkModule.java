package modules;

public class RealNetworkModule implements INetworkModule {
    @Override
    public String sendRequest(String url) {
        System.out.println("[Network] Підключення до сервера... Завантаження " + url);
        try { Thread.sleep(1500); } catch (InterruptedException e) {}

        return "<html>Вміст сторінки " + url + "</html>";
    }
}
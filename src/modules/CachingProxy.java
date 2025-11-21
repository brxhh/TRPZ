package modules;
import java.util.HashMap;
import java.util.Map;

public class CachingProxy implements INetworkModule {
    private RealNetworkModule realModule;
    private Map<String, String> cache = new HashMap<>();

    @Override
    public String sendRequest(String url) {
        if (cache.containsKey(url)) {
            System.out.println("[Proxy] Повернення з кешу: " + url);
            return cache.get(url);
        }

        if (realModule == null) {
            realModule = new RealNetworkModule();
        }

        String data = realModule.sendRequest(url);
        cache.put(url, data);
        return data;
    }
}
package modules;

public class RenderingEngine {
    public void render(String html) {
        System.out.println("[Renderer] Побудова DOM-дерева...");
        System.out.println("[UI] ВІДОБРАЖЕННЯ: " + html);
    }
}
package com.edu.web.browserapp.service;

import com.edu.web.browserapp.service.impl.ClientHistoryService;
import javafx.application.Platform;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

public class P2PSearchService {
    private static final String GROUP_ADDRESS = "230.0.0.2";
    private static final int PORT = 7777;

    private MulticastSocket socket;
    private InetAddress group;
    private boolean running = true;
    private final Consumer<String> onResultFound;

    public P2PSearchService(Consumer<String> onResultFound) {
        this.onResultFound = onResultFound;
        initNetwork();
    }

    private void initNetwork() {
        try {
            socket = new MulticastSocket(PORT);
            group = InetAddress.getByName(GROUP_ADDRESS);
            socket.joinGroup(group);

            Thread listener = new Thread(this::listenLoop);
            listener.setDaemon(true);
            listener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastSearchRequest(String query) {
        sendMessage("SEARCH_REQ:" + query);
    }

    private void sendMessage(String msg) {
        try {
            byte[] buf = msg.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, PORT);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenLoop() {
        byte[] buffer = new byte[2048];
        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
                handleMessage(message);
            } catch (IOException e) {
                if(running) e.printStackTrace();
            }
        }
    }

    private void handleMessage(String message) {
        if (message.startsWith("SEARCH_REQ:")) {
            String query = message.substring(11);

            List<String> results = ClientHistoryService.getInstance().searchLocalHistory(query);

            for (String res : results) {
                sendMessage("SEARCH_RES:" + res);
            }

        } else if (message.startsWith("SEARCH_RES:")) {
            String foundUrl = message.substring(11);
            Platform.runLater(() -> onResultFound.accept(foundUrl));
        }
    }

    public void stop() {
        running = false;
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}
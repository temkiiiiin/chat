package com.temkiiiiin.chat;


import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234,0,InetAddress.getByName("localhost"));
            System.out.println("server start");

            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    SClient sClient = new SClient(socket, socket.getInputStream(), socket.getOutputStream());
                    new Server(sClient).start();
                    addNewClient(sClient);

                    System.out.println("new client connect");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final int LAST_MESSAGES_COUNT = 10;

    private static List<SClient> sClients = new ArrayList<>();
    private static ArrayDeque<MessageResult> lastMessages = new ArrayDeque<>(LAST_MESSAGES_COUNT);

    private SClient sClient;

    public Server(SClient sClient) {
        this.sClient = sClient;
    }

    @Override
    public void run() {
        boolean connected = true;

        while (connected) {
            MessageResult messageResult = sClient.receive();

            if (messageResult.getStatus() == MessageStatus.OK) {
                System.out.println(messageResult.getText());
                sendToAllUsers(messageResult.getText());
                addNewMessage(messageResult);
            } else if (messageResult.getStatus() == MessageStatus.DISCONNECT) {
                connected = false;
            }
        }

        System.out.println("client disconnect");
    }

    private synchronized static void addNewClient(SClient sClient) {
        sClients.add(sClient);
    }

    private synchronized static void addNewMessage(MessageResult messageResult) {
        if (lastMessages.size() == LAST_MESSAGES_COUNT) {
            lastMessages.poll();
        }
        lastMessages.add(messageResult);
    }

    private synchronized void sendToAllUsers(String message) {
        for (SClient client: sClients) {
            if (this.sClient.equals(client)) {
                continue;
            }

            try {
                client.send(message);
            } catch (Exception e) {
                sClients.remove(client);
                client.close();
            }
        }
    }
}

package com.temkiiiiin.chat;


import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
                    synchronized (sClients) {
                        sClients.add(sClient);
                    }

                    System.out.println("new client connect");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<SClient> sClients = new ArrayList<>();
    private static List<MessageResult> lastMessages = new ArrayList<>();

    private SClient sClient;

    public Server(SClient sClient) {
        this.sClient = sClient;
    }

    @Override
    public void run() {
        boolean connected = true;

        while (connected) {
            MessageResult messageResult = sClient.read();

            if (messageResult.getStatus() == MessageStatus.OK) {
                System.out.println(messageResult.getText());
                sendToAllUsers(messageResult.getText());
            } else if (messageResult.getStatus() == MessageStatus.DISCONNECT) {
                connected = false;
            }
        }

        System.out.println("client disconnect");
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

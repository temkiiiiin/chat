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
                    sClients.add(sClient);

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

                for (SClient client: sClients) {
                    if (!this.sClient.equals(client)) {
                        try {
                            client.send(messageResult.getText());
                        } catch (Exception e) {
                            sClients.remove(client);
                            client.close();
                        }
                    }
                }
            } else if (messageResult.getStatus() == MessageStatus.DISCONNECT) {
                connected = false;
            }
        }

        System.out.println("client disconnect");
    }
}

package com.temkiiiiin.chat;


import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

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

                    System.out.println("new client connect");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            } else if (messageResult.getStatus() == MessageStatus.DISCONNECT) {
                connected = false;
            }
        }

        System.out.println("client disconnect");
    }
}

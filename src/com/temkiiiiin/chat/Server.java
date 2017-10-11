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
                new Server(serverSocket.accept()).start();
                System.out.println("new client connect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Socket clientSocket;
    private InputStream inputStream;

    public Server(Socket clientSoscket) {
        this.clientSocket = clientSoscket;
        try {
            inputStream = clientSoscket.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (inputStream == null) {
            return;
        }

        boolean clientConnected = true;

        while (clientConnected) {
            try {
                byte[] messageBytes = new byte[1024];
                int messageLen = inputStream.read(messageBytes);

                if (messageLen == -1) {
                    clientConnected = false;
                } else {
                    System.out.println(new String(messageBytes));
                }
            } catch (SocketException e) {
                clientConnected = false;
            } catch (Exception e) {
                e.printStackTrace();
                clientConnected = false;
            }
        }

        System.out.println("client disconnect");
    }
}

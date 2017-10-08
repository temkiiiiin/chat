package com.temkiiiiin.chat;


import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234,0,InetAddress.getByName("localhost"));

            while (true) {
                new Server(serverSocket.accept()).start();
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

        while (true) {
            try {
                byte[] messageBytes = new byte[1024];
                int messageLen = inputStream.read(messageBytes);

                if (messageLen == 0) {
                    return;
                }

                System.out.println(new String(messageBytes));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

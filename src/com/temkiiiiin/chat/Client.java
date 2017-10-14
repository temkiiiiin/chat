package com.temkiiiiin.chat;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    private static final String HOST_NAME = "localhost";
    private static final int PORT = 1234;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Enter your name");
            name = scanner.nextLine();
        } while (name.isEmpty());

        try {
            Socket socket = new Socket(InetAddress.getByName(HOST_NAME), PORT);
            OutputStream outputStream = socket.getOutputStream();
            new Client(socket.getInputStream());

            String message;
            do {
                message = scanner.nextLine();
                if (message.isEmpty()) {
                    System.out.println("Message is empty");
                } else {
                    outputStream.write((name + ": " + message.trim()).getBytes());
                }
            } while (!"stop".equals(message.trim()));

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String name;
    private InputStream inputStream;

    public Client(InputStream inputStream) {
        this.inputStream = inputStream;
        start();
    }

    @Override
    public void run() {
        boolean connect = true;
        while (connect) {
            byte[] messageBytes = new byte[1024];
            try {
                int messageLen = inputStream.read(messageBytes);

                if (messageLen == -1) {
                    connect = false;
                } else {
                    System.out.println(new String(messageBytes, 0, messageLen));
                }
            } catch (Exception e) {
                connect = false;
                e.printStackTrace();
            }
        }
    }
}

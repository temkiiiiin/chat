package com.temkiiiiin.chat.client;


import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public final class Client {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Enter your name");
            name = scanner.nextLine();
        } while (name.isEmpty());

        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static final String HOST_NAME = "localhost";
    private static final int PORT = 1234;

    private static String name;

    private static Socket socket;

    public static void setName(String name) {
        Client.name = name;
    }

    public static String getName() {
        return Client.name;
    }

    public static void start() throws IOException {
        socket = new Socket(InetAddress.getByName(HOST_NAME), PORT);

        Thread sender = new Thread(new Sender(socket.getOutputStream()));
        Thread listener = new Thread(new Listener(socket.getInputStream()));

        sender.start();
        listener.start();

        do {
            if (listener.isInterrupted()) {
                System.out.println("serever error");
            }
        } while (!sender.isInterrupted() && !listener.isInterrupted());

        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

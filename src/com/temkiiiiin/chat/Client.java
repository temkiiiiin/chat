package com.temkiiiiin.chat;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(InetAddress.getByName("localhost"), 1234);
            OutputStream outputStream = socket.getOutputStream();
            new Client(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);

            String message = "";
            do {
                message = scanner.nextLine();
                outputStream.write(message.trim().getBytes());
            } while (!"stop".equals(message.trim()));

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                    System.out.println(new String(messageBytes));
                }
            } catch (Exception e) {
                connect = false;
            }
        }
    }
}

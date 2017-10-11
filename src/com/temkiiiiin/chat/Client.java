package com.temkiiiiin.chat;


import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(InetAddress.getByName("localhost"), 1234);
            OutputStream outputStream = socket.getOutputStream();

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
}

package com.temkiiiiin.chat;


import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(InetAddress.getByName("localhost"), 1234);
            OutputStream outputStream = socket.getOutputStream();

            String message = "";
            do {

            } while (message.trim() != "stop");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.temkiiiiin.chat;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class SClient {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public SClient(Socket socket, InputStream inputStream, OutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public MessageResult read() {
        byte[] messageBytes = new byte[1024];
        try {
            int messageLen = inputStream.read(messageBytes);

            if (messageLen == -1) {
                return new MessageResult(MessageStatus.DISCONNECT, "");
            } else {
                return new MessageResult(MessageStatus.OK, new String(messageBytes));
            }
        } catch (Exception e) {
            return new MessageResult(MessageStatus.DISCONNECT, "");
        }
    }

    public void send(String message) {
        if (message.isEmpty()) {
            return;
        }

        try {
            outputStream.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

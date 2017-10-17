package com.temkiiiiin.chat;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class SClient {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public SClient(Socket socket, InputStream inputStream, OutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public MessageResult receive() {
        byte[] messageBytes = new byte[1024];
        try {
            int messageLen = inputStream.read(messageBytes);

            if (messageLen == -1) {
                return new MessageResult(MessageStatus.DISCONNECT);
            } else {
                return new MessageResult(MessageStatus.OK, MessageResult.deserialise(Arrays.copyOfRange(messageBytes, 0, messageLen)));
            }
        } catch (Exception e) {
            return new MessageResult(MessageStatus.DISCONNECT);
        }
    }

    public MessageResult send(String message) throws Exception {
        if (message.isEmpty()) {
            throw new Exception("Message is empty");
        }

        try {
            outputStream.write(message.getBytes());
            return new MessageResult(MessageStatus.OK);
        } catch (IOException e) {
            return new MessageResult(MessageStatus.DISCONNECT);
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

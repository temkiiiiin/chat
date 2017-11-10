package com.temkiiiiin.chat.server;


import com.temkiiiiin.chat.Message;
import com.temkiiiiin.chat.MessageProcessor;
import com.temkiiiiin.chat.MessageResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
        try {
            Message message = MessageProcessor.receive(inputStream);
            return new MessageResult(MessageResult.MessageStatus.OK, message);
        } catch (IOException | ClassNotFoundException e) {
            return new MessageResult(MessageResult.MessageStatus.DISCONNECT);
        }
    }

    public MessageResult send(Message message) {
        try {
            MessageProcessor.send(outputStream, message);
            return new MessageResult(MessageResult.MessageStatus.OK);
        } catch (IOException e) {
            return new MessageResult(MessageResult.MessageStatus.DISCONNECT);
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

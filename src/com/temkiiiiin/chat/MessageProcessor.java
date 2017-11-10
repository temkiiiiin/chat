package com.temkiiiiin.chat;


import java.io.*;
import java.net.SocketException;

public final class MessageProcessor {

    public static void send(OutputStream outputStream, Message message) throws IOException {
        new ObjectOutputStream(outputStream).writeObject(message);
    }

    public static Message receive(InputStream inputStream) throws IOException, ClassNotFoundException, SocketException {
        return (Message)new ObjectInputStream(inputStream).readObject();
    }

}

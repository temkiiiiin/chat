package com.temkiiiiin.chat;


import java.io.*;

public final class MessageProcessor {

    public static void send(OutputStream outputStream, Message message) throws IOException {
        new ObjectOutputStream(outputStream).writeObject(message);
    }

    public static Message receive(InputStream inputStream) throws IOException, ClassNotFoundException {
        return (Message)new ObjectInputStream(inputStream).readObject();
    }

}

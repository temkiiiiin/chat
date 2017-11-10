package com.temkiiiiin.chat.client;


import com.temkiiiiin.chat.Message;
import com.temkiiiiin.chat.MessageProcessor;

import java.io.IOException;
import java.io.InputStream;

public class Listener implements Runnable {

    private final InputStream inputStream;

    public Listener(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        do {
            Message message;
            try {
                message = MessageProcessor.receive(inputStream);
                System.out.println(message);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                currentThread.interrupt();
            }
        } while (!currentThread.isInterrupted());
    }

}

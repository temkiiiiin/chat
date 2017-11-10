package com.temkiiiiin.chat.client;


import com.temkiiiiin.chat.Message;
import com.temkiiiiin.chat.MessageProcessor;
import com.temkiiiiin.chat.MessageResult;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Scanner;

public class Sender implements Runnable {

    private final OutputStream outputStream;

    public Sender(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Thread currentThread = Thread.currentThread();
        String message;
        do {
            message = scanner.nextLine();
            if (message.isEmpty()) {
                System.out.println("Message is empty");
            } else if ("stop".equals(message.trim())) {
                currentThread.interrupt();
            } else {
                try {
                    MessageProcessor.send(outputStream, new Message(new Date(), Client.getName(), message.trim()));
                } catch (IOException e) {
                    e.printStackTrace();
                    currentThread.interrupt();
                }
            }
        } while (!currentThread.isInterrupted());
    }

}

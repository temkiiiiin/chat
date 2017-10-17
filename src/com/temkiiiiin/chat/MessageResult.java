package com.temkiiiiin.chat;

import java.io.*;
import java.util.Date;

enum MessageStatus {
    OK,
    DISCONNECT
}

public class MessageResult extends Message implements Serializable {
    private MessageStatus status;

    public MessageStatus getStatus() {
        return status;
    }

    public MessageResult(MessageStatus status) {
        super();
        this.status = status;
    }

    public MessageResult(MessageStatus status, Message message) {
        super(message.getDate(), message.getName(), message.getText());
        this.status = status;
    }

    public MessageResult(MessageStatus status, Date date, String name, String text) {
        super(date, name, text);
        this.status = status;
    }

    public static byte[] serialize(Message message) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(message);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[] {};
    }

    public static Message deserialise(byte[] data) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (Message)ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.temkiiiiin.chat;

import java.util.Date;

public class MessageResult {

    public enum  MessageStatus {
        OK,
        DISCONNECT
    }

    private MessageStatus status;

    private Message message;

    public MessageStatus getStatus() {
        return status;
    }

    public Message getMessage() {
        return message;
    }

    public MessageResult(MessageStatus status) {
        this.status = status;
        this.message = null;
    }

    public MessageResult(MessageStatus status, Message message) {
        this.status = status;
        this.message = message;
    }

}

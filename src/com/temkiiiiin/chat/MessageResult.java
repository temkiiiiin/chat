package com.temkiiiiin.chat;

enum MessageStatus{
    OK,
    DISCONNECT
}

public class MessageResult {
    private MessageStatus status;
    private String text;

    public MessageStatus getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }

    public MessageResult(MessageStatus status, String text) {
        this.status = status;
        this.text = text;
    }
}

package com.temkiiiiin.chat;


import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private Date date;
    private String name;
    private String text;

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public Message() {
        date = new Date();
        name = "";
        text = "";
    }

    public Message(Date date, String name, String text) {
        this.date = date;
        this.name = name;
        this.text = text;
    }
}

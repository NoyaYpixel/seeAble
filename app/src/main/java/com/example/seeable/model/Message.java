package com.example.seeable.model;

import android.widget.Spinner;

import java.util.Date;

public class Message {

    String id;
    String content, whoSent, whoReceive;
    Date date;

    public Message() {
    }

    public Message(String id, String content, String whoSent, String whoReceive, Date date) {
        this.id = id;
        this.content = content;
        this.whoSent = whoSent;
        this.whoReceive = whoReceive;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWhoSent() {
        return whoSent;
    }

    public void setWhoSent(String whoSent) {
        this.whoSent = whoSent;
    }

    public String getWhoReceive() {
        return whoReceive;
    }

    public void setWhoReceive(String whoReceive) {
        this.whoReceive = whoReceive;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", whoSent='" + whoSent + '\'' +
                ", whoReceive='" + whoReceive + '\'' +
                ", date=" + date +
                '}';
    }
}

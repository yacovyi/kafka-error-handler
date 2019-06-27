package com.ws.ng.kafkaerrorhandler.model;

import com.ws.ng.kafkaerrorhandler.controller.MessageController;

import java.util.Date;

public class Message {
    private String payload;

    private Date date;

    public Message() {
    }

    public Message(String payload) {
        this.payload = payload;
    }

    public Message(String payload, Date date) {
        this.payload = payload;
        this.date = date;
    }

    public Message(Message message) {
        this.payload = message.payload;
        this.date = new Date();
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("payload='").append(payload).append('\'');
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }
}


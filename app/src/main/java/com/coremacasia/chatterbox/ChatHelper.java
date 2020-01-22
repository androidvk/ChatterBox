package com.coremacasia.chatterbox;

import java.util.Date;

public class ChatHelper {
    Date messageTime;

    public Date getMessageTime() {
        return messageTime;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    String senderId,textMessage;
    Date timeStamp;

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getTextMessage() {
        return textMessage;
    }
}

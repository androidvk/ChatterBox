package com.coremacasia.chatterbox;

import java.util.Date;

public class ChatHelper {
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

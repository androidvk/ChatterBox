package com.coremacasia.chatterbox;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyMessageHolder extends RecyclerView.ViewHolder {
    private TextView textView, tTime;

    public MyMessageHolder(View view) {
        super(view);
        textView = view.findViewById(R.id.listText);
        tTime = view.findViewById(R.id.time);

    }

    public void start(int position, RecyclerView.ViewHolder holder, List<ChatHelper> dataList) {
        ChatHelper chatHelper = dataList.get(position);
        if(chatHelper.getMessageTime()!=null){
            String timeAgo = new RelativeTime().getTimeAgo(chatHelper.getMessageTime().getTime());
            tTime.setText(timeAgo);
        }

        textView.setText(chatHelper.getTextMessage());
    }
}

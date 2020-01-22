package com.coremacasia.chatterbox;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HisMessageHolder extends RecyclerView.ViewHolder {

    private TextView textView2, tTime;

    public HisMessageHolder(View itemView) {
        super(itemView);
        textView2 = itemView.findViewById(R.id.listText2);
        tTime = itemView.findViewById(R.id.time);
    }

    public void start(int position,
                      RecyclerView.ViewHolder holder, List<ChatHelper> dataList) {
        ChatHelper chatHelper = dataList.get(position);
        if (chatHelper.getMessageTime() != null) {
            String timeAgo = new RelativeTime().getTimeAgo(chatHelper.getMessageTime().getTime());
            tTime.setText(timeAgo);
        }
        textView2.setText(dataList.get(position).getTextMessage());
    }
}

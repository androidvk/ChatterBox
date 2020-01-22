package com.coremacasia.chatterbox.ui.home;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coremacasia.chatterbox.ChatMain;
import com.coremacasia.chatterbox.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class UserHolder extends RecyclerView.ViewHolder {
    private int position;
    private RecyclerView.ViewHolder holder;
    private List<UserHelper> dataList;
    private TextView tUserName,tUserEmail;
    private CircleImageView iUserImage;
    private View click;
    private FirebaseUser firebaseUser;
    public UserHolder(View itemView) {
        super(itemView);
        iUserImage=itemView.findViewById(R.id.userImg);
        tUserName=itemView.findViewById(R.id.userName);
        tUserEmail=itemView.findViewById(R.id.userEmail);
        click=itemView.findViewById(R.id.listClick);
        firebaseUser =FirebaseAuth.getInstance().getCurrentUser();
    }

    public void start(int position, RecyclerView.ViewHolder holder,
                      List<UserHelper> dataList) {

        final UserHelper userHelper=dataList.get(position);
        this.position = position;
        this.holder = holder;
        this.dataList = dataList;

        tUserName.setText(dataList.get(position).getName());
        Glide.with(itemView.getContext())
                .load(userHelper.getPhotolink())
                .into(iUserImage);
        tUserEmail.setText(userHelper.getEmail());

        if(firebaseUser.getUid().equals(userHelper.getUserId())){
            click.setVisibility(View.GONE);
        }else click.setVisibility(View.VISIBLE);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            itemView.getContext().startActivity(new Intent(itemView.getContext()
            , ChatMain.class).putExtra("uid",userHelper.getUserId()));

            }
        });

    }
}

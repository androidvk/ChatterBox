package com.coremacasia.chatterbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.coremacasia.chatterbox.ui.home.UserHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChatMain extends AppCompatActivity {
private String userId;
private EditText eMsg;
private Button bSend;
private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userId=getIntent().getStringExtra("uid");
        getUserData();

        eMsg=findViewById(R.id.eMsg);
        bSend=findViewById(R.id.bSend);
        recyclerView=findViewById(R.id.rv_chatMain);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        ChatMainAdapter myAdapter=new ChatMainAdapter(getApplicationContext(),userId);
        recyclerView.setAdapter(myAdapter);

    }
    public void sendMsgBtn(View view){
        String msg=eMsg.getText().toString().trim();

        sendMessage(msg);
    }

    private void sendMessage(String msg) {
        final Map map=new HashMap();

        map.put("timeStamp", FieldValue.serverTimestamp());
        map.put("textMessage",msg);
        map.put("senderId",ChatList.myUserId);
        map.put("messageTime",new Date());

        DocumentReference myRef=FirebaseFirestore.getInstance().collection("users")
                .document(ChatList.myUserId).collection("chats").document(userId)
                .collection("conversation").document();

        final DocumentReference hisRef=FirebaseFirestore.getInstance().collection("users")
                .document(userId).collection("chats").document(ChatList.myUserId)
                .collection("conversation").document();

        myRef.set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                hisRef.set(map,SetOptions.merge());
                eMsg.setText("");
            }
        });

    }


    // get data for a single document
    private void getUserData() {
        DocumentReference userRef= FirebaseFirestore.getInstance().collection("users")
                .document(userId);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserHelper userHelper=documentSnapshot.toObject(UserHelper.class);
                getSupportActionBar().setTitle(userHelper.getName());
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();

    }
}

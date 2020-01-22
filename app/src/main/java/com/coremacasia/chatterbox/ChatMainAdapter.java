package com.coremacasia.chatterbox;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.coremacasia.chatterbox.ui.home.UserHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

class ChatMainAdapter extends RecyclerView.Adapter {

    private List<ChatHelper> dataList = new ArrayList<>();
    private static final String TAG = "ChatMainAdapter";
    private List<String> contentIds = new ArrayList<>();
    private ChatHelper helperChanged;

    public ChatMainAdapter(Context applicationContext, String userId, final RecyclerView recyclerView) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference myRef = db.collection("users")
                .document(ChatList.myUserId).collection("chats").document(userId)
                .collection("conversation");


        Query query = myRef.orderBy("timeStamp");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                    switch (documentChange.getType()) {
                        case ADDED:
                            ChatHelper helper = documentChange.getDocument().toObject(ChatHelper.class);
                            dataList.add(helper);
                            notifyItemInserted(dataList.size() - 1);
                            contentIds.add(documentChange.getDocument().getId());
                            recyclerView.smoothScrollToPosition(dataList.size());
                        break;
                        case MODIFIED:
                            helperChanged=documentChange.getDocument().toObject(ChatHelper.class);
                            String contentKey = documentChange.getDocument().getId();
                            int contentIndex = contentIds.indexOf(contentKey);
                            if (contentIndex > -1) {
                                dataList.set(contentIndex, helperChanged);

                                notifyItemChanged(contentIndex);
                                //notifyDataSetChanged();
                            } else {
                                Log.w(TAG, "onEvent: Modified " + contentKey);
                            }
                            break;
                    }
                }
            }
        });

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());// initialization of inflator
        switch (viewType) {
            case 1:
                View view1 = inflater.inflate(R.layout.list_my_message, parent, false);// assign view with help of inflator
                holder = new MyMessageHolder(view1);// assign view / xml to holder class
                return holder;
            case 2:
                View view2 = inflater.inflate(R.layout.list_his_message, parent, false);// assign view with help of inflator
                holder = new HisMessageHolder(view2);// assign view / xml to holder class
                return holder;

            default:
                return null;

        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 1:
                MyMessageHolder myHolder = (MyMessageHolder) holder;
                myHolder.start(position, myHolder, dataList);
                break;
            case 2:
                HisMessageHolder hisMessageHolder = (HisMessageHolder) holder;
                hisMessageHolder.start(position, hisMessageHolder, dataList);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + holder.getItemViewType());
        }


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        String senderId = dataList.get(position).getSenderId();
        if (senderId.equals(ChatList.myUserId)) {
            Log.d(TAG, "getItemViewType: MyMSG");
            return 1;
        } else {
            Log.d(TAG, "getItemViewType: HisMSG");
            return 2;
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

}

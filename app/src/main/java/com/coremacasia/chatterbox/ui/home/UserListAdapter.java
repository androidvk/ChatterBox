package com.coremacasia.chatterbox.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.coremacasia.chatterbox.ChatHelper;
import com.coremacasia.chatterbox.R;
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

class UserListAdapter extends RecyclerView.Adapter {
    List<UserHelper> dataList = new ArrayList<>();
    private static final String TAG = "UserListAdapter";
    public UserListAdapter(FragmentActivity activity) {
        //Offline Data store
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(settings);
        //Offline Data store

        CollectionReference ref = db.collection("users");
        ref.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange documentChange:queryDocumentSnapshots.getDocumentChanges()){
                    switch (documentChange.getType()) {
                        case ADDED:
                            UserHelper helper = documentChange.getDocument().toObject(UserHelper.class);
                            dataList.add(helper);
                            notifyItemInserted(dataList.size()-1);
                    }
                }
            }
        });


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());// initialization of inflat
        View view1 = inflater.inflate(R.layout.list_user, parent, false);// assign view with help of inflator
        holder = new UserHolder(view1);// assign view / xml to holder class
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserHolder myViewHolder = (UserHolder) holder;
        myViewHolder.start(position, holder, dataList);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
       // UserHelper userHelper=dataList.get(position);
        //Log.e(TAG, "getItemViewType: Email: "+userHelper.getEmail());
        return super.getItemViewType(position);
    }
}

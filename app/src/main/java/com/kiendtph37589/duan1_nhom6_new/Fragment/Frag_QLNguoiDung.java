package com.kiendtph37589.duan1_nhom6_new.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kiendtph37589.duan1_nhom6_new.Adapter.UserAdapter;
import com.kiendtph37589.duan1_nhom6_new.DTO.User;
import com.kiendtph37589.duan1_nhom6_new.R;

import java.util.ArrayList;
import java.util.List;

public class Frag_QLNguoiDung extends Fragment {
    List<User> list ;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    String id;
    User user = new User();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth;
    public Frag_QLNguoiDung() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_ql_nguoi_dung, container, false);

        recyclerView = view.findViewById(R.id.re_nguoiDung);
        loatData();
        firebaseAuth = FirebaseAuth.getInstance();


        return view;
    }
    public boolean isValidateEmail(CharSequence e) {
        return !TextUtils.isEmpty(e) && Patterns.EMAIL_ADDRESS.matcher(e).matches();
    }

    public boolean isValidatePhone(CharSequence e) {
        return !TextUtils.isEmpty(e) && Patterns.PHONE.matcher(e).matches();
    }

    public void loatData() {
        nghe();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list =new ArrayList<>();
        recyclerView.setAdapter(new UserAdapter(getContext(), list));
        userAdapter = new UserAdapter(getContext(), list);
        recyclerView.setAdapter(userAdapter);
    }




    private void nghe() {
        db.collection("User").whereEqualTo("chucVu", 3).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                if (value != null) {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        Log.e("TAG", "onEvent: " + dc.getType());
                        switch (dc.getType()) {
                            case ADDED:
                                dc.getDocument().toObject(User.class);
                                list.add(dc.getDocument().toObject(User.class));
                                userAdapter.notifyDataSetChanged();
                                break;
                            case MODIFIED:
                                User user1 = dc.getDocument().toObject(User.class);
                                if (dc.getOldIndex() == dc.getNewIndex()) {
                                    list.set(dc.getOldIndex(), user1);
                                    userAdapter.notifyDataSetChanged();
                                } else {
                                    list.remove(dc.getOldIndex());
                                    list.add(user1);
                                    userAdapter.notifyDataSetChanged();
                                }
                                break;
                            case REMOVED:
                                dc.getDocument().toObject(User.class);
                                list.remove(dc.getOldIndex());
                                userAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }
}
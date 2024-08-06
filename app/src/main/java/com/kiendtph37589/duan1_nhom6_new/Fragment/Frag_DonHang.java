package com.kiendtph37589.duan1_nhom6_new.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kiendtph37589.duan1_nhom6_new.Adapter.donHang_Adapter;
import com.kiendtph37589.duan1_nhom6_new.DTO.DonHangDTO;
import com.kiendtph37589.duan1_nhom6_new.R;

import java.util.ArrayList;
import java.util.List;

public class Frag_DonHang extends Fragment {
    RecyclerView rcv_list;
    donHang_Adapter donHangAdapter;
    List<DonHangDTO> list;
    FirebaseFirestore db;
    FirebaseUser user ;
    String TAG = "TAG";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_don_hang,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);
    }

    private void anhXa(View view) {
        rcv_list = view.findViewById(R.id.Rcv_donhang);
        user = FirebaseAuth.getInstance().getCurrentUser();
        getData();

    }

    private void getData() {
        db = FirebaseFirestore.getInstance();
        db.collection("donHang").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (!task.isComplete()){
                    return;
                }
                if (task.getResult().isEmpty()) {
                    // Xử lý trường hợp không có dữ liệu
                    return;
                }

                list= new ArrayList<>();
                donHangAdapter= new donHang_Adapter(list,getContext(),1);
                rcv_list.setAdapter(donHangAdapter);
                rcv_list.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                for (QueryDocumentSnapshot dc : task.getResult()){

                    if (user.getUid().equals(dc.toObject(DonHangDTO.class).getMaKhachHang())) {
                        list.add(dc.toObject(DonHangDTO.class));
                        donHangAdapter.notifyDataSetChanged();

                    }
                }
            }
        });
    }
}

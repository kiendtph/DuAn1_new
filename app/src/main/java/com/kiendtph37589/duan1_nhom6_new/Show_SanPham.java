package com.kiendtph37589.duan1_nhom6_new;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kiendtph37589.duan1_nhom6_new.Adapter.item_tt_Adapter;
import com.kiendtph37589.duan1_nhom6_new.DTO.SanPhamDTO;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Show_SanPham extends AppCompatActivity {
    RecyclerView rc_list;
    item_tt_Adapter item_CuaHang;
    List<SanPhamDTO> list;
    FirebaseFirestore db;
    TextView tenHang;
    ImageView img_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_san_pham);

        Intent intent = getIntent();
        String[] a = intent.getStringArrayExtra("list");

        if (a == null){
            return;
        }
        list = new ArrayList<>();
        rc_list = findViewById(R.id.rcv_list_sanPham_more);
        tenHang = findViewById(R.id.tv_ten_hang_show);

        tenHang.setText(a[1]);
        img_close = findViewById(R.id.iv_close);

        item_CuaHang = new item_tt_Adapter(Show_SanPham.this,list);
        rc_list.setAdapter(item_CuaHang);
        GridLayoutManager gridLayoutManager  = new GridLayoutManager(Show_SanPham.this,2);
        rc_list.setLayoutManager(gridLayoutManager);
        laydulieu(a[0]);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void laydulieu(String a){
        db = FirebaseFirestore.getInstance();
        db.collection("Sanpham").whereEqualTo("maHang",a)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value == null){
                            return;
                        }
                        if (error != null){
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            switch (dc.getType()){
                                case ADDED:
                                    list.add(dc.getDocument().toObject(SanPhamDTO.class));
                                    item_CuaHang.notifyDataSetChanged();
                                    break;
                                case MODIFIED:
                                    SanPhamDTO sanPhamDTO = dc.getDocument().toObject(SanPhamDTO.class);
                                    if (dc.getOldIndex() == dc.getNewIndex()){
                                        list.set(dc.getOldIndex(), sanPhamDTO);
                                        item_CuaHang.notifyDataSetChanged();
                                    }else {
                                        list.remove(dc.getOldIndex());
                                        list.add(sanPhamDTO);
                                        item_CuaHang.notifyDataSetChanged();
                                    }
                                    break;
                                case REMOVED:
                                    dc.getDocument().toObject(SanPhamDTO.class);
                                    list.remove(dc.getOldIndex());
                                    item_CuaHang.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}
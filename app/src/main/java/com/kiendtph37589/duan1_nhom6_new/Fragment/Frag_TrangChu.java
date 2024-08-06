package com.kiendtph37589.duan1_nhom6_new.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kiendtph37589.duan1_nhom6_new.Adapter.Banner_Adapter;
import com.kiendtph37589.duan1_nhom6_new.Adapter.trangChu_Adapter;
import com.kiendtph37589.duan1_nhom6_new.DTO.HangDTO;
import com.kiendtph37589.duan1_nhom6_new.DTO.SanPhamDTO;
import com.kiendtph37589.duan1_nhom6_new.DTO.banner;
import com.kiendtph37589.duan1_nhom6_new.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Frag_TrangChu extends Fragment {
    RecyclerView rc_list;
    ViewPager2 viewPager2Banner;
    Banner_Adapter bannerAdapter;
    List<banner> bannerList;
    private int currentPage = 0;
    private Timer timer;
    EditText editText;
    ImageView imageView;
    List<HangDTO> listThuongHieu;
    List<SanPhamDTO> sanPhamDTOList;
    FirebaseFirestore db;
    trangChu_Adapter trangChuAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_trangchu,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listThuongHieu = new ArrayList<>();
        sanPhamDTOList = new ArrayList<>();
        trangChuAdapter = new trangChu_Adapter(listThuongHieu,getContext());

        viewPager2Banner = view.findViewById(R.id.viewPager_banner);

        rc_list = view.findViewById(R.id.rc_trangChu);
        imageView = view.findViewById(R.id.img_timKiem);
        editText = view.findViewById(R.id.ed_timKiem);

        bannerAdapter = new Banner_Adapter(getanh(), getContext());
        rc_list.setAdapter(trangChuAdapter);
        rc_list.setLayoutManager(new LinearLayoutManager(getContext()));

        viewPager2Banner.setAdapter(bannerAdapter);
        startAutoSwipeTimer();

        laydulieu();
    }

    private void startAutoSwipeTimer() {
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable update = () -> {
            if (currentPage == getanh().size()){
                currentPage = 0;
            }
            viewPager2Banner.setCurrentItem(currentPage++, true);
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },500,3000);
    }
    public void onDestroy(){
        if (timer != null){
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }

    private void laydulieu(){
        db = FirebaseFirestore.getInstance();
        db.collection("thuonghieu").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    return;
                }
                if (value == null){
                    return;
                }
                sanPhamDTOList.clear();
                listThuongHieu.clear();
                for (DocumentSnapshot dc : value.getDocuments()){
                    AddListSP(dc.getString("maHang"), dc.getString("tenHang"));
                }
            }
        });
    }
    private void AddListSP(String maHang,String tenHang){
        db.collection("Sanpham").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    return;
                }
                if (value == null){
                    return;
                }
                for (DocumentChange dc : value.getDocumentChanges()){
                    if (maHang.equals(dc.getDocument().get("maHang").toString())){
                        sanPhamDTOList.add(dc.getDocument().toObject(SanPhamDTO.class));

                    }
                    switch (dc.getType()){
                        case MODIFIED:
                            return;
                    }
                }
                listThuongHieu.add(new HangDTO(maHang, tenHang, sanPhamDTOList));
                listThuongHieu.sort(new Comparator<HangDTO>() {
                    @Override
                    public int compare(HangDTO o1, HangDTO o2) {
                        return (o2.getSanPham().size() - o1.getSanPham().size());
                    }
                });
                trangChuAdapter.notifyDataSetChanged();
                sanPhamDTOList = new ArrayList<>();
            }
        });
        for (HangDTO h : listThuongHieu){
            if (h.getSanPham().size() <= 0){
                listThuongHieu.remove(h);
                trangChuAdapter.notifyDataSetChanged();
            }
        }
    }
    private List<banner> getanh() {
        List<banner> list = new ArrayList<>();
        list.add(new banner(R.drawable.bannner1));
        list.add(new banner(R.drawable.banner2));
        list.add(new banner(R.drawable.banner3));
        return list;
    }
}

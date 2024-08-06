package com.kiendtph37589.duan1_nhom6_new.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kiendtph37589.duan1_nhom6_new.DTO.SanPhamDTO;
import com.kiendtph37589.duan1_nhom6_new.R;

import java.util.HashMap;
import java.util.List;

public class Top10_Adapter extends RecyclerView.Adapter<Top10_Adapter.ViewHolder> {
    Context context;
    List<SanPhamDTO>list_sanPham;
    FirebaseFirestore db;
    List<HashMap<String, Object>> list_top10;
    public Top10_Adapter(Context context, List<SanPhamDTO> list_sanPham,  List<HashMap<String, Object>> list_top10) {
        this.context = context;
        this.list_sanPham = list_sanPham;
        this.list_top10 = list_top10;
    }
    @NonNull
    @Override
    public Top10_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_top10, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Top10_Adapter.ViewHolder holder, int position) {
        SanPhamDTO sanPhamDTO = maSP(list_top10.get(position).get("maSP").toString());
        Log.e("TAG","" + sanPhamDTO.getAnh());
        if (sanPhamDTO==null){
            return;
        }
        Log.e("TAG",""+sanPhamDTO.getTenSP());
        Glide.with(context).load(sanPhamDTO.getAnh()).error(R.drawable.baseline_crop_original_24).into(holder.img_anh);
        holder.tv_tenSp.setText("Tên sản phẩm: "+sanPhamDTO.getTenSP());
//        holder.tv_thuonghieu.setText("Hãng: "+sanPhamDTO.getTenSP());
        holder.tv_soLuong.setText("Số lượng: "+list_top10.get(position).get("soLuong").toString());
    }
    public SanPhamDTO maSP(String masp) {
        SanPhamDTO sanPham = new SanPhamDTO();
        for (SanPhamDTO sp : list_sanPham) {
            if (masp.equals(sp.getMaSp())) {
                return sp;
            }

        }
        return sanPham;
    }
    @Override
    public int getItemCount() {
        return list_top10.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_tenSp, tv_soLuong;
        ImageView img_anh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenSp = itemView.findViewById(R.id.tv_TenSP);
            tv_soLuong = itemView.findViewById(R.id.tv_SoLuong);
            img_anh = itemView.findViewById(R.id.img_anhtop10);
        }
    }
}

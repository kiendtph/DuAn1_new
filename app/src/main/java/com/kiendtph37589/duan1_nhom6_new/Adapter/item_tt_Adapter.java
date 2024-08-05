package com.kiendtph37589.duan1_nhom6_new.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kiendtph37589.duan1_nhom6_new.ChiTietSPActivity;
import com.kiendtph37589.duan1_nhom6_new.DTO.SanPhamDTO;
import com.kiendtph37589.duan1_nhom6_new.R;

import java.util.List;

public class item_tt_Adapter extends RecyclerView.Adapter<item_tt_Adapter.ViewHolder> {
    Context context;
    List<SanPhamDTO> list;

    public item_tt_Adapter(Context context, List<SanPhamDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(((Activity)context).getLayoutInflater()
                .inflate(R.layout.item_sanpham_trangchu,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(list.get(position).getAnh()).error(R.drawable.baseline_crop_original_24).into(holder.anhSp);
        holder.TenSp.setText(list.get(position).getTenSP());
        holder.Gia.setText("Giá: " + list.get(position).getGia() + " VND");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietSPActivity.class);
                intent.putExtra("Sanpham",list.get(position).getMaSp());
                (context).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView anhSp;
        TextView Gia, TenSp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            anhSp = itemView.findViewById(R.id.img_anh_sp_trangChu);
            Gia = itemView.findViewById(R.id.tv_giasp_trangChu);
            TenSp = itemView.findViewById(R.id.tv_tensp_trangChu);
        }
    }
}

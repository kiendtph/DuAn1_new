package com.kiendtph37589.duan1_nhom6_new.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;


import com.kiendtph37589.duan1_nhom6_new.DTO.SanPhamDTO;
import com.kiendtph37589.duan1_nhom6_new.R;
import com.kiendtph37589.duan1_nhom6_new.taikhoan.ChiTietSPActivity;

public class Timkiem_Adapter extends RecyclerView.Adapter<Timkiem_Adapter.ViewHolder> implements Filterable {

    List<SanPhamDTO> list;
    List<SanPhamDTO> listsp;
    Context context;
    item_tt_Adapter adapterItemTt;


    public Timkiem_Adapter(List<SanPhamDTO> list, Context context) {
        this.list = list;
        this.listsp = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(((Activity) context).getLayoutInflater().inflate(R.layout.item_sanpham_trangchu, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(list.get(position).getAnh()).
                error(R.drawable.baseline_crop_original_24).into(holder.anhSp);
        holder.TenSp.setText(list.get(position).getTenSP());
        holder.Gia.setText("Giá: " + list.get(position).getGia() + " VND");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChiTietSPActivity.class);
                intent.putExtra("Sanpham", list.get(position).getMaSp());
                (context).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView anhSp;
        TextView Gia, TenSp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            anhSp = itemView.findViewById(R.id.imv_anh_sp_trangchu);
            Gia = itemView.findViewById(R.id.tv_giasp_trangchu);
            TenSp = itemView.findViewById(R.id.tv_tensp_trangchu);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String sttimkiem = charSequence.toString();
                if (sttimkiem.isEmpty()) {
                    list = listsp;
                } else {
                    List<SanPhamDTO> sanPhamDTOList = new ArrayList<>();
                    for (SanPhamDTO sanPhamDTO : listsp) {
                        if (sanPhamDTO.getTenSP().toLowerCase().contains(sttimkiem.toLowerCase())) {
                            sanPhamDTOList.add(sanPhamDTO);
                        }

                    }
                    list = sanPhamDTOList;

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (List<SanPhamDTO>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
package com.kiendtph37589.duan1_nhom6_new.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kiendtph37589.duan1_nhom6_new.DTO.HangDTO;
import com.kiendtph37589.duan1_nhom6_new.DTO.SanPhamDTO;
import com.kiendtph37589.duan1_nhom6_new.R;
import com.kiendtph37589.duan1_nhom6_new.Show_SanPham;

import java.util.List;

public class trangChu_Adapter extends RecyclerView.Adapter<trangChu_Adapter.ViewHolder> {
    List<HangDTO> list;
    Context context;
    item_tt_Adapter itemTtAdapter;

    public trangChu_Adapter(List<HangDTO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(((Activity)context).getLayoutInflater().inflate(R.layout.item_sanpham_trangchuu,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list.get(position).getSanPham().size()==0){
            return;
        }
        holder.tenHang.setText(list.get(position).getTenHang());

        itemTtAdapter = new item_tt_Adapter(context, list.get(position).getSanPham());
        holder.rc_list.setAdapter(itemTtAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
        holder.rc_list.setLayoutManager(manager);
        holder.xemThem.setText("Xem thêm");
        Log.e("TAG", "onBindViewHolder: "+list.get(position).getSanPham().get(0).getTenSP() );
        holder.xemThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Show_SanPham.class);
                List<SanPhamDTO> listsp = list.get(position).getSanPham();
                String [] s = new String[]{listsp.get(position).getMaHang(),list.get(position).getTenHang()};
                 intent.putExtra("list", s);
                ((Activity)context).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tenHang;
        TextView xemThem;
        RecyclerView rc_list;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenHang = itemView.findViewById(R.id.tv_tenhang);
            xemThem = itemView.findViewById(R.id.ll_xemthem_moi);
            rc_list = itemView.findViewById(R.id.rcv_list_sp_khach);
        }
    }
}

package com.kiendtph37589.duan1_nhom6_new.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kiendtph37589.duan1_nhom6_new.DTO.banner;
import com.kiendtph37589.duan1_nhom6_new.R;

import java.util.List;

public class Banner_Adapter extends RecyclerView.Adapter<Banner_Adapter.ViewHolder> {
    List<banner> list;
    Context context;

    public Banner_Adapter(List<banner> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(((Activity)context).getLayoutInflater().inflate(R.layout.item_banner,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.img_banner.setImageResource(list.get(position).getBannerid());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_banner;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_banner = itemView.findViewById(R.id.img_banner);
        }
    }
}

package com.kiendtph37589.duan1_nhom6_new.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kiendtph37589.duan1_nhom6_new.DTO.HangDTO;
import com.kiendtph37589.duan1_nhom6_new.R;

import java.util.List;

public class thuongHieu_Adapter extends BaseAdapter {
    List<HangDTO> list;
    Context context;

    public thuongHieu_Adapter(List<HangDTO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thuonghieu,parent,false);
        TextView ten = view.findViewById(R.id.tv_ten_thuonghieu);
        ten.setText(list.get(position).getTenHang());
        return view;
    }
}

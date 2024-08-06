package com.kiendtph37589.duan1_nhom6_new.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kiendtph37589.duan1_nhom6_new.DTO.DonDTO;
import com.kiendtph37589.duan1_nhom6_new.DTO.DonHangDTO;
import com.kiendtph37589.duan1_nhom6_new.DTO.HangDTO;
import com.kiendtph37589.duan1_nhom6_new.DTO.SanPhamDTO;
import com.kiendtph37589.duan1_nhom6_new.DTO.gioHangDTO;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_GioHang;
import com.kiendtph37589.duan1_nhom6_new.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class gioHang_Adapter extends RecyclerView.Adapter<gioHang_Adapter.ViewHoler> {
    List<gioHangDTO> list_gio;
    List<SanPhamDTO> list_sanPham;
    List<HangDTO> list_hang;
    Context context;
    Frag_GioHang gioHang;
    FirebaseFirestore db;
    FirebaseUser User;
    TextView tongGia;
    gioHang_Adapter gioHangAdapter;
    List<DonDTO> listMaSP;

    public gioHang_Adapter(List<gioHangDTO> list_gio, List<SanPhamDTO> list_sanPham, List<HangDTO> list_hang, Context context, Frag_GioHang gioHang) {
        this.list_gio = list_gio;
        this.list_sanPham = list_sanPham;
        this.list_hang = list_hang;
        this.context = context;
        this.gioHang = gioHang;
        db = FirebaseFirestore.getInstance();
        User = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHoler(((Activity) context).getLayoutInflater().
                inflate(R.layout.item_sanpham_giohang, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, @SuppressLint("RecyclerView") int position) {
        // Lấy mã sản phẩm từ giỏ hàng
        String maSanPham = list_gio.get(position).getMaSanPham();

        // Tìm đối tượng SanPhamDTO tương ứng
        SanPhamDTO sp = getSanPham(maSanPham);
        if (sp == null) {
            return;
        }

        // Lấy URL ảnh từ SanPhamDTO
        String urlAnh = sp.getAnh();
        if (urlAnh.isEmpty()) {
            return;
        }

        // Sử dụng Glide để tải ảnh
        Glide.with(context)
                .load(urlAnh)
                .error(R.drawable.baseline_crop_original_24)
                .into(holder.anh);

        // Hiển thị thông tin sản phẩm
        holder.tenSP.setText(sp.getTenSP());
        holder.giaSP.setText("Giá: " + sp.getGia() + " VND");

        String tenHang = getTenLoai(sp.getMaHang());
        if (tenHang == null) {
            return;
        }
        holder.loaiSP.setText("Loại: " + tenHang);
        holder.soLuong.setText("Số lượng: " + list_gio.get(position).getSoLuong());
        holder.kichCo.setText("Kích cỡ: " + list_gio.get(position).getKichCo());

        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xoa(list_gio.get(position).getMaGio());
            }
        });

        holder.mua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                them(position);
            }
        });

    }
    private String laylink(String maSanPham) {
        String link = "";
        for (SanPhamDTO s : list_sanPham) {
            if (maSanPham.equals(s.getMaSp())) {
                link = s.getAnh();
                return link;
            }

        }
        return link;
    }
        private void them(int position) {
//        List<Don> listDon = new ArrayList<>();
//        listDon.add(new Don(list_gio.get(position).getMaSanPham(),list_gio.get(position).getSoLuong()));
//        String maDon = UUID.randomUUID().toString();
//        Calendar lich = Calendar.getInstance();
//        int ngay = lich.get(Calendar.DAY_OF_MONTH);
//        int thang =lich.get(Calendar.MONTH)+1;
//        int nam = lich.get(Calendar.YEAR);
//        String ngayMua = nam+"/"+thang+"/"+ngay;
//        db.collection("donHang").document(maDon).set(new DonHang(maDon,User.getUid(),listDon,list_sanPham,0,TongGiaSP(position),ngayMua))
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isComplete()){
//                            Toast.makeText(context, "Đơn hàng đang chờ nhân viên xác nhận", Toast.LENGTH_SHORT).show();
//                            xoa(list_gio.get(position).getMaGio());
//                        }else {
//                            Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            List<String> listMaGio = getListMa();
            if (listMaGio.size()<=0) {
                Toast.makeText(context, "Vui lòng thêm sản phẩm vào giỏ", Toast.LENGTH_SHORT).show();
                return;
            }
            String maDon = UUID.randomUUID().toString();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Calendar lich = Calendar.getInstance();
            int ngay = lich.get(Calendar.DAY_OF_MONTH);
            int thang = lich.get(Calendar.MONTH)+1;
            int nam = lich.get(Calendar.YEAR);
            String ngayMua = nam+"/"+thang+"/"+ngay;
            db.collection("donHang").document(maDon).set(new DonHangDTO(maDon,user.getUid(),listMaSP,new Date().getTime(),0, (Long) TongGiaSP(position),ngayMua))
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()){
                                Toast.makeText(context, "Đơn hàng đang chờ nhân viên xác nhận", Toast.LENGTH_SHORT).show();
//                            guiThongBao();
                            }else {
                                Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            for (String s : listMaGio){
                db.collection("gioHang").document(s).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()){
                            notifyDataSetChanged();
                        }else {
                            Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
        }

    private List<String> getListMa() {
        List<String> listGio = new ArrayList<>();
        listMaSP = new ArrayList<>();
        for (gioHangDTO gh : list_gio){
            listGio.add(gh.getMaGio());
            listMaSP.add(new DonDTO(gh.getMaSanPham(),gh.getSoLuong()));
        }
        return listGio;
    }
    private Object TongGiaSP(int position) {
        for (SanPhamDTO g : list_sanPham){
            if (list_gio.get(position).getMaSanPham().equals(g.getMaSp())){
                return (list_gio.get(position).getSoLuong()*g.getGia());
            }
        }
        return 0l;
    }

    private void xoa(String maGio) {
        db.collection("gioHang").document(maGio).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()){
                    Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
//                    guiThongBao();
                }else {
                    Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //    private void guiThongBao() {
//        String id = UUID.randomUUID().toString();
//        db.collection("thongBao").document(id).set(new ThongBao(id,user.getUid(),"Có đơn hàng mới của "+user.getUid(),2,new Date().getTime())).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isComplete()){
//                }
//            }
//        });
//    }
    private String getTenLoai(String maHang) {
        for (HangDTO s : list_hang) {
            if (maHang.equals(s.getMaHang())) {
                return s.getTenHang();
            }
        }
        return null;
    }
    private SanPhamDTO getSanPham(String maSP) {

        for (SanPhamDTO s : list_sanPham) {
            if (maSP.equals(s.getMaSp())) {
                return s;
            }
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return list_gio.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        ImageView anh;
        TextView tenSP, loaiSP, kichCo, soLuong, giaSP, mua, xoa;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            anh = itemView.findViewById(R.id.imv_anh_sp_gioHang);
            tenSP = itemView.findViewById(R.id.tv_tensp_gioHang);
            giaSP = itemView.findViewById(R.id.tv_giasp_giohang);
            loaiSP = itemView.findViewById(R.id.tv_thuonghieu_gioHang);
            kichCo = itemView.findViewById(R.id.tv_kichcosp_giohang);
            soLuong = itemView.findViewById(R.id.tv_soluongsp_giohang);
            mua = itemView.findViewById(R.id.tv_mua_giohang);
            xoa = itemView.findViewById(R.id.tv_xoa_giohang);
        }
    }
}

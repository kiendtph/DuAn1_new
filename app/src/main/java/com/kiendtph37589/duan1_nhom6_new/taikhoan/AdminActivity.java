package com.kiendtph37589.duan1_nhom6_new.taikhoan;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_DoanhThu;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_DoiMatKhau;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_QLNguoiDung;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_QLNhanVien;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_QLSanPham;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_ThemTK;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_Top10;
import com.kiendtph37589.duan1_nhom6_new.R;

public class AdminActivity extends AppCompatActivity {
    Toolbar toolbar;
    Frag_QLSanPham Frag_QLSanPham=new Frag_QLSanPham();
    NavigationView navView;
    //    FrameLayout frameLayout;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    TextView tv_header;
    ImageView img_avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toolbar = findViewById(R.id.toolbar);
        navView = findViewById(R.id.nav_view);
//        frameLayout = findViewById(R.id.frame_layout);
        drawerLayout = findViewById(R.id.drawer_layout);


        //ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

        drawerToggle = new ActionBarDrawerToggle(AdminActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        //Frag mặc định
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new Frag_QLNhanVien())
                .commit();




        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                String title = "";


                if (item.getItemId() == R.id.ql_nhanVien){
                    fragment = new Frag_QLNhanVien();
                    title = "Quản lý nhân viên";
                }else if(item.getItemId() == R.id.ql_nguoiDung){
                    fragment = new Frag_QLNguoiDung();
                    title = "Quản lý người dùng";
                }else if(item.getItemId() == R.id.ql_sanPham){
                    fragment =Frag_QLSanPham ;
                    title = "Quản lý sản phẩm";

                }else if(item.getItemId() == R.id.top10){
                    fragment = new Frag_Top10();
                    title = "Top 10 bán chạy";
                }else if(item.getItemId() == R.id.doanh_thu){
                    fragment = new Frag_DoanhThu();
                    title = "Doanh thu";
                }else if(item.getItemId() == R.id.thong_tin_tk){
                    fragment = new Frag_ThemTK();
                    title = "Thêm tài khoản";
                }else if(item.getItemId() == R.id.doi_mk){
                    fragment = new Frag_DoiMatKhau();
                    title = "Đổi mật khẩu";
                }else if(item.getItemId() == R.id.dang_xuat) {
                    fragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                    FirebaseAuth.getInstance().signOut();



                    if (fragment != null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                        builder.setIcon(R.drawable.canh_bao);
                        builder.setTitle("Thng báo!");
                        builder.setMessage("Bạn có chắc muốn đăng xuất?");
                        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(AdminActivity.this, DangNhapActivity.class));
                                AuthUI.getInstance()
                                        .signOut(AdminActivity.this)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            public void onComplete(@NonNull Task<Void> task) {
                                                // ...
                                            }
                                        });

                            }
                        });
                        builder.setNegativeButton("Hủy", null);
                        builder.show();
                    }
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .commit();
                getSupportActionBar().setTitle(title);
                drawerLayout.close();

                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK) {
                        Intent intent = o.getData();
                        if (intent == null) {
                            return;
                        }
                        //để lấy ảnh
                        Frag_QLSanPham.setUri(intent.getData());


                    }

                }
            });
    public void layAnh() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);
    }

    public void yeucauquyen(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            layAnh();
            return;
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            String[] quyen = new String[]{android.Manifest.permission.READ_MEDIA_IMAGES};
            requestPermissions(quyen, CODE_QUYEN);
            return;
        }
        if (context.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // xử lý sau
            layAnh();
        } else {
            String[] quyen = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(quyen, CODE_QUYEN);
        }
    }

    private static final int CODE_QUYEN = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_QUYEN) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                layAnh();
            } else {
                Toast.makeText(this, "Bạn cần cấp quyền để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
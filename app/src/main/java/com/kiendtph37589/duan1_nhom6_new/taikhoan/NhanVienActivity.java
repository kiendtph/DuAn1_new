package com.kiendtph37589.duan1_nhom6_new.taikhoan;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.PackageManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_DoanhThu;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_DoiMatKhau;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_DonHang;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_QLDonHang;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_QLSanPham;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_Top10;
import com.kiendtph37589.duan1_nhom6_new.R;

public class NhanVienActivity extends AppCompatActivity {
    Toolbar toolbar;
    Frag_QLSanPham frag_qlSanPham = new Frag_QLSanPham();
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(NhanVienActivity.this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new Frag_QLSanPham())
                .commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                String title = "";

                if (menuItem.getItemId() ==  R.id.ql_sanPham_nv){
                    fragment = frag_qlSanPham;
                    title = "Quản lý sản phẩm";
                } else if (menuItem.getItemId() == R.id.ql_donHang_nv) {
                    fragment = new Frag_QLDonHang();
                    title = "Quản lý đơn hàng";
                } else if (menuItem.getItemId() == R.id.top10_nv) {
                    fragment = new Frag_Top10();
                    title = "Top 10 bán chạy";
                } else if (menuItem.getItemId() == R.id.doanh_thu_nv) {
                    fragment = new Frag_DoanhThu();
                    title = "Doanh thu";
                }else if(menuItem.getItemId() == R.id.doi_mk_nv){
                    fragment = new Frag_DoiMatKhau();
                    title = "Đổi mật khẩu";
                } else if (menuItem.getItemId() == R.id.dang_xuat_nv) {
                    fragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
                    FirebaseAuth.getInstance().signOut();

                    if (fragment!= null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(NhanVienActivity.this);
                        builder.setIcon(R.drawable.canh_bao);
                        builder.setTitle("Thông báo!");
                        builder.setMessage("Bạn có chắc muốn đăng xuất?");
                        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(NhanVienActivity.this, DangNhapActivity.class));
                            }
                        });
                        builder.setNegativeButton("Hủy",null);
                        builder.show();
                    }
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout,fragment)
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK){
                        Intent intent = o.getData();
                        if (intent == null){
                            return;
                        }
                        frag_qlSanPham.setUri(intent.getData());
                    }
                }
            });
    public void layanh(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);
    }

    private static final int CODE_QUYEN = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_QUYEN){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                layanh();
            }else {
                Toast.makeText(this, "Bạn cần cấp quyền để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
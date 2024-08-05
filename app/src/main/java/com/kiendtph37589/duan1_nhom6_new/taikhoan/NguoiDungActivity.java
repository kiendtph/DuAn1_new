package com.kiendtph37589.duan1_nhom6_new.taikhoan;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_DonHang;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_GioHang;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_TaiKhoan;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_TimKiem;
import com.kiendtph37589.duan1_nhom6_new.Fragment.Frag_TrangChu;
import com.kiendtph37589.duan1_nhom6_new.R;

public class NguoiDungActivity extends AppCompatActivity {
    FrameLayout frameLayout;

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);
        frameLayout = findViewById(R.id.frame_layout);
        bottomNavigationView = findViewById(R.id.menu_nav);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Frag_TrangChu frag_TrangChu = new Frag_TrangChu();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, frag_TrangChu)
                .commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                if (menuItem.getItemId() == R.id.trang_chu) {
                    fragment = new Frag_TrangChu();
                } else if (menuItem.getItemId() == R.id.tim_kiem) {
                    fragment = new Frag_TimKiem();
                } else if (menuItem.getItemId() == R.id.gio_hang) {
                    fragment = new Frag_GioHang();
                } else if (menuItem.getItemId() == R.id.don_hang) {
                    fragment = new Frag_DonHang();
                } else if (menuItem.getItemId() == R.id.tai_khoan) {
                    fragment = new Frag_TaiKhoan();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .commit();
                return true;
            }
        });

    }
}
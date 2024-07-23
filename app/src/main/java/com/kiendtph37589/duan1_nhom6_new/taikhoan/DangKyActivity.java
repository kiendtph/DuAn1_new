package com.kiendtph37589.duan1_nhom6_new.taikhoan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kiendtph37589.duan1_nhom6_new.R;

public class DangKyActivity extends AppCompatActivity {
    TextView tv_DangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        tv_DangNhap = findViewById(R.id.tv_DanhNhap);
        tv_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangKyActivity.this, DangNhapActivity.class));
            }
        });
    }
}
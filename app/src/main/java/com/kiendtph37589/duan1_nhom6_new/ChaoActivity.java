package com.kiendtph37589.duan1_nhom6_new;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kiendtph37589.duan1_nhom6_new.taikhoan.DangNhapActivity;

public class ChaoActivity extends AppCompatActivity {
    ImageView img_logo_chao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chao);
        img_logo_chao = findViewById(R.id.img_logo_chao);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ChaoActivity.this, DangNhapActivity.class);
                startActivity(intent);
                finish();
            }
        },3500);
    }
}
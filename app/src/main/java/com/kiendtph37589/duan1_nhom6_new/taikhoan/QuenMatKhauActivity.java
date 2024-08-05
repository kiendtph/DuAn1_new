package com.kiendtph37589.duan1_nhom6_new.taikhoan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.kiendtph37589.duan1_nhom6_new.R;

public class QuenMatKhauActivity extends AppCompatActivity {
    private EditText edt_MkEmail;
    private Button btn_LayLaiMK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau);
        edt_MkEmail = findViewById(R.id.edt_QMK_Email);
        btn_LayLaiMK = findViewById(R.id.btn_LayLaiMK);
        btn_LayLaiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                String email = edt_MkEmail.getText().toString();

                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(QuenMatKhauActivity.this, "Thành công!! Kiểm tra email để lấy lại mật khẩu", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(QuenMatKhauActivity.this, DangNhapActivity.class));
                        }else {
                            Toast.makeText(QuenMatKhauActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
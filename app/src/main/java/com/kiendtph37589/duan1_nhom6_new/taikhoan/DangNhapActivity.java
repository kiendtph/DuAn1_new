package com.kiendtph37589.duan1_nhom6_new.taikhoan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kiendtph37589.duan1_nhom6_new.DTO.User;
import com.kiendtph37589.duan1_nhom6_new.R;

public class DangNhapActivity extends AppCompatActivity {
    private TextView tv_DangKy,tv_QuenMatKhau;
    private TextInputEditText edt_Email,edt_MatKhau;
    private Button btn_DangNhap;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        tv_QuenMatKhau = findViewById(R.id.tv_quenMatKhau);
        tv_DangKy = findViewById(R.id.tv_DangKy);
        firebaseAuth = FirebaseAuth.getInstance();
        edt_Email = findViewById(R.id.edt_email);
        edt_MatKhau = findViewById(R.id.edt_MatKhau);
        btn_DangNhap = findViewById(R.id.btn_DangNhap);
        tv_DangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangNhapActivity.this, DangKyActivity.class));

            }
        });
        tv_QuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quenMk = new Intent(DangNhapActivity.this, QuenMatKhauActivity.class);
                startActivity(quenMk);
            }
        });

        btn_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,mk;
                email = edt_Email.getText().toString();
                mk = edt_MatKhau.getText().toString();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(DangNhapActivity.this, "Vui lòng nhập tài khoản!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mk)){
                    Toast.makeText(DangNhapActivity.this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email,mk)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection("User").whereEqualTo("email",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isComplete()){
                                                User user = new User();
                                                for (QueryDocumentSnapshot c : task.getResult()){
                                                    user = c.toObject(User.class);
                                                    Log.e("TAG", "onComplete: 9 " + user.getChucVu() );
                                                }
                                                if (user.getChucVu() == 3){
                                                    Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(DangNhapActivity.this, NguoiDungActivity.class));
                                                }else if(user.getChucVu() == 1){
                                                    Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(DangNhapActivity.this, AdminActivity.class));
                                                    Log.e("TAG", "onComplete: " + user.getEmail() );
                                                } else if (user.getChucVu() == 2) {
                                                    Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(DangNhapActivity.this, NhanVienActivity.class));
                                                }else {
                                                    Toast.makeText(DangNhapActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(DangNhapActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
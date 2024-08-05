package com.kiendtph37589.duan1_nhom6_new.taikhoan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kiendtph37589.duan1_nhom6_new.DTO.User;
import com.kiendtph37589.duan1_nhom6_new.R;

public class DangKyActivity extends AppCompatActivity {
    private TextView tv_DangNhap;
    private EditText edt_DKHoVaTen,edt_DKEmail,edt_DKSoDienThoai;
    private TextInputEditText edt_DKMatKhau,edt_DKNhapLaiMatKhau;
    private Button btn_DangKi;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        edt_DKHoVaTen = findViewById(R.id.edt_DKHoVaTen);
        edt_DKEmail = findViewById(R.id.edt_DKemail);
        edt_DKSoDienThoai = findViewById(R.id.edt_DKSoDienThoai);
        edt_DKMatKhau = findViewById(R.id.edt_DKMatKhau);
        edt_DKNhapLaiMatKhau = findViewById(R.id.edt_DKNhapLaiMatKhau);
        btn_DangKi = findViewById(R.id.btn_DangKy);
        tv_DangNhap = findViewById(R.id.tv_DanhNhap);
        tv_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangKyActivity.this, DangNhapActivity.class));
            }
        });
        btn_DangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangKy();
            }
        });
    }
    private void dangKy(){
        String hoTen = edt_DKHoVaTen.getText().toString();
        String email = edt_DKEmail.getText().toString();
        String sdt = edt_DKSoDienThoai.getText().toString();
        String mk = edt_DKMatKhau.getText().toString();
        String NhapLaiMk = edt_DKNhapLaiMatKhau.getText().toString();
        if (email.isEmpty() || hoTen.isEmpty() || sdt.isEmpty() || mk.isEmpty() || NhapLaiMk.isEmpty()){
            Toast.makeText(this, "Vui lòng không bỏ trống!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isValidatePhone(sdt) || sdt.length()< 10 || sdt.length() > 10){
            Toast.makeText(this, "Số điện thoại phải gồm 10 chữ số!", Toast.LENGTH_SHORT).show();
        }
        if (mk.length() <8){
            Toast.makeText(this, "Mật khẩu phải từ 8 kí tự trở lên!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!mk.equals(edt_DKNhapLaiMatKhau.getText().toString())){
            Toast.makeText(this, "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email,mk).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(DangKyActivity.this, DangNhapActivity.class));
                    Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    finishAffinity();
                    TaoUser();
                }else {
                    Toast.makeText(DangKyActivity.this, "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean isValidateEmail(CharSequence e){
        return !TextUtils.isEmpty(e) && Patterns.EMAIL_ADDRESS.matcher(e).matches();
    }
    public boolean isValidatePhone(CharSequence e){
        return !TextUtils.isEmpty(e) && Patterns.PHONE.matcher(e).matches();
    }
    public void TaoUser(){
        user = firebaseAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        if (user == null){
            return;
        }
        User user1 = new User();
        user1.setMaUser(user.getUid());
        user1.setHoTen(edt_DKHoVaTen.getText().toString());
        user1.setEmail(edt_DKEmail.getText().toString());
        user1.setSDT(edt_DKSoDienThoai.getText().toString());
        user1.setChucVu(3);
        user1.setTrangThai(1);
        firestore.collection("User").document(user1.getMaUser()).set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(DangKyActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DangKyActivity.this, "Lỗi đăng ký!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.kiendtph37589.duan1_nhom6_new.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kiendtph37589.duan1_nhom6_new.R;

public class Frag_DoiMatKhau extends Fragment {
    EditText edt_mk_cu, edt_mk_moi, edt_re_mk;
    Button btn_Huy, btn_luu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.frag_doimk, container, false);

       edt_mk_cu = view.findViewById(R.id.ed_mkcu_dmk);
       edt_mk_moi = view.findViewById(R.id.ed_mkmoi_dmk);
       edt_re_mk = view.findViewById(R.id.ed_re_mkmoi_dmk);
       btn_Huy = view.findViewById(R.id.btn_huy);
       btn_luu = view.findViewById(R.id.btn_luu);
       btn_Huy.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               edt_mk_cu.setText("");
               edt_mk_moi.setText("");
               edt_re_mk.setText("");
           }
       });
       btn_luu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String mkCu = edt_mk_cu.getText().toString();
               String mkMoi = edt_mk_moi.getText().toString();
               String reMk = edt_re_mk.getText().toString();

               if (mkCu.isEmpty() || mkMoi.isEmpty() || reMk.isEmpty()){
                   Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
               }else if (reMk.equals(mkMoi)){
                   doiMk(mkCu, mkMoi);
               }else {
                   Toast.makeText(getContext(), "Xác nhận mật khẩu mới sai", Toast.LENGTH_SHORT).show();
               }
           }
       });

        return view;
    }
    public void doiMk(String passCu, String passMoi){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), passCu);
        user.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    user.updatePassword(passMoi).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(getContext(), "Mật khẩu cũ sai vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

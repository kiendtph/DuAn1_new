package com.kiendtph37589.duan1_nhom6_new.taikhoan;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.kiendtph37589.duan1_nhom6_new.R;

public class ThongTinLHActivity extends AppCompatActivity {
    ImageView img_back;
    LinearLayout ll_face, ll_email, ll_insta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_lhactivity);
        img_back = findViewById(R.id.img_back_lien_he);
        ll_face = findViewById(R.id.linear_facebook);
        ll_email = findViewById(R.id.linear_Email);
        ll_insta = findViewById(R.id.linear_instagram);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFacebookPage();
            }
        });
        ll_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEmail();
            }
        });
        ll_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInstagram();
            }
        });
    }

    private void openEmail() {
        String linkemail = "kiendtph37589@fpt.edu.vn";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Email");
        builder.setMessage(linkemail);
        builder.setPositiveButton("Gửi email", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendEmail();
            }
        });
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void sendEmail() {
        // Mở ứng dụng gửi email
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mail to:" + "kiendtph37589@fpt.edu.vn")); // Thay thế bằng địa chỉ email thực của bạn
        intent.putExtra(Intent.EXTRA_SUBJECT, "Chủ đề email");
        intent.putExtra(Intent.EXTRA_TEXT, "Nội dung email");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void openInstagram() {
        String link = "https://www.instagram.com/t_kien39/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }

    private void openFacebookPage() {
        String link = "https://www.facebook.com/profile.php?id=100014486917685&mibextid=ZbWKwL";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }


}

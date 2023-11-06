package tdtu.report.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import tdtu.report.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_GMAIL_LOGIN = 1;

    private ImageView ivFacebookLogin;
    private ImageView ivGoogleLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvRegister = findViewById(R.id.tvRegister);
        ivFacebookLogin = findViewById(R.id.ivFacebookLogin);
        ivGoogleLogin = findViewById(R.id.ivGoogleLogin);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        ivFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithFacebook();
            }
        });

        ivGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginWithGoogle();
            }
        });
    }

    private void loginWithFacebook() {
        // Thực hiện đăng nhập bằng Facebook
        // Mở trang đăng nhập Facebook trong trình duyệt hoặc ứng dụng Facebook
        String facebookUrl = "https://www.facebook.com/login";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
        startActivity(intent);
    }

    private void loginWithGoogle() {
        // Thực hiện đăng nhập bằng Google
        // Mở trang đăng nhập Google trong trình duyệt hoặc ứng dụng Google
        String googleUrl = "https://accounts.google.com";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleUrl));
        startActivityForResult(intent, REQUEST_CODE_GMAIL_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GMAIL_LOGIN) {
            // Kiểm tra kết quả trả về từ hoạt động đăng nhập Gmail
            if (resultCode == RESULT_OK) {
                // Xử lý đăng nhập thành công
                // Quay trở lại giao diện của ứng dụng
                Intent intent = new Intent(LoginActivity.this, SuccessActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Xử lý đăng nhập không thành công hoặc đã hủy
                // Quay trở lại giao diện đăng nhập
            }
        }
    }
}
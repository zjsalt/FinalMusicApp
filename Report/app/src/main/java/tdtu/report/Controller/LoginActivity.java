package tdtu.report.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import tdtu.report.AppDatabase;
import tdtu.report.Model.User;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_GMAIL_LOGIN = 1;
    private AppDatabase appDatabase;
    private ImageView ivFacebookLogin;
    private ImageView ivGoogleLogin;
    private TextView tvRegister;
    private Button btnLogin;
    private EditText edtEmail;
    private EditText edtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvRegister = findViewById(R.id.tvRegister);
        ivFacebookLogin = findViewById(R.id.ivFacebookLogin);
        ivGoogleLogin = findViewById(R.id.ivGoogleLogin);
        btnLogin = findViewById(R.id.btnLogin);

        // Khởi tạo cơ sở dữ liệu
        appDatabase =AppDatabase.getInstance(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                // Kiểm tra thông tin đăng nhập
                if (isValidCredentials(email, password)) {
                    // Thực hiện đăng nhập thành công
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Hiển thị thông báo lỗi
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
    private boolean isValidCredentials(String email, String password) {
        // Truy vấn cơ sở dữ liệu để kiểm tra thông tin đăng nhập
        User user = appDatabase.userDao().getUserByEmailAndPassword(email, password);
        return user != null;
    }
}
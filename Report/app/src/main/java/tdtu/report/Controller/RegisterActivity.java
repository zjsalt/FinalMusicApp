package tdtu.report.Controller;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import tdtu.report.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import tdtu.report.AppDatabase;
import tdtu.report.Model.User;

public class RegisterActivity extends AppCompatActivity {
    private EditText  etEmail, etPassword, edtConfirmPassword;
    private Button btnRegister;

    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.edtUserName);
        etPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // Khởi tạo cơ sở dữ liệu
        appDatabase = AppDatabase.getInstance(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmpassword = edtConfirmPassword.getText().toString().trim();
                String name = getUserNameFromEmail(email);
                if(password.equals(confirmpassword)){
                    
                }
                // Kiểm tra thông tin đăng ký
                if (isValidRegistration(name,email, password)) {
                    // Thực hiện đăng ký thành công
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                    // Lưu thông tin người dùng vào cơ sở dữ liệu
                    User user = new User(name, email, password);
                    appDatabase.userDao().insert(user);

                    // Chuyển đến màn hình đăng nhập
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Hiển thị thông báo lỗi
                    Toast.makeText(RegisterActivity.this, "Invalid registration details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidRegistration(String name, String email, String password) {
        // Kiểm tra xem người dùng đã tồn tại trong cơ sở dữ liệu chưa
        User user = appDatabase.userDao().getUserByEmail(email);
        if (user != null) {
            Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra các ràng buộc khác (ví dụ: độ dài mật khẩu, định dạng email, v.v.)
        // ...

        return true;
    }
    public String getUserNameFromEmail(String email) {
        int atIndex = email.indexOf("@");
        if (atIndex != -1) {
            String userName = email.substring(0, atIndex);
            return userName;
        } else {
            // Không tìm thấy dấu @ trong email
            return "";
        }
    }
}
package tdtu.report.Controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import tdtu.report.R;

public class SuccessActivity extends AppCompatActivity {

    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        btnLogout = findViewById(R.id.btnLogout);

        // Xử lý sự kiện đăng xuất
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        // Lấy thông tin người dùng từ intent hoặc SharedPreferences
        String username = "John Doe"; // Thay thế bằng thông tin người dùng thực tế

        // Hiển thị thông tin người dùng
        TextView textViewUsername = findViewById(R.id.textViewUsername);
        textViewUsername.setText(username);
    }

    private void logout() {
        // Thực hiện quá trình đăng xuất
        // Ví dụ: Xóa token, xóa dữ liệu đăng nhập, chuyển về màn hình đăng nhập, vv.

        // Kết thúc hoạt động hiện tại và quay trở lại màn hình đăng nhập
        finish();
    }
}
package tdtu.report.Controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import tdtu.report.AppDatabase;
import tdtu.report.Dao.UserDao;
import tdtu.report.Model.User;
import tdtu.report.R;

public class ConfirmationActivity extends AppCompatActivity {
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        // Khởi tạo cơ sở dữ liệu
        appDatabase = AppDatabase.getInstance(getApplicationContext());

        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        EditText etConfirmationCode = findViewById(R.id.etConfirmationCode);
        Button btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredCode = etConfirmationCode.getText().toString().trim();
                String correctCode = getIntent().getStringExtra("confirmationCode");

                if (enteredCode.equals(correctCode)) {
                    // Tạo đối tượng User mới
                    User newUser = new User(name, email, password);
                    // Lưu thông tin người dùng vào cơ sở dữ liệu
                    UserDao userDao = appDatabase.userDao();
                    userDao.insert(newUser);

                    // Correct code, navigate to MainActivity
                    Intent mainIntent = new Intent(ConfirmationActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    setResult(RESULT_OK);  // Set result as OK
                    finish();
                } else {
                    Toast.makeText(ConfirmationActivity.this, "Incorrect code. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

package tdtu.report.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import tdtu.report.AppDatabase;
import tdtu.report.Dao.UserDao;
import tdtu.report.R;
import tdtu.report.ViewModel.LoginViewModel;
import tdtu.report.ViewModel.LoginViewModelFactory;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.edtUserName);
        etPassword = findViewById(R.id.edtPassword);

        AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
        UserDao userDao = appDatabase.userDao();

        LoginViewModelFactory factory = new LoginViewModelFactory(getApplication(), userDao);
        LoginViewModel loginViewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            loginViewModel.loginUser(email, password);
        });

        loginViewModel.getLoginResult().observe(this, isSuccess -> {
            if (isSuccess) {
                // Login is successful, navigate to HomeActivity
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Optionally, finish LoginActivity to prevent going back
            } else {
                // Login failed, show an error message
                Toast.makeText(LoginActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

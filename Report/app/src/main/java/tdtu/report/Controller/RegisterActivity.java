package tdtu.report.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import tdtu.report.R;
import tdtu.report.ViewModel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel registerViewModel;
    private EditText etEmail, etPassword, edtConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.edtUserName);
        etPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);

        // Use ViewModelProvider.AndroidViewModelFactory.getInstance for AndroidViewModel
        registerViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(RegisterViewModel.class);
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = edtConfirmPassword.getText().toString().trim();
            String name = getUserNameFromEmail(email);

            if (password.equals(confirmPassword) && password.length() > 6) {
                registerViewModel.registerUser(name, email, password, confirmPassword);
            } else {
                // Password validation failed
                Toast.makeText(RegisterActivity.this, "Password validation failed", Toast.LENGTH_SHORT).show();
            }
        });

        registerViewModel.getRegistrationResult().observe(this, isSuccess -> {
            if (isSuccess) {
                // Registration is successful
                // Handle success scenario, e.g., navigate to the next screen
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                // Email already exists or other registration failure
                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getUserNameFromEmail(String email) {
        int atIndex = email.indexOf("@");
        if (atIndex != -1 && atIndex < email.length() - 1) {
            return email.substring(0, atIndex);
        }
        return null;
    }

}


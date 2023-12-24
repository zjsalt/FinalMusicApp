package tdtu.report.Controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;
import java.util.Random;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import tdtu.report.AppDatabase;
import tdtu.report.Dao.UserDao;
import tdtu.report.Model.MailSender;
import tdtu.report.Model.User;
import tdtu.report.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText etEmail, etPassword, edtConfirmPassword;
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
        appDatabase = AppDatabase.getInstance(getApplicationContext());

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = edtConfirmPassword.getText().toString().trim();
                String name = getUserNameFromEmail(email);
                new RegistrationTask(name, email, password, confirmPassword).execute();

            }
        });
    }

    private String getUserNameFromEmail(String email) {
        int atIndex = email.indexOf("@");
        if (atIndex != -1) {
            return email.substring(0, atIndex);
        }
        return null;
    }

    private boolean isValidRegistration(String name, String email, String password, String confirmPassword) {
        // Kiểm tra xem người dùng đã tồn tại trong cơ sở dữ liệu chưa
        User user = appDatabase.userDao().getUserByEmail(email);
        if (user != null) {
            Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra các ràng buộc khác (ví dụ: độ dài mật khẩu, định dạng email, v.v.)
        // ...

        // Kiểm tra mật khẩu và xác nhận mật khẩu
        if (!password.equals(confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private String generateConfirmationCode() {
        // Generate a random confirmation code (this is just an example, you may use your logic)
        Random random = new Random();
        int confirmationCode = 1000 + random.nextInt(9000);
        return String.valueOf(confirmationCode);
    }

    private void showConfirmationScreen(String confirmationCode) {
        // After successful registration, navigate to the SuccessActivity and pass the confirmation code
        Intent intent = new Intent(RegisterActivity.this, ConfirmationActivity.class);
        intent.putExtra("confirmationCode", confirmationCode);
        startActivity(intent);
        finish(); // Optionally, you can finish the RegisterActivity to prevent going back
    }

    private class RegistrationTask extends AsyncTask<Void, Void, Boolean> {
        private final String name;
        private final String email;
        private final String password;
        private final String confirmPassword;

        RegistrationTask(String name, String email, String password, String confirmPassword) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.confirmPassword = confirmPassword;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Perform database operations on a background thread
            UserDao userDao = appDatabase.userDao();
            User user = userDao.getUserByEmail(email);
            return user == null && isValidRegistration(name, email, password, confirmPassword);
        }

        @Override
        protected void onPostExecute(Boolean isValid) {
            // Handle the result on the main thread
            if (isValid) {
                // Continue with registration
                new SendEmailTask(name, email, password, confirmPassword).execute();
            } else {
                Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class SendEmailTask extends AsyncTask<Void, Void, Void> {
        private final String name;
        private final String email;
        private final String password;
        private final String confirmPassword;

        SendEmailTask(String name, String email, String password, String confirmPassword) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.confirmPassword = confirmPassword;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Send email in the background
            try {
                // Gửi email xác nhận đến địa chỉ email của người dùng
                String confirmationCode = generateConfirmationCode();

                // Sử dụng Properties để cấu hình
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");

                // Tạo Session với thông tin xác thực
                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("your-email@gmail.com", "your-app-password");
                    }
                });

                // Tạo đối tượng MailSender và gửi email
                MailSender mailSender = new MailSender(session);
                mailSender.sendMail("Confirmation Code", "Your confirmation code is: " + confirmationCode, "your-email@gmail.com", email);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        // ...
    }
}
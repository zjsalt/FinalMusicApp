package tdtu.report.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import tdtu.report.AppDatabase;
import tdtu.report.Dao.UserDao;
import tdtu.report.Model.User;
public class RegisterViewModel extends AndroidViewModel {
    private UserDao userDao;
    private MutableLiveData<Boolean> registrationResult = new MutableLiveData<>();


    public RegisterViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao();
    }

    public MutableLiveData<Boolean> getRegistrationResult() {
        return registrationResult;
    }

    public void registerUser(String name, String email, String password, String confirmPassword) {
        new RegisterAsyncTask(userDao, registrationResult).execute(new User(name, email, password));

        // For password confirmation and length validation, you can do it here before calling AsyncTask
        // Example: if (password.equals(confirmPassword) && password.length() > 6) { ... }
    }

    private static class RegisterAsyncTask extends AsyncTask<User, Void, Boolean> {
        private UserDao userDao;
        private MutableLiveData<Boolean> registrationResult;

        RegisterAsyncTask(UserDao userDao, MutableLiveData<Boolean> registrationResult) {
            this.userDao = userDao;
            this.registrationResult = registrationResult;
        }

        @Override
        protected Boolean doInBackground(User... users) {
            if (userDao == null) {
                Log.d("RegisterViewModel:", "userDao is null");
                return false;
            }

            User user = userDao.getUserByEmail(users[0].getEmail());

            // Check if the email already exists
            if (user != null) {
                Log.d("RegisterViewModel:", "user is exists");
                return false; // Email already exists
            }

            // Insert the user into the database
            userDao.insert(users[0]);
            return true; // Registration successful
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            registrationResult.setValue(isSuccess);
        }
    }
}


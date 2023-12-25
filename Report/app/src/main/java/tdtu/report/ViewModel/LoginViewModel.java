package tdtu.report.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import tdtu.report.AppDatabase;
import tdtu.report.Dao.UserDao;
import tdtu.report.Model.User;

public class LoginViewModel extends ViewModel {
    private UserDao userDao;
    private MutableLiveData<Boolean> loginResult = new MutableLiveData<>();
    private MutableLiveData<User> loggedInUser = new MutableLiveData<>();
    public LoginViewModel() {
        super();
    }
    public LoginViewModel(@NonNull Application application, UserDao userDao) {
        super();
        this.userDao = userDao;
    }

    public LoginViewModel(@NonNull Application application) {
        super();
        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao();
    }


    public MutableLiveData<Boolean> getLoginResult() {
        return loginResult;
    }

    public MutableLiveData<User> getLoggedInUser() {
        return loggedInUser;
    }

    public void loginUser(String email, String password) {
        new LoginAsyncTask(userDao, loginResult, loggedInUser).execute(email, password);
    }

    private static class LoginAsyncTask extends AsyncTask<String, Void, User> {
        private UserDao userDao;
        private MutableLiveData<Boolean> loginResult;
        private MutableLiveData<User> loggedInUser;

        LoginAsyncTask(UserDao userDao, MutableLiveData<Boolean> loginResult, MutableLiveData<User> loggedInUser) {
            this.userDao = userDao;
            this.loginResult = loginResult;
            this.loggedInUser = loggedInUser;
        }

        @Override
        protected User doInBackground(String... credentials) {
            String email = credentials[0];
            String password = credentials[1];

            // Retrieve user by email and password
            User user = userDao.getUserByEmailAndPassword(email, password);

            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            if (user != null) {
                // Login successful
                loginResult.setValue(true);
                loggedInUser.setValue(user);
            } else {
                // Login failed
                loginResult.setValue(false);
            }
        }
    }
}

package tdtu.report.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import tdtu.report.Database.AppDatabase;
import tdtu.report.Dao.UserDao;
import tdtu.report.Model.User;

public class RegisterViewModel extends AndroidViewModel {
    private UserDao userDao;
    private MutableLiveData<Boolean> registrationResult = new MutableLiveData<>();
    private User[] users;

    public RegisterViewModel(Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        userDao = db.userDao();
    }

    public MutableLiveData<Boolean> getRegistrationResult() {
        return registrationResult;
    }

    public void registerUser(String name, String email, String password, String confirmPassword) {
        users = new User[]{new User(name, email, password)};
        new RegisterAsyncTask(userDao, registrationResult, users).execute(users);
    }
    private static class RegisterAsyncTask extends AsyncTask<User, Void, Boolean> {
        private UserDao userDao;
        private MutableLiveData<Boolean> registrationResult;
        private User[] users;

        RegisterAsyncTask(UserDao userDao, MutableLiveData<Boolean> registrationResult, User[] users) {
            this.userDao = userDao;
            this.registrationResult = registrationResult;
            this.users = users;
        }

        @Override
        protected Boolean doInBackground(User... users) {
            if (userDao == null || users.length == 0) {
                Log.d("RegisterViewModel:", "userDao is null or empty users array");
                return false;
            }

            // Check if the user already exists
            LiveData<User> userLiveData = userDao.getUserByEmail(users[0].getEmail());
            User user = userLiveData.getValue();
            return user == null;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            if (isSuccess) {
                // Insert the user into the database in a background thread
                new InsertUserAsyncTask(userDao, registrationResult).execute(users);
            } else {
                registrationResult.setValue(false);
            }
        }
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;
        private MutableLiveData<Boolean> registrationResult;

        InsertUserAsyncTask(UserDao userDao, MutableLiveData<Boolean> registrationResult) {
            this.userDao = userDao;
            this.registrationResult = registrationResult;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            registrationResult.setValue(true);
        }
    }


//    private static class RegisterAsyncTask extends AsyncTask<User, Void, Boolean> {
//        private UserDao userDao;
//        private MutableLiveData<Boolean> registrationResult;
//        private User[] users;
//
//        RegisterAsyncTask(UserDao userDao, MutableLiveData<Boolean> registrationResult, User[] users) {
//            this.userDao = userDao;
//            this.registrationResult = registrationResult;
//            this.users = users;
//        }
//
//        @Override
//        protected Boolean doInBackground(User... users) {
//            if (userDao == null || users.length == 0) {
//                Log.d("RegisterViewModel:", "userDao is null or empty users array");
//                return false;
//            }
//
//            // Check if the user already exists
//            LiveData<User> userLiveData = userDao.getUserByEmail(users[0].getEmail());
//            User user = userLiveData.getValue();
//            return user == null;
//        }
//
//        @Override
//        protected void onPostExecute(Boolean isSuccess) {
//            if (isSuccess) {
//                // Insert the user into the database
//                userDao.insert(users[0]);
//            }
//            registrationResult.setValue(isSuccess);
//        }
//    }
}

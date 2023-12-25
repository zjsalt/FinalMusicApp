package tdtu.report.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tdtu.report.AppDatabase;
import tdtu.report.Dao.UserDao;
import tdtu.report.Model.User;

public class UserRepository {

    private UserDao userDao;
    private ExecutorService executorService;

    public UserRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        userDao = database.userDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(User user) {
        executorService.execute(() -> userDao.insert(user));
    }

    public boolean doesEmailExist(String email) {
        return userDao.getUserByEmail(email) != null;
    }

    // Other database operations related to User entity

    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }
}

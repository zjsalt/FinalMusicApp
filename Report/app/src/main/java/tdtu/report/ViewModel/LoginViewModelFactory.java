package tdtu.report.ViewModel;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import tdtu.report.Dao.UserDao;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private UserDao userDao;

    public LoginViewModelFactory(Application application, UserDao userDao) {
        this.application = application;
        this.userDao = userDao;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(application, userDao);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

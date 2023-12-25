package tdtu.report.Repository;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tdtu.report.Dao.UserDao;
import tdtu.report.Database.AppDatabase;
import tdtu.report.Model.Song;
import tdtu.report.Model.User;

public class UserRepository {

    private UserDao userDao;
    private ExecutorService executorService;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "user_session";
    private static final String KEY_LOGGED_IN_USER_EMAIL = "logged_in_user_email";

    public UserRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        userDao = database.userDao();
        executorService = Executors.newSingleThreadExecutor();
        sharedPreferences = application.getSharedPreferences(PREF_NAME, 0);
    }

    public void insert(User user) {
        executorService.execute(() -> userDao.insert(user));
    }

    public boolean doesEmailExist(String email) {
        return userDao.getUserByEmail(email) != null;
    }

    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }

    // Method to save the email of the currently logged-in user
    public void saveLoggedInUserEmail(String email) {
        sharedPreferences.edit().putString(KEY_LOGGED_IN_USER_EMAIL, email).apply();
    }

    // Method to get the email of the currently logged-in user
    public String getLoggedInUserEmail() {
        return sharedPreferences.getString(KEY_LOGGED_IN_USER_EMAIL, null);
    }

    // Method to add a song to the favorite playlist of the currently logged-in user
    public void addSongToFavoritePlaylist(User loggedInUser, Song songToAdd) {
        executorService.execute(() -> {
            // Check if the user and favorite playlist are not null
            if (loggedInUser != null && loggedInUser.getFavoritePlaylist() != null) {
                // Add the song to the favorite playlist
                loggedInUser.addToFavoritePlaylist(songToAdd);

                // Update the user in the database
                userDao.update(loggedInUser);
            }
        });
    }

    // Method to check if a playlist with a specific name exists for the logged-in user
    public LiveData<Boolean> doesPlaylistExist(String playlistName, String loggedInUserEmail) {
        // Change the return type to LiveData<Boolean>
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();

        // Get the LiveData<User> from userDao
        LiveData<User> loggedInUserLiveData = userDao.getUserByEmail(loggedInUserEmail);

        loggedInUserLiveData.observeForever(new Observer<User>() {
            @Override
            public void onChanged(User loggedInUser) {
                if (loggedInUser != null && loggedInUser.getPlaylists() != null) {
                    // Use the LiveData result to post the result
                    resultLiveData.postValue(loggedInUser.getPlaylists().stream()
                            .anyMatch(playlist -> playlist.getName().equals(playlistName)));
                } else {
                    // Handle the case where the user or playlists are null
                    resultLiveData.postValue(false);
                }
            }
        });

        return resultLiveData;
    }


    public User getUserByEmailAndPassword(String email, String password) {
        return userDao.getUserByEmailAndPassword(email,password);
    }

    public LiveData<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

}

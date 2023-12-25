package tdtu.report.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import tdtu.report.Model.Song;
import tdtu.report.Model.User;
import tdtu.report.Repository.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<List<User>> allUsers;

    // MutableLiveData for observing the authentication state
    private MutableLiveData<Boolean> isAuthenticated = new MutableLiveData<>();

    // MutableLiveData for observing whether a playlist exists
    private MutableLiveData<Boolean> doesPlaylistExist = new MutableLiveData<>();

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
        allUsers = userRepository.getAllUsers();
        isAuthenticated.setValue(false); // Initial state is not authenticated
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public LiveData<Boolean> getIsAuthenticated() {
        return isAuthenticated;
    }

    public LiveData<Boolean> getDoesPlaylistExist() {
        return doesPlaylistExist;
    }

    // Method to authenticate the user
    public void authenticateUser(String email, String password) {
        // Perform authentication logic (check email and password against stored data)
        User authenticatedUser = userRepository.getUserByEmailAndPassword(email, password);

        if (authenticatedUser != null) {
            // Save the email of the authenticated user
            userRepository.saveLoggedInUserEmail(email);
            isAuthenticated.setValue(true);
        } else {
            isAuthenticated.setValue(false);
        }
    }

    // Method to check if a playlist exists for the logged-in user
    // Method to check if a playlist exists for the logged-in user
    public void checkPlaylistExistence(String playlistName) {
        String loggedInUserEmail = userRepository.getLoggedInUserEmail();

        if (loggedInUserEmail != null) {
            // Check if the playlist exists
            userRepository.doesPlaylistExist(playlistName, loggedInUserEmail)
                    .observeForever(result -> doesPlaylistExist.setValue(result));
        } else {
            Log.e("UserViewModel", "User is not logged in.");
        }
    }


    // Method to add a song to the favorite playlist of the currently logged-in user
    // Method to add a song to the favorite playlist of the currently logged-in user
    public void addSongToFavoritePlaylist(Song songToAdd) {
        String loggedInUserEmail = userRepository.getLoggedInUserEmail();

        if (loggedInUserEmail != null) {
            // Observe the LiveData and update the value
            userRepository.getUserByEmail(loggedInUserEmail)
                    .observeForever(user -> {
                        if (user != null) {
                            userRepository.addSongToFavoritePlaylist(user, songToAdd);
                        }
                    });
        } else {
            Log.e("UserViewModel", "User is not logged in.");
        }
    }

}

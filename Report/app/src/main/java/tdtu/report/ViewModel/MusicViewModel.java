package tdtu.report.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;  // Add this import

import java.util.List;

import tdtu.report.Model.Song;
import tdtu.report.Model.User;
import tdtu.report.Repository.MusicRepository;
import tdtu.report.Repository.UserRepository;

public class MusicViewModel extends AndroidViewModel {

    private LiveData<List<String>> playlist;
    private MusicRepository musicRepository;
    private LiveData<List<Song>> songsLiveData;
    private final MediatorLiveData<User> userLiveData;  // Change this line
    private UserRepository userRepository;

    public MusicViewModel(Application application) {
        super(application);
        musicRepository = new MusicRepository(application);
        userRepository = new UserRepository(application);
        playlist = musicRepository.getAllSongPaths();
        songsLiveData = musicRepository.getAllSongsLiveData();

        // Initialize userLiveData as a MediatorLiveData
        userLiveData = new MediatorLiveData<>();
    }

    public LiveData<List<String>> getPlaylist() {
        return playlist;
    }

    public LiveData<List<Song>> getSongsLiveData() {
        return songsLiveData;
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }
    public LiveData<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public void setUserByEmail(String email) {
        LiveData<User> source = userRepository.getUserByEmail(email);

        // Remove any existing sources
        userLiveData.removeSource(userLiveData);

        // Add the new source
        userLiveData.addSource(source, user -> {
            userLiveData.setValue(user);  // Update the userLiveData when the source changes
        });
    }
}

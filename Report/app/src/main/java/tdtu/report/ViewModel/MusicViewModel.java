package tdtu.report.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import tdtu.report.Model.Song;
import tdtu.report.Repository.MusicRepository;

public class MusicViewModel extends AndroidViewModel {

    private LiveData<List<String>> playlist;
    private MusicRepository musicRepository;
    private LiveData<List<Song>> songsLiveData;
    private LiveData<Song> song;

    public MusicViewModel(Application application) {
        super(application);
        musicRepository = new MusicRepository(application);
        playlist = musicRepository.getAllSongPaths();
        songsLiveData = musicRepository.getAllSongsLiveData();
    }

    public LiveData<List<String>> getPlaylist() {
        return playlist;
    }

    // Add this method to set the playlist
//    public void setPlaylist(List<String> newPlaylist) {
//        musicRepository.setPlaylist(newPlaylist);
//    }

    public LiveData<List<Song>> getSongsLiveData() {
        return songsLiveData;
    }
}

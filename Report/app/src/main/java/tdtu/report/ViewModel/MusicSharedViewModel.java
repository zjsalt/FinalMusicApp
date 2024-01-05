package tdtu.report.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import tdtu.report.Model.Song;

public class MusicSharedViewModel extends ViewModel {

    // LiveData to store the currently playing song information
    private final MutableLiveData<Song> currentSongLiveData = new MutableLiveData<>();

    // LiveData to store the playback state (playing or paused)
    private final MutableLiveData<Boolean> isPlayingLiveData = new MutableLiveData<>();

    // Getter methods for observing these LiveData instances

    public MutableLiveData<Song> getCurrentSongLiveData() {
        return currentSongLiveData;
    }

    public MutableLiveData<Boolean> getIsPlayingLiveData() {
        return isPlayingLiveData;
    }

    // Setter methods to update the values

    public void setCurrentSong(Song song) {
        currentSongLiveData.setValue(song);
    }

    public void setIsPlaying(boolean isPlaying) {
        isPlayingLiveData.setValue(isPlaying);
    }
}

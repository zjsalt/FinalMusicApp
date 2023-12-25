package tdtu.report.ViewModel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import tdtu.report.Model.Song;
import tdtu.report.Repository.MusicRepository;

public class SuggestViewModel extends ViewModel {
    private MusicRepository musicRepository;
    private LiveData<List<Song>> songListLiveData;


    public SuggestViewModel(Application application) {
        musicRepository = new MusicRepository(application);
        songListLiveData = musicRepository.getAllSongsLiveData();
    }

    public SuggestViewModel(MusicRepository musicRepository) {
        musicRepository = musicRepository;
        songListLiveData = musicRepository.getAllSongsLiveData();
    }

    public LiveData<List<Song>> getSongListLiveData() {
        return songListLiveData;
    }
}

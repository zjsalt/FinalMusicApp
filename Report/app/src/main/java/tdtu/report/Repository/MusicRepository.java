package tdtu.report.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import tdtu.report.Database.AppDatabase;
import tdtu.report.Dao.SongDao;
import tdtu.report.Model.Song;

public class MusicRepository {

    private SongDao songDao;

    public MusicRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        songDao = appDatabase.songDao();
    }

    public MusicRepository() {

    }

    public LiveData<List<String>> getAllSongPaths() {
        // Sử dụng Coroutine để thực hiện truy vấn trên background thread
        return songDao.getAllSongPathsLiveData();
    }
    public LiveData<List<Song>> getAllSongsLiveData() {
        return songDao.getAllSongsLiveData();
    }
    public LiveData<Song> getSongByAudioPath(String audioPath) {
        return songDao.getSongByAudioPathLiveData(audioPath);
    }
    public List<Song> getAllSongs() {
        // Truy vấn tất cả bài hát từ cơ sở dữ liệu trên một thread khác
        return songDao.getAllSongs();
    }

    public void setPlaylistForSong(String audioPath, String newPlaylist) {
        // Execute the database operation on a background thread
        new Thread(() -> {
            songDao.setPlaylistForSong(audioPath, newPlaylist);
        }).start();
    }


}


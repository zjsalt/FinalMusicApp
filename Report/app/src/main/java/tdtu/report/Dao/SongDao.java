package tdtu.report.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import tdtu.report.Model.Song;

@Dao
public interface SongDao {
    @Query("SELECT * FROM song")
    List<Song> getAllSongs();

    @Insert
    void insert(Song song);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertAndReturnId(Song song);
    @Update
    void update(Song song);

    @Delete
    void delete(Song song);
    @Insert
    void insertAll(List<Song> songs);

    @Query("SELECT * FROM song WHERE id = :songId")
    Song getSongById(int songId);
    @Query("SELECT * FROM song WHERE albumId = :albumId")
    int getSongsByAlbumId(int albumId);

//    @Query("SELECT audioPath FROM song")
//    List<String> getAllSongPaths();
    @Query("SELECT audioPath FROM song")
    Flowable<List<String>> getAllSongPaths();
    @Query("SELECT audioPath FROM song")
    LiveData<List<String>> getAllSongPathsLiveData();

    @Query("SELECT * FROM song")
    LiveData<List<Song>> getAllSongsLiveData();
    @Query("SELECT * FROM song WHERE audioPath = :audioPath")
    LiveData<Song> getSongByAudioPathLiveData(String audioPath);

    @Query("SELECT * FROM song WHERE audioPath = :audioPath")
    Song getSongByAudioPath(String audioPath);
    @Query("SELECT * FROM song WHERE playlist = :playlist")
    List<Song> getSongsByPlaylist(String playlist);

    @Query("UPDATE song SET playlist = :newPlaylist WHERE audioPath = :audioPath")
    void setPlaylistForSong(String audioPath, String newPlaylist);
    @Query("SELECT title FROM song WHERE audioPath = :audioPath")
    String getSongTitleByAudioPath(String audioPath);

//    @Query("UPDATE song SET playlist = :newPlaylist")
//    void setPlaylist(List<String> newPlaylist);
}

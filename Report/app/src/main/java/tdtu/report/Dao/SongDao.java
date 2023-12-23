package tdtu.report.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import tdtu.report.Model.Album;
import tdtu.report.Model.Song;

import java.util.List;

@Dao
public interface SongDao {
    @Query("SELECT * FROM song")
    List<Song> getAllSongs();

    @Insert
    void insert(Song song);
    @Update
    void update(Song song);

    @Delete
    void delete(Song song);
    @Insert
    void insertAll(List<Song> songs);

    @Query("SELECT * FROM song WHERE album = :album")
    List<Song> getSongsByAlbum(Album album);

    @Query("SELECT audioPath FROM song")
    List<String> getAllSongPaths();
}

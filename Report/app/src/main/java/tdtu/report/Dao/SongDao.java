package tdtu.report.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import tdtu.report.Model.Song;

import java.util.List;

@Dao
public interface SongDao {
    @Query("SELECT * FROM song")
    List<Song> getAllSongs();

    @Insert
    void insert(Song song);

    @Delete
    void delete(Song song);
}

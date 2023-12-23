package tdtu.report.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tdtu.report.Model.Album;
import tdtu.report.Model.Song;

@Dao
public interface AlbumDao {
    @Query("SELECT * FROM album")
    List<Album> getAllAlbums();

    @Query("SELECT * FROM album WHERE id = :albumId")
    Album getAlbumById(int albumId);

    @Insert
    void insert(Album album);

    @Delete
    void delete(Album album);

    @Update
    void update(Album album);
    @Insert
    void insertAll(List<Album> albums);
}
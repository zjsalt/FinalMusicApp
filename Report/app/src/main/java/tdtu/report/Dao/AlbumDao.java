package tdtu.report.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tdtu.report.Model.Album;

@Dao
public interface AlbumDao {
    @Query("SELECT * FROM album_t")
    List<Album> getAllAlbums();

    @Query("SELECT * FROM album_t WHERE id = :albumId")
    Album getAlbumById(int albumId);

    @Insert
    void insert(Album album);

    @Delete
    void delete(Album album);

    @Update
    void update(Album album);
}
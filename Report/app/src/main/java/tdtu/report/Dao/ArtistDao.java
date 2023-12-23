package tdtu.report.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import tdtu.report.Model.Artist;

import java.util.List;

@Dao
public interface ArtistDao {
    @Query("SELECT * FROM artist")
    List<Artist> getAllArtists();

    @Query("SELECT * FROM artist WHERE id = :artistId")
    Artist getArtistById(int artistId);

    @Insert
    void insert(Artist artist);

    @Delete
    void delete(Artist artist);

    @Update
    void update(Artist artist);
    @Insert
    void insertAll(List<Artist> artists);
}
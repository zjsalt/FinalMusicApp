package tdtu.report.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import tdtu.report.Model.Playlist;

import java.util.List;

@Dao
public interface PlaylistDao {
    @Query("SELECT * FROM playlist")
    List<Playlist> getAllPlaylists();
    @Query("SELECT * FROM playlist WHERE id = :playlistId")
    Playlist getPlaylistById(int playlistId);
    @Insert
    void insert(Playlist playlist);

    @Delete
    void delete(Playlist playlist);
}
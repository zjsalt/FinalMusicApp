package tdtu.report.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import tdtu.report.Database.PlaylistSongCrossRef;
import tdtu.report.Model.Playlist;

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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addSongToPlaylist(PlaylistSongCrossRef crossRef);

    @Query("SELECT COUNT(*) FROM playlist_song_cross_ref WHERE playlistId = :playlistId AND audioPath = :audioPath")
    int countSongInPlaylist(String playlistId, String audioPath);
}
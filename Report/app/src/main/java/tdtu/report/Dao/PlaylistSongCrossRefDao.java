package tdtu.report.Dao;

import androidx.room.Dao;
import androidx.room.Insert;

import tdtu.report.Database.PlaylistSongCrossRef;

@Dao
public interface PlaylistSongCrossRefDao {
    @Insert
    void insert(PlaylistSongCrossRef playlistSongCrossRef);
}

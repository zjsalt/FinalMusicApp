package tdtu.report.Database;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(
        tableName = "playlist_song_cross_ref",
        primaryKeys = {"playlistId", "audioPath"}
)
public class PlaylistSongCrossRef {
    @NonNull
    private String playlistId;

    @NonNull
    private String audioPath;

    // Các trường và phương thức getter/setter khác

    public PlaylistSongCrossRef(@NonNull String playlistId, @NonNull String audioPath) {
        this.playlistId = playlistId;
        this.audioPath = audioPath;
    }

    public PlaylistSongCrossRef() {
    }

    @NonNull
    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(@NonNull String playlistId) {
        this.playlistId = playlistId;
    }

    @NonNull
    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(@NonNull String audioPath) {
        this.audioPath = audioPath;
    }
}

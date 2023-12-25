package tdtu.report.Repository;

import tdtu.report.Dao.PlaylistDao;
import tdtu.report.Database.PlaylistSongCrossRef;
import tdtu.report.Model.Song;

public class PlaylistRepository {
    private final PlaylistDao playlistDao;

    public PlaylistRepository(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

    public void addSongToPlaylist(String playlistId, Song newSong) {
        PlaylistSongCrossRef crossRef = new PlaylistSongCrossRef(playlistId, newSong.getAudioPath());

        // Check for duplicates
        if (playlistDao.countSongInPlaylist(playlistId, newSong.getAudioPath()) == 0) {
            // If not a duplicate, add the song to the playlist
            playlistDao.addSongToPlaylist(crossRef);
        }
    }
}

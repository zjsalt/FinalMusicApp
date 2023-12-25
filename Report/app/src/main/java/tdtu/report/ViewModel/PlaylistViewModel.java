package tdtu.report.ViewModel;

import androidx.lifecycle.ViewModel;

import tdtu.report.Model.Song;
import tdtu.report.Repository.PlaylistRepository;

public class PlaylistViewModel extends ViewModel {
    private final PlaylistRepository playlistRepository;

    public PlaylistViewModel(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public void addSongToPlaylist(String playlistId, Song newSong) {
        playlistRepository.addSongToPlaylist(playlistId, newSong);
        // Update LiveData or perform other actions as needed
    }
}

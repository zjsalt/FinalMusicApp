package tdtu.report.Utils;

import java.util.List;

public class PlaylistUtil {
    private List<String> playlist;
    private int currentPosition;

    public PlaylistUtil(List<String> playlist) {
        this.playlist = playlist;
        this.currentPosition = 0;
    }
    public int getCurrentPosition() {
        return currentPosition;
    }
    public String getCurrentSong() {
        if (currentPosition >= 0 && currentPosition < playlist.size()) {
            return playlist.get(currentPosition);
        }
        return null;
    }

    public void setPlaylist(List<String> newPlaylist) {
        this.playlist = newPlaylist;
        currentPosition = 0; // Reset the current position when the playlist changes
    }

    public void playNext() {
        if (currentPosition < playlist.size() - 1) {
            currentPosition++;
        } else {
            currentPosition = 0; // Wrap around to the beginning
        }
    }

    public void playPrevious() {
        if (currentPosition > 0) {
            currentPosition--;
        } else {
            currentPosition = playlist.size() - 1; // Wrap around to the end
        }
    }
}

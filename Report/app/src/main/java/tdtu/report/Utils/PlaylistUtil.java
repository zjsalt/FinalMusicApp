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
            currentPosition = 0; // Loop back to the first song if it's the last song
        }
    }

    public void playPrevious() {
        if (currentPosition > 0) {
            currentPosition--;
        } else {
            currentPosition = playlist.size() - 1; // Play the last song if at the beginning
        }
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int findPositionByAudioPath(String audioPath) {
        for (int i = 0; i < playlist.size(); i++) {
            if (audioPath.equals(playlist.get(i))) {
                return i;
            }
        }
        return -1; // Return -1 if the audioPath is not found in the playlist
    }

    public boolean hasNext() {
        return currentPosition < playlist.size() - 1;
    }

    public void playFirst() {
        currentPosition = 0;
    }
    public void resetCurrentPosition() {
        currentPosition = 0;
    }
}

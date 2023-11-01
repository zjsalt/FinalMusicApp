package tdtu.report.Model;

import java.util.List;

public class Playlist {
    private String name;
    private List<Song> songs;

    public String getName() {
        return name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
    // Constructors, getters, and setters
    // ...
}
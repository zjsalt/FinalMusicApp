package tdtu.report.Model;

import java.util.List;

public class User {
    private String name;
    private String email;
    private String password;
    private List<Playlist> playlists;
    private Playlist favoritePlaylist;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public Playlist getFavoritePlaylist() {
        return favoritePlaylist;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void setFavoritePlaylist(Playlist favoritePlaylist) {
        this.favoritePlaylist = favoritePlaylist;
    }
    // Constructors, getters, and setters
    // ...
}
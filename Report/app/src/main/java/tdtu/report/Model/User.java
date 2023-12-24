package tdtu.report.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;
@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    private String email;
    private String name;

    private String password;
    private List<Playlist> playlists;
    private List<Song> favoritePlaylist;
    public User(){}
    @Ignore
    public User(String name, String email, String password, List<Playlist> playlists, List<Song> favoritePlaylist) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.playlists = playlists;
        this.favoritePlaylist = favoritePlaylist;
    }
    @Ignore
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    @Ignore
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

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

    public List<Song> getFavoritePlaylist() {
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

    public void setFavoritePlaylist(List<Song> favoritePlaylist) {
        this.favoritePlaylist = favoritePlaylist;
    }


    // Constructors, getters, and setters
    // ...
}
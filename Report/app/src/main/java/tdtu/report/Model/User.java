package tdtu.report.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;
@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String email;
    private String password;
    private List<Playlist> playlists;
    private Playlist favoritePlaylist;
    public User(){}
    @Ignore
    public User(String name, String email, String password, List<Playlist> playlists, Playlist favoritePlaylist) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    // Constructors, getters, and setters
    // ...
}
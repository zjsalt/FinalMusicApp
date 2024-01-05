package tdtu.report.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "playlist")
public class Playlist {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private List<Song> songs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() { // Change the return type to String
        return id;
    }

    public void setId(int id) { // Change the parameter type to String
        this.id = id;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}

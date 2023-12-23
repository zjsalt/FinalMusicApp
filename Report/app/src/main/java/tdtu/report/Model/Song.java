package tdtu.report.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "song")
public class Song {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private int idArtist; // Change the data type to String
    private int album; // Change the data type to String
    private String genre;
    private String audioPath;
    private int duration;

    public Song(String title, int idArtist, String audioPath) {
        this.title = title;
        this.idArtist = idArtist;
        this.audioPath = audioPath;
    }

    public String getTitle(){
        return  title;
    }

    public int getIdArtist(){
        return idArtist;
    }

    public int getAlbum(){
        return album;
    }

    public String getGenre(){
        return genre;
    }

    public String getAudioPath(){
        return audioPath;
    }

    public int getDuration(){
        return duration;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setAlbum(int album) {
        this.album = album;
    }

    public void setIdArtist(int idArtist) { // Change the parameter type to String
        this.idArtist = idArtist;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

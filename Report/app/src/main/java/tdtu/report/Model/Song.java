package tdtu.report.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "song")
public class Song {
    @PrimaryKey(autoGenerate = true)
    private  int id;
    private String title;
    private Artist artist;
    private Album album;
    private String genre;
    private String audioPath;
    private int duration;
//    private int image;
//    public Song(int image, String title, String artist){
//        this.image=image;
//        this.title=title;
//        this.artist.setName(artist);
//    }

    public String getTitle(){
        return  title;
    }
    public Artist getArtist(){
        return artist;
    }
    public Album getAlbum(){
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

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
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

//    public int getImage() {
//        return image;
//    }
//
//    public void setImage(int image) {
//        this.image = image;
//    }
}
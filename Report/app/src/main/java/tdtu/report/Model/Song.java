package tdtu.report.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "song")
public class Song {
    @PrimaryKey(autoGenerate = true)
    private  int id;
    private String title;
    private int artistId; // Thay vì Artist object, sử dụng String để lưu trữ artistId
    private int albumId;
    private String genre;
    private String audioPath;
    private int duration;
    private String lyrics;


    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }


    @Ignore
    public Song() {
    }
    @Ignore
    public Song(String title, int artistId, int albumId, String genre, String audioPath, int duration) {
        this.title = title;
        this.artistId = artistId;
        this.albumId = albumId;
        this.genre = genre;
        this.audioPath = audioPath;
        this.duration = duration;
    }
    @Ignore
    public Song(String title, int artistId, String audioPath) {
        this.title = title;
        this.artistId = artistId;
        this.audioPath = audioPath;
    }

    public Song(String title, int artistId, int albumId, String audioPath) {
        this.title = title;
        this.artistId = artistId;
        this.albumId = albumId;
        this.audioPath = audioPath;
    }


    // Các getter và setter khác (đã giữ nguyên)

    // Các phương thức getter và setter cho artistId và albumId
    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getTitle(){
        return  title;
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
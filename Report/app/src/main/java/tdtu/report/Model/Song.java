package tdtu.report.Model;

public class Song {
    private String title;
    private Artist artist;
    private Album album;
    private String genre;
    private String audioPath;
    private int duration;

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

    // Constructors, getters, and setters
    // ...
}
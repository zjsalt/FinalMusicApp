package tdtu.report.Model;

public class Album {
    private String title;
    private Artist artist;
    private int year;
    private String image;

    public String getTitle() {
        return title;
    }

    public Artist getArtist() {
        return artist;
    }

    public String getImage() {
        return image;
    }

    public int getYear() {
        return year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public void setYear(int year) {
        this.year = year;
    }
    // Constructors, getters, and setters
    // ...
}
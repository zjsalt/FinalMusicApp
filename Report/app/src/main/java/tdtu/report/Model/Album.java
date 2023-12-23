package tdtu.report.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "album")
public class Album {
    @PrimaryKey(autoGenerate = true)
    private int id; // Change the data type to String
    private String title;
    private int artistId; // Change the data type to String
    private int year;
    private String image;

    @Ignore
    public Album() {
    }

    @Ignore
    public Album(String title) {
        this.title = title;
    }

    public Album(String title, int artistId) { // Change the parameter type to String
        this.title = title;
        this.artistId = artistId;
    }

    // Uncomment the following constructor if needed
    // @Ignore
    // public Album(String id, String title, String artistId, int year, String image) {
    //     this.id = id;
    //     this.title = title;
    //     this.artistId = artistId;
    //     this.year = year;
    //     this.image = image;
    // }

    // Getters and setters

    public int getId() { // Change the return type to String
        return id;
    }

    public void setId(int id) { // Change the parameter type to String
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getArtistId() { // Change the return type to String
        return artistId;
    }

    public void setArtistId(int artistId) { // Change the parameter type to String
        this.artistId = artistId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

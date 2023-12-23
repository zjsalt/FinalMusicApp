package tdtu.report.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "artist")
public class Artist {
    @PrimaryKey(autoGenerate = true)
    private int id; // Change the data type to String
    private String name;
    private String image;
    private String description;

    @Ignore
    public Artist() {
        // Constructor without parameters
    }

    @Ignore
    public Artist(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    @Ignore
    public Artist(String name) {
        this.name = name;
    }

    @Ignore
    public Artist(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public Artist(int id, String name, String image, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
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

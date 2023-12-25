package tdtu.report.Model;

import android.graphics.drawable.Drawable;

public class CardItem2 {
    private int imageView;
    private  Song song;


    public CardItem2(int imageView, Song song) {
        this.imageView = imageView;
        this.song = song;
    }

    public int getImageView() {
        return imageView;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}

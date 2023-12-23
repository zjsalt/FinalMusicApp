package tdtu.report.Model;

import android.graphics.drawable.Drawable;

public class CardItem2 {
    private int imageView;
    private String title;

    public CardItem2(int imageView, String title) {
        this.imageView = imageView;
        this.title = title;
    }

    public int getImageView() {
        return imageView;
    }

    public String getTitle() {
        return title;
    }
}

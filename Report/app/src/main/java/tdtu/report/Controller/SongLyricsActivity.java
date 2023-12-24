package tdtu.report.Controller;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import tdtu.report.R;

public class SongLyricsActivity extends AppCompatActivity {
    ImageButton moreChoice;
    PopupMenu popupMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_lyrics);

        moreChoice = findViewById(R.id.moreChoice);
        moreChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu = new PopupMenu(SongLyricsActivity.this, moreChoice);

                // Liên kết menu_imagebutton.xml với PopupMenu
                popupMenu.getMenuInflater().inflate(R.menu.menu_song, popupMenu.getMenu());

                // Xử lý sự kiện khi một mục trong menu được chọn
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Xử lý logic khi một mục trong menu được chọn
                        int itemId = item.getItemId();
                        if (itemId == R.id.comment) {
                            // Xử lý khi Menu Item 1 được chọn
                            return true;
                        } else if (itemId == R.id.addToPlaylist) {
                            // Xử lý khi Menu Item 2 được chọn
                            return true;
                        } else if (itemId == R.id.playlistView) {
                            // Xử lý khi Menu Item 3 được chọn
                            return true;
                        } else if (itemId == R.id.downloadSong) {
                            // Xử lý khi Menu Item 4 được chọn
                            return true;
                        } else if (itemId == R.id.share) {
                            // Xử lý khi Menu Item 5 được chọn
                            return true;
                        } else if (itemId == R.id.like) {
                            // Xử lý khi Menu Item 6 được chọn
                            return true;
                        } else {
                            return false;
                        }
                    }
                });

                // Hiển thị PopupMenu
                popupMenu.show();
            }
        });
    }
}

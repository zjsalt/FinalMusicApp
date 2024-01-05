//// LikeActivity.java
//package tdtu.report.Controller;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import tdtu.report.Adapter.LikeAdapter;
//import tdtu.report.Database.AppPreferences;
//import tdtu.report.Model.Song;
//import tdtu.report.Model.User;
//import tdtu.report.R;
//import tdtu.report.Utils.MusicUtil;
//
//// LikeActivity.java
//// Import statements...
//
//public class LikeActivity extends AppCompatActivity {
//
//    private RecyclerView rvLikeSong;
//    private List<Song> datalist;
//    private LikeAdapter adapter;
//    private MusicUtil musicUtil;
//    private AppPreferences appPreferences;
//    private User userLogged;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_like);
//
//
//        rvLikeSong = findViewById(R.id.rvLikeSong);
//        rvLikeSong.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        datalist = new ArrayList<>(); // Initialize an empty list
//        adapter = new LikeAdapter(datalist);
//        rvLikeSong.setAdapter(adapter);
//
//        // Initialize MusicUtil with the current position and song
////        musicUtil = new MusicUtil(this, 0, "", null, null, null, null, null, null, null);
//
//        // Load and show favorite songs
//        loadFavoriteSongs();
//    }
//
//    private void loadFavoriteSongs() {
//        // Get the favorite songs from MusicUtil
//        List<Song> favoriteSongs = musicUtil.getFavoriteSongs();
//
//        // Show the favorite songs in the RecyclerView
//        showFavoriteSongs(favoriteSongs);
//    }
//
//    public void showFavoriteSongs(List<Song> favoriteSongs) {
//        // Update the adapter with the new list of favorite songs
//        datalist.clear();
//        datalist.addAll(favoriteSongs);
//        adapter.notifyDataSetChanged();
//    }
//
//    // Existing methods...
//}

package tdtu.report.Controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tdtu.report.Adapter.LikeAdapter;
import tdtu.report.Dao.SongDao;
import tdtu.report.Database.AppDatabase;
import tdtu.report.Database.AppPreferences;
import tdtu.report.Model.Song;
import tdtu.report.R;
// LikeActivity.java
public class LikeActivity extends AppCompatActivity {

    private RecyclerView rvLikeSong;
    private List<Song> datalist;
    private LikeAdapter adapter;
    private AppPreferences appPreferences;
    private SongDao songDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        rvLikeSong = findViewById(R.id.rvLikeSong);
        rvLikeSong.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        datalist = new ArrayList<>(); // Initialize an empty list
        adapter = new LikeAdapter(datalist);
        rvLikeSong.setAdapter(adapter);

        // Obtain the AppPreferences instance
        appPreferences = AppPreferences.getInstance(getApplicationContext());

        // Obtain the SongDao instance
        songDao = AppDatabase.getInstance().songDao();

        // Load and show favorite songs
        loadFavoriteSongs();
    }

    private void loadFavoriteSongs() {
        // Get the favorite songs from AppPreferences
        appPreferences.getFavoriteSongs(getApplicationContext(), new AppPreferences.AppPreferencesCallback() {
            @Override
            public void onSongsLoaded(List<Song> favoriteSongs) {
                // Show the favorite songs in the RecyclerView
                if (favoriteSongs != null && !favoriteSongs.isEmpty()) {
                    // Use runOnUiThread to update the UI on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            displayFavoriteSongs(favoriteSongs);
                        }
                    });
                }
            }
        });
    }

    // Method to display favorite songs in the RecyclerView
    private void displayFavoriteSongs(List<Song> favoriteSongs) {
        // Clear existing data
        datalist.clear();

        // Add all favorite songs to the list
        datalist.addAll(favoriteSongs);

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();
    }
}

package tdtu.report.Database;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import tdtu.report.Model.Song;
// AppPreferences.java
public class AppPreferences {
    private static final String PREF_NAME = "app_preferences";
    private static final String KEY_LOGGED_IN_USER_EMAIL = "loggedInUserEmail";
    private static final String KEY_FAVORITE_SONGS = "favorite_songs";

    private SharedPreferences preferences;

    private static AppPreferences instance;

    private AppPreferences(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized AppPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new AppPreferences(context);
        }
        return instance;
    }

    public void setLoggedInUserEmail(String email) {
        preferences.edit().putString(KEY_LOGGED_IN_USER_EMAIL, email).apply();
    }

    public String getLoggedInUserEmail() {
        return preferences.getString(KEY_LOGGED_IN_USER_EMAIL, "");
    }

    public void getFavoriteSongs(Context context, AppPreferencesCallback callback) {
        // Get the favorite songs paths in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> favoritePaths = getFavoriteSongsPaths();

                if (favoritePaths != null && !favoritePaths.isEmpty()) {
                    List<Song> favoriteSongs = new ArrayList<>();
                    for (String songPath : favoritePaths) {
                        Song song = AppDatabase.getInstance(context).songDao().getSongByAudioPath(songPath);
                        if (song != null) {
                            favoriteSongs.add(song);
                        }
                    }

                    // Callback to the UI thread with the result
                    callback.onSongsLoaded(favoriteSongs);
                }
            }
        }).start();
    }

    public interface AppPreferencesCallback {
        void onSongsLoaded(List<Song> songs);
    }

    public List<String> getFavoriteSongsPaths() {
        String savedJson = preferences.getString(KEY_FAVORITE_SONGS, null);

        if (savedJson != null) {
            return Arrays.asList(new Gson().fromJson(savedJson, String[].class));
        }

        return null;
    }

//    public void setFavoriteSongs(List<String> favoriteSongsPaths) {
//        SharedPreferences.Editor editor = preferences.edit();
//        String json = new Gson().toJson(favoriteSongsPaths);
//        editor.putString(KEY_FAVORITE_SONGS, json);
//        editor.apply();
//    }
public void setFavoriteSongs(List<String> favoriteSongsPaths) {
    // Get the existing favorite songs paths
    List<String> existingFavoritePaths = getFavoriteSongsPaths();

    // Remove duplicates by creating a HashSet
    HashSet<String> uniquePathsSet = new HashSet<>(existingFavoritePaths);
    uniquePathsSet.addAll(favoriteSongsPaths);

    // Convert the HashSet back to a List
    List<String> uniquePathsList = new ArrayList<>(uniquePathsSet);

    // Save the unique paths to SharedPreferences
    SharedPreferences.Editor editor = preferences.edit();
    String json = new Gson().toJson(uniquePathsList);
    editor.putString(KEY_FAVORITE_SONGS, json);
    editor.apply();
}

}

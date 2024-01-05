package tdtu.report.Controller;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import tdtu.report.Dao.AlbumDao;
import tdtu.report.Dao.ArtistDao;
import tdtu.report.Dao.SongDao;
import tdtu.report.Database.AppDatabase;
import tdtu.report.Database.InsertDatabase;
import tdtu.report.Fragment.HomeFragment;
import tdtu.report.Fragment.PlayMusicFragment;
import tdtu.report.Fragment.ProfileFragment;
import tdtu.report.Fragment.SuggestFragment;
import tdtu.report.Model.Song;
import tdtu.report.R;
import tdtu.report.ViewModel.MusicSharedViewModel;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private SongDao songDao;
    private AlbumDao albumDao;
    private ArtistDao artistDao;
    private AppDatabase appDatabase;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String LAST_SELECTED_POSITION_KEY = "lastSelectedPosition";
    private static final String LAST_SELECTED_AUDIO_PATH_KEY = "lastSelectedAudioPath";

    private MusicSharedViewModel musicSharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        appDatabase = AppDatabase.getInstance(getApplicationContext());
        artistDao = appDatabase.artistDao();
        albumDao = appDatabase.albumDao();
        songDao = appDatabase.songDao();
        musicSharedViewModel = new ViewModelProvider(this).get(MusicSharedViewModel.class);

        // Kiểm tra và cập nhật dữ liệu nếu chưa được thêm
        checkAndInsertData();

        bottomNavigationView = findViewById(R.id.navigation_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.home) {
                selectedFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
                if (selectedFragment == null) {
                    selectedFragment = new HomeFragment();
                }
            } else if (item.getItemId() == R.id.suggest) {
                selectedFragment = getSupportFragmentManager().findFragmentByTag(SuggestFragment.class.getSimpleName());
                if (selectedFragment == null) {
                    selectedFragment = new SuggestFragment();
                }
            } else if (item.getItemId() == R.id.profile) {
                selectedFragment = getSupportFragmentManager().findFragmentByTag(ProfileFragment.class.getSimpleName());
                if (selectedFragment == null) {
                    selectedFragment = new ProfileFragment();
                }
            } else if (item.getItemId() == R.id.music) {
                // Kiểm tra nếu có bài hát được click trước đó thì hiển thị nó
                String lastClickedAudioPath = getLastClickedAudioPath();
                if (!lastClickedAudioPath.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", 0);
                    bundle.putString("audioPath", lastClickedAudioPath);

                    PlayMusicFragment playMusicFragment = new PlayMusicFragment();
                    playMusicFragment.setArguments(bundle);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_menu, playMusicFragment, PlayMusicFragment.class.getSimpleName())
                            .commit();
                } else {
                }
            }

//            else if (item.getItemId() == R.id.music) {
//                loadFirstSongAndNavigate();
//                return true;
////
//            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_menu, selectedFragment, selectedFragment.getClass().getSimpleName())
                        .commit();
            }

            return true;
        });

        // Load HomeFragment by default
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_menu, new HomeFragment(), HomeFragment.class.getSimpleName())
                    .commit();
        }
    }
    private void checkAndInsertData() {
        // Kiểm tra xem dữ liệu đã được thêm chưa
        if (!appDatabase.isDataInitialized()) {
            // Nếu chưa, thì thực hiện thêm dữ liệu
            InsertDatabase insertDatabase = new InsertDatabase(
                    appDatabase.artistDao(),
                    appDatabase.songDao(),
                    appDatabase.albumDao()
            );

            insertDatabase.insertData();

            // Đặt cờ để đánh dấu là dữ liệu đã được thêm vào
            appDatabase.setDataInitialized(true);
        }
    }
    private void loadFirstSongAndNavigate() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                return getFirstAudioPathFromDatabase();
            }

            @Override
            protected void onPostExecute(String firstAudioPath) {
                // Now, you can use firstAudioPath to navigate to PlayMusicFragment or perform any other action
                if (firstAudioPath != null) {
                    navigateToPlayMusicFragment(firstAudioPath);
                } else {
                    // Handle the case where there are no songs in the database
                    Toast.makeText(HomeActivity.this, "No songs found", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private String getFirstAudioPathFromDatabase() {
        AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
        SongDao songDao = appDatabase.songDao();

        Song firstSong = songDao.getFirstSong();

        if (firstSong != null) {
            return firstSong.getAudioPath();
        } else {
            return null;
        }
    }

    private void loadLastPlayedSongAndNavigate() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                // Retrieve the last played audio path from SharedPreferences
                return getLastSelectedAudioPath();
            }

            @Override
            protected void onPostExecute(String lastPlayedAudioPath) {
                if (lastPlayedAudioPath != null && !lastPlayedAudioPath.isEmpty()) {
                    navigateToPlayMusicFragment(lastPlayedAudioPath);
                } else {
                    // Handle the case where there is no last played song
                    Toast.makeText(HomeActivity.this, "No last played song found", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void navigateToPlayMusicFragment(String audioPath) {
        Fragment selectedFragment = getSupportFragmentManager().findFragmentByTag(PlayMusicFragment.class.getSimpleName());
        if (selectedFragment == null) {
            selectedFragment = new PlayMusicFragment();
            Bundle args = new Bundle();
            args.putInt("position", 0); // Assuming you want to start from the first song
            args.putString("audioPath", audioPath);
            selectedFragment.setArguments(args);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_menu, selectedFragment, PlayMusicFragment.class.getSimpleName())
                .commit();
    }
    private int getLastSelectedPosition() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getInt(LAST_SELECTED_POSITION_KEY, 0); // Default to 0 if not found
    }

    // Helper method to retrieve the last selected audio path from SharedPreferences
    private String getLastSelectedAudioPath() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(LAST_SELECTED_AUDIO_PATH_KEY, ""); // Default to empty string if not found
    }
    private String getLastClickedAudioPath() {
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        return prefs.getString("lastClickedAudioPath", "");
    }




}

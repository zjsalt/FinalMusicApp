package tdtu.report.Controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import tdtu.report.AppDatabase;
import tdtu.report.Dao.AlbumDao;
import tdtu.report.Dao.ArtistDao;
import tdtu.report.Dao.SongDao;
import tdtu.report.Fragment.HomeFragment;
import tdtu.report.Fragment.ProfileFragment;
import tdtu.report.Fragment.SuggestFragment;
import tdtu.report.R;
import tdtu.report.Database.InsertDatabase;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private SongDao songDao;
    private AlbumDao albumDao;
    private ArtistDao artistDao;
    private AppDatabase appDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        appDatabase = AppDatabase.getInstance(getApplicationContext());
        artistDao = appDatabase.artistDao();
        albumDao = appDatabase.albumDao();
        songDao = appDatabase.songDao();
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
            }

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



}

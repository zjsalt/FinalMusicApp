package tdtu.report.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import tdtu.report.AppDatabase;
import tdtu.report.Dao.AlbumDao;
import tdtu.report.Dao.ArtistDao;
import tdtu.report.Dao.SongDao;
import tdtu.report.Fragment.HomeFragment;
import tdtu.report.R;
import tdtu.report.Repository.InsertDatabase;

public class MainActivity extends AppCompatActivity {

//    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SongDao songDao;
    private AlbumDao albumDao;
    private ArtistDao artistDao;
    private AppDatabase appDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase =  AppDatabase.getInstance(getApplicationContext());
        songDao = appDatabase.songDao();
        artistDao = appDatabase.artistDao();
        albumDao = appDatabase.albumDao();
        InsertDatabase insertDatabase = new InsertDatabase(artistDao, songDao, albumDao);
        insertDatabase.insertData();

        HomeFragment homeFragment  = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.FirstFragment,homeFragment).commit();
        tabLayout = findViewById(R.id.tabLayout);
        if(tabLayout!=null && tabLayout.getTabCount()>=4){
            TabLayout.Tab tabPersonal = tabLayout.getTabAt(3);
            tabPersonal.select();
            tabPersonal.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Hiển thị giao diện đăng nhập
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }

    }
//    private void iniViewPager(){
//        ViewPager viewPager = findViewById(R.v)
//    }
}

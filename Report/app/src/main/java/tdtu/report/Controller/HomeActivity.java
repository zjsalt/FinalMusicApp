package tdtu.report.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import tdtu.report.Fragment.HomeFragment;
import tdtu.report.Fragment.ProfileFragment;
import tdtu.report.Fragment.SuggestFragment;
import tdtu.report.R;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        bottomNavigationView = findViewById(R.id.navigation_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.home) {
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.suggest) {
                selectedFragment = new SuggestFragment();
            } else if (item.getItemId() == R.id.profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_menu, selectedFragment)
                        .commit();
            }

            return true;
        });

        // Load HomeFragment by default
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_menu, new HomeFragment())
                .commit();
    }

}

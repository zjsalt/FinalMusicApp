package tdtu.report.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import tdtu.report.Adapter.CardPagerAdapter;
import tdtu.report.Fragment.MyFragment;
import tdtu.report.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabLayout);
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
//
//        viewPager = findViewById(R.id.viewPager);
//        tabLayout = findViewById(R.id.tabLayout);
//
//        // Tạo adapter cho ViewPager và đặt adapter cho ViewPager
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(adapter);
//
//        // Kết nối TabLayout với ViewPager
//        tabLayout.setupWithViewPager(viewPager);
//
//        // Xử lý sự kiện khi nhấn vào tab "Cá nhân"
//        TabLayout.Tab tabPersonal = tabLayout.getTabAt(3);
//        tabPersonal.select();
//        tabPersonal.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Hiển thị giao diện đăng nhập
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private class ViewPagerAdapter extends FragmentPagerAdapter {
//
//        public ViewPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            // Trả về fragment tương ứng với vị trí
//            return MyFragment.newInstance(position);
//        }
//
//        @Override
//        public int getCount() {
//            // Số lượng fragment
//            return 4;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            // Tiêu đề của từng tab
//            switch (position) {
//                case 0:
//                    return "Thư viện";
//                case 1:
//                    return "Khám Phá";
//                case 2:
//                    return "My Playlist";
//                case 3:
//                    return "Cá nhân";
//                default:
//                    return "";
//            }
//        }
//    }
//}
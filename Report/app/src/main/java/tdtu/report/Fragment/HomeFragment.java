package tdtu.report.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import tdtu.report.Adapter.HomeAdapter;
import tdtu.report.Model.CardItem2;
import tdtu.report.R;

public class HomeFragment extends Fragment {

    private FrameLayout frameLayout;
    private View rootView;
    private View fragmentHomeView;
    private RecyclerView rvRecently;
    private RecyclerView rvPlaylist;
    private RecyclerView rvAlbum;
    private RecyclerView rvArtist;
    private HomeAdapter adapter;
    private List<CardItem2> dataList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Tìm FrameLayout trong home.xml
        frameLayout = rootView.findViewById(R.id.main_menu);

        // Inflate layout fragment_home.xml và add vào FrameLayout
        fragmentHomeView = inflater.inflate(R.layout.fragment_home, frameLayout, true);

        rvRecently = rootView.findViewById(R.id.rvRecently); // Thay id RecyclerView bằng id thực của bạn trong fragment_home.xml
        rvPlaylist = rootView.findViewById(R.id.rvPlaylist);
        rvAlbum = rootView.findViewById(R.id.rvAlbum);
        rvArtist = rootView.findViewById(R.id.rvArtist);
        rvRecently.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false)); // Hoặc sử dụng GridLayoutManager, StaggeredGridLayoutManager
        dataList = createSampleData(); // Tạo dữ liệu mẫu
        adapter = new HomeAdapter(dataList); // Thay RecyclerViewAdapter bằng tên Adapter của bạn
        rvRecently.setAdapter(adapter);

        return rootView;
    }
    private List<CardItem2> createSampleData() {
        List<CardItem2> data = new ArrayList<>();
        data.add(new CardItem2( R.drawable.avt, "Song 1"));
        data.add(new CardItem2( R.drawable.avt, "Song 2"));
        return data;
    }

    
}
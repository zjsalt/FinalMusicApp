package tdtu.report.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import tdtu.report.Adapter.SuggestAdapter;
import tdtu.report.Model.CardItem2;
import tdtu.report.Model.Song;
import tdtu.report.R;

public class SuggestFragment extends Fragment {

    private FrameLayout frameLayout;
    private View rootView;
    private View fragmentSuggestView;
    private RecyclerView rvSuggestSong;
    private List<Song> datalist;
    private SuggestAdapter adapter;
    public SuggestFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_suggest, container, false);

        // Tìm FrameLayout trong home.xml
        frameLayout = rootView.findViewById(R.id.main_menu);

        // Inflate layout fragment_home.xml và add vào FrameLayout
        fragmentSuggestView = inflater.inflate(R.layout.fragment_suggest, frameLayout, true);

        rvSuggestSong = rootView.findViewById(R.id.rvSuggestSong);
        rvSuggestSong.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        datalist = createSampleData();
        adapter = new SuggestAdapter(datalist);
        rvSuggestSong.setAdapter(adapter);

        return rootView;
    }
    private List<Song> createSampleData() {
        List<Song> data = new ArrayList<>();
//        data.add(new Song( R.drawable.avt, "Song 1", "Artist 1"));
//        data.add(new Song( R.drawable.avt, "Song 2", "Artist 2"));
        return data;
    }
}
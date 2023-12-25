package tdtu.report.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tdtu.report.Adapter.LikeAdapter;
import tdtu.report.Model.Artist;
import tdtu.report.Model.Song;
import tdtu.report.R;

public class LikeFragment extends Fragment {
    private View rootView;
    private RecyclerView rvLikeSong;
    private List<Song> datalist;
    private LikeAdapter adapter;

    public LikeFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_like, container, false);

        rvLikeSong = rootView.findViewById(R.id.rvLikeSong);
        rvLikeSong.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        datalist = new ArrayList<>(); // Initialize an empty list
        adapter = new LikeAdapter(datalist);
        rvLikeSong.setAdapter(adapter);

        return rootView;
    }

    public void showFavoriteSongs(List<Song> favoriteSongs) {
        // Update the adapter with the new list of favorite songs
        datalist.clear();
        datalist.addAll(favoriteSongs);
        adapter.notifyDataSetChanged();
    }

    private List<Song> createSampleData() {
        List<Song> data = new ArrayList<>();
        Artist artist = new Artist("Anh Tú");
        data.add(new Song("Song 1", artist.getId(), "KhoaLyBiet.mp3"));
        data.add(new Song("Song 2", artist.getId(), "Roi Bo.mp3"));
        return data;
    }
}

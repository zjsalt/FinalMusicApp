package tdtu.report.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import tdtu.report.Adapter.AlbumAdapter;
import tdtu.report.Adapter.SuggestAdapter;
import tdtu.report.Model.Artist;
import tdtu.report.Model.Song;
import tdtu.report.R;

public class AlbumFragment extends Fragment {
    private View rootView;
    private RecyclerView rvAlbumSong;
    private List<Song> datalist;
    private AlbumAdapter adapter;

    public AlbumFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_album, container, false);

        rvAlbumSong = rootView.findViewById(R.id.rvAlbumSong);
        rvAlbumSong.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        datalist = createSampleData();
        adapter = new AlbumAdapter(datalist);
        rvAlbumSong.setAdapter(adapter);

        return rootView;
    }
    private List<Song> createSampleData() {
        List<Song> data = new ArrayList<>();
        Artist artist = new Artist("Anh Tú");
        data.add(new Song(  "Song 1", artist.getId(),"KhoaLyBiet.mp3"));
        data.add(new Song(  "Song 2", artist.getId(),"Roi Bo.mp3"));
        return data;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}
package tdtu.report.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tdtu.report.Adapter.PlaylistAdapter;
import tdtu.report.Model.Playlist;
import tdtu.report.R;
import tdtu.report.ViewModel.MusicSharedViewModel;

public class PlaylistFragment extends Fragment {
    private View rootView;
    private RecyclerView rvPlaylistSong;
    private List<Playlist> datalist;
    private PlaylistAdapter adapter;
    private MusicSharedViewModel musicSharedViewModel;


    public PlaylistFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_playlist, container, false);

        rvPlaylistSong = rootView.findViewById(R.id.rvPlaylistSong);
        rvPlaylistSong.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        datalist = createSampleData();
        adapter = new PlaylistAdapter(datalist);
        rvPlaylistSong.setAdapter(adapter);
        musicSharedViewModel = new ViewModelProvider(requireActivity()).get(MusicSharedViewModel.class);


        return rootView;
    }
//    private List<Song> createSampleData() {
//        List<Song> data = new ArrayList<>();
//        Artist artist = new Artist("Anh Tú");
//        data.add(new Song(  "Song 1", artist.getId(),"KhoaLyBiet.mp3"));
//        data.add(new Song(  "Song 2", artist.getId(),"Roi Bo.mp3"));
//        return data;
//    }
}
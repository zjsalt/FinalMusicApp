package tdtu.report.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import tdtu.report.Adapter.DownloadAdapter;
import tdtu.report.Model.Artist;
import tdtu.report.Model.Song;
import tdtu.report.R;

public class DownloadFragment extends Fragment {
    private View rootView;
    private RecyclerView rvDownloadSong;
    private List<Song> datalist;
    private DownloadAdapter adapter;

    public DownloadFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_download, container, false);

        rvDownloadSong = rootView.findViewById(R.id.rvDownloadSong);
        rvDownloadSong.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        datalist = createSampleData();
        adapter = new DownloadAdapter(datalist);
        rvDownloadSong.setAdapter(adapter);

        return rootView;
    }
    private List<Song> createSampleData() {
        List<Song> data = new ArrayList<>();
        Artist artist = new Artist("Anh Tú");
        data.add(new Song(  "Song 1", artist.getId(),"KhoaLyBiet.mp3"));
        data.add(new Song(  "Song 2", artist.getId(),"Roi Bo.mp3"));
        return data;
    }
}
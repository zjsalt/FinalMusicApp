package tdtu.report.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tdtu.report.Adapter.AlbumAdapter;
import tdtu.report.Model.Album;
import tdtu.report.Model.Artist;
import tdtu.report.R;

public class AlbumFragment extends Fragment {
    private View rootView;
    private RecyclerView rvAlbumSong;
    private List<Album> dataList; // Thay đổi từ List<Song> sang List<Album>
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

        dataList = createSampleData(); // Thay đổi từ createSampleData() trả về List<Song> sang List<Album>
        adapter = new AlbumAdapter(dataList); // Thay đổi từ AlbumAdapter(datalist) sang AlbumAdapter(dataList)
        rvAlbumSong.setAdapter(adapter);

        return rootView;
    }

    private List<Album> createSampleData() {
        List<Album> data = new ArrayList<>(); // Thay đổi từ List<Song> sang List<Album>

        // Bạn cần cung cấp thông tin cho Album, ví dụ như ID, tên Album, và các thông tin khác.
        // Dưới đây là một ví dụ đơn giản:
        Artist artist = new Artist("Anh Tú");
        Album album1 = new Album("Album 1", artist.getId());
        Album album2 = new Album("Album 2", artist.getId());

        data.add(album1);
        data.add(album2);

        return data;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}

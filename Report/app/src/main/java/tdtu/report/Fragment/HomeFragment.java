package tdtu.report.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tdtu.report.Adapter.AlbumAdapter;
import tdtu.report.Adapter.HomeAdapter;
import tdtu.report.Adapter.PlaylistAdapter;
import tdtu.report.Controller.LikeActivity;
import tdtu.report.Model.Album;
import tdtu.report.Model.CardItem2;
import tdtu.report.Model.Playlist;
import tdtu.report.R;
import tdtu.report.Utils.HomeUtil;

public class HomeFragment extends Fragment {

    private FrameLayout frameLayout;
    private View rootView;
    private RecyclerView rvRecently;
    private RecyclerView rvPlaylist;
    private RecyclerView rvAlbum;
    private RecyclerView rvArtist;
    private HomeAdapter adapter;
    private LinearLayout BlockLike;
    private HomeUtil homeUtil;
    private LikeFragment likeFragment;
    private FrameLayout fragmentContainer;
    private List<CardItem2> dataList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize other components or variables as needed
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Tìm FrameLayout trong home.xml
        frameLayout = rootView.findViewById(R.id.flHome);
        fragmentContainer = rootView.findViewById(R.id.flHome);
        // Inflate layout fragment_home.xml và add vào FrameLayout
        // Note: This view is not used in the code you provided, consider removing it
        // fragmentHomeView = inflater.inflate(R.layout.fragment_home, frameLayout, true);

        rvRecently = rootView.findViewById(R.id.rvRecently);
        rvPlaylist = rootView.findViewById(R.id.rvPlaylist);
        rvAlbum = rootView.findViewById(R.id.rvAlbum);
        rvArtist = rootView.findViewById(R.id.rvArtist);

        rvRecently.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeAdapter(dataList);
        rvRecently.setAdapter(adapter);
        BlockLike = rootView.findViewById(R.id.BlockLike);

        likeFragment = new LikeFragment();

        // Khởi tạo HomeUtil
        homeUtil = new HomeUtil(requireContext(), likeFragment, fragmentContainer);

        // Thiết lập fragmentContainer cho HomeFragment
        homeUtil.setFragmentContainer(fragmentContainer);
        if(likeFragment == null){
            Log.d("HomeFragment","likefragment ==null" );

        }
        if(fragmentContainer == null){
            Log.d("HomeFragment","fragmentContainer ==null" );

        }
        CardView cvLikedSong = rootView.findViewById(R.id.cvLikedSong);
        // Assuming BlockLike is a LinearLayout or any clickable view in your layout
        BlockLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang LikeFragment khi click vào BlockLike
//                if (homeUtil != null) {
//                    homeUtil.showLikeFragment();
//                    Log.d("HomeFragment","homeUtil != null");
//                }
                Intent intent = new Intent(getActivity(), LikeActivity.class);
                startActivity(intent);
            }
        });


        // Load and show playlists, albums, and artists in the background
        new LoadDataAsyncTask().execute();

        return rootView;
    }

    private class LoadDataAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            // Load and show playlists
            loadAndShowPlaylists();

            // Load and show albums
            loadAndShowAlbums();

            // Load and show artists
            // Uncomment the line below if you want to load and show artists
            // loadAndShowArtists();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Update UI or perform any additional tasks after loading data
        }
    }

    private void loadAndShowPlaylists() {
        List<Playlist> playlists = homeUtil.getPlaylistsFromDatabase();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rvPlaylist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                PlaylistAdapter playlistAdapter = new PlaylistAdapter(playlists);
                rvPlaylist.setAdapter(playlistAdapter);
            }
        });
    }

    private void loadAndShowAlbums() {
        List<Album> albums = homeUtil.getAlbumsFromDatabase();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rvAlbum.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                AlbumAdapter albumAdapter = new AlbumAdapter(albums);
                rvAlbum.setAdapter(albumAdapter);
            }
        });
    }

    // Uncomment the method below if you want to load and show artists
    /*
    private void loadAndShowArtists() {
        List<Artist> artists = homeUtil.getArtistsFromDatabase();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rvArtist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                ArtistAdapter artistAdapter = new ArtistAdapter(artists);
                rvArtist.setAdapter(artistAdapter);
            }
        });
    }
    */
}

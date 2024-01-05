package tdtu.report.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import tdtu.report.Adapter.SuggestAdapter;
import tdtu.report.Model.Song;
import tdtu.report.R;
import tdtu.report.Repository.MusicRepository;
import tdtu.report.ViewModel.SuggestViewModel;
import tdtu.report.ViewModel.SuggestViewModelFactory;

public class SuggestFragment extends Fragment {

    private SuggestViewModel suggestViewModel;
    private RecyclerView rvSuggestSong, rvList;
    private SuggestAdapter adapter;
    private View rootView;
    private List<Song> uniqueSongList;
    private SearchView searchView;
    private ImageButton ibSearch;
    private TextView tvSuggest, tmp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_suggest, container, false);

        rvSuggestSong = rootView.findViewById(R.id.rvSuggestSong);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvSuggestSong.setLayoutManager(layoutManager);

        adapter = new SuggestAdapter(new ArrayList<>(), new SuggestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Song song) {
                if (song != null) {
                    // Handle item click if needed
                    // For example, you can navigate to PlayMusicFragment here
                    saveLastClickedSong(song.getAudioPath());
//                    musicSharedViewModel.setSelectedSongPath(song.getAudioPath());

                    navigateToPlayMusicFragment(song.getAudioPath());
                } else {
                    Toast.makeText(getContext(), "Bài hát là null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemClickedForPlayMusic(Song song) {
                // Do something when clicking on an item to play music in PlayMusicFragment
                navigateToPlayMusicFragment(song.getAudioPath());
            }
        });

        rvSuggestSong.setAdapter(adapter);

        MusicRepository musicRepository = new MusicRepository(requireActivity().getApplication());
        suggestViewModel = new ViewModelProvider(this, new SuggestViewModelFactory(musicRepository)).get(SuggestViewModel.class);

        suggestViewModel.getSongListLiveData().observe(getViewLifecycleOwner(), songList -> {
            if (songList != null && !songList.isEmpty()) {
                uniqueSongList = removeDuplicateSongs(songList);
                adapter.setDataList(uniqueSongList);
            } else {
                Toast.makeText(getContext(), "Empty", Toast.LENGTH_SHORT).show();
            }
        });

        rvSuggestSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ẩn SearchView và RecyclerView
                searchView.setVisibility(View.GONE);
                rvList.setVisibility(View.GONE);

                ibSearch.setVisibility(View.VISIBLE);
                tmp.setVisibility(View.VISIBLE);
                tvSuggest.setVisibility(View.VISIBLE);
            }
        });

        searchView = rootView.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });


        return rootView;
    }

    private List<Song> removeDuplicateSongs(List<Song> songList) {
        Set<Song> uniqueSongs = new LinkedHashSet<>(songList);
        return new ArrayList<>(uniqueSongs);
    }

    private void saveLastClickedSong(String audioPath) {
        SharedPreferences prefs = requireContext().getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastClickedAudioPath", audioPath);
        editor.apply();
    }
    private void navigateToPlayMusicFragment(String audioPath) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", 0); // Assuming you want to start from the first song
        bundle.putString("audioPath", audioPath);

        PlayMusicFragment playMusicFragment = new PlayMusicFragment();
        playMusicFragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_menu, playMusicFragment, PlayMusicFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        suggestViewModel.getSongListLiveData().removeObservers(getViewLifecycleOwner());
    }

    private void filterList(String text) {
        List<Song> filteredList = new ArrayList<>();
        for(Song song: uniqueSongList){
            if(song.getTitle().toLowerCase().contains((text.toLowerCase()))){
                filteredList.add((song));
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this.getContext(), "Không tìm thấy bài hát", Toast.LENGTH_SHORT).show();
        }else{
            adapter.setFilteredList(filteredList);
        }
    }
}



package tdtu.report.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import tdtu.report.Adapter.SuggestAdapter;
import tdtu.report.Controller.PlayMusicActivity;
import tdtu.report.Model.Song;
import tdtu.report.R;
import tdtu.report.Repository.MusicRepository;
import tdtu.report.ViewModel.SuggestViewModel;
import tdtu.report.ViewModel.SuggestViewModelFactory;

public class SuggestFragment extends Fragment {

    private SuggestViewModel suggestViewModel;
    private RecyclerView rvSuggestSong;
    private SuggestAdapter adapter;
    private FrameLayout frameLayout;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_suggest, container, false);

        rvSuggestSong = rootView.findViewById(R.id.rvSuggestSong);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvSuggestSong.setLayoutManager(layoutManager);

        // Khởi tạo Adapter (không cần dữ liệu ban đầu)
        adapter = new SuggestAdapter(new ArrayList<>(), new SuggestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Song song) {
                if (song != null) {
                    String audioPath = song.getAudioPath();
                    Intent intent = new Intent(getActivity(), PlayMusicActivity.class);
                    intent.putExtra("audioPath", audioPath);
                    intent.putExtra("position", position); // Thêm vị trí vào Intent
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Bài hát là null", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set Adapter cho RecyclerView
        rvSuggestSong.setAdapter(adapter);

        // Khởi tạo MusicRepository và SuggestViewModel
        MusicRepository musicRepository = new MusicRepository(requireActivity().getApplication());
        suggestViewModel = new ViewModelProvider(this, new SuggestViewModelFactory(musicRepository)).get(SuggestViewModel.class);

        // Quan sát LiveData và cập nhật dữ liệu cho Adapter
        suggestViewModel.getSongListLiveData().observe(getViewLifecycleOwner(), songList -> {
            if (songList != null && !songList.isEmpty()) {
                // Kiểm tra và xử lý trùng lặp nếu cần
                List<Song> uniqueSongList = removeDuplicateSongs(songList);

                // Cập nhật dữ liệu cho Adapter
                adapter.setDataList(uniqueSongList);
            } else {
                Toast.makeText(getContext(), "Empty", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    // Hàm loại bỏ các bài hát trùng lặp
    private List<Song> removeDuplicateSongs(List<Song> songList) {
        Set<Song> uniqueSongs = new LinkedHashSet<>(songList);
        return new ArrayList<>(uniqueSongs);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        suggestViewModel.getSongListLiveData().removeObservers(getViewLifecycleOwner());
    }
}

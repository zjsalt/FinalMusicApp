package tdtu.report.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import tdtu.report.Adapter.SuggestAdapter;
import tdtu.report.ClickListener.RecyclerItemClickListener;
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
    private View fragmentSuggestView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Khởi tạo MusicRepository ở đây
        MusicRepository musicRepository = new MusicRepository(requireActivity().getApplication());

        // Sử dụng ViewModelProvider để tạo SuggestViewModel và truyền MusicRepository vào đó
        suggestViewModel = new ViewModelProvider(this, new SuggestViewModelFactory(musicRepository))
                .get(SuggestViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_suggest, container, false);

        rvSuggestSong = rootView.findViewById(R.id.rvSuggestSong); // Replace with your actual RecyclerView ID
        rvSuggestSong.setLayoutManager(new LinearLayoutManager(getContext())); // Use an appropriate layout manager

        getViewLifecycleOwner().getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            public void onStart() {
                suggestViewModel.getSongListLiveData().observe(getViewLifecycleOwner(), songList -> {
                    if (rvSuggestSong != null) {
                        if (songList != null && !songList.isEmpty()) {
                            // Kiểm tra và xử lý trùng lặp nếu cần
                            List<Song> uniqueSongList = removeDuplicateSongs(songList);

                            // Tạo Adapter và gán cho RecyclerView
                            adapter = new SuggestAdapter(uniqueSongList);
                            rvSuggestSong.setAdapter(adapter);

                            // Bắt sự kiện khi người dùng click vào một item
                            rvSuggestSong.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rvSuggestSong,
                                    new RecyclerItemClickListener.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, int position) {
                                            // Lấy đối tượng Song tại vị trí được click
                                            Song selectedSong = adapter.getSongAtPosition(position);

                                            // Kiểm tra xem selectedSong có tồn tại không và lấy audioPath
                                            if (selectedSong != null) {
                                                String audioPath = selectedSong.getAudioPath();

                                                // Chuyển đến PlayMusicActivity và truyền audioPath
                                                Intent intent = new Intent(getActivity(), PlayMusicActivity.class);
                                                intent.putExtra("audioPath", audioPath);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(getContext(), "Song is null", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onLongItemClick(View view, int position) {
                                            // Xử lý sự kiện long click nếu cần
                                        }
                                    }));
                        } else {
                            Toast.makeText(getContext(), "Empty", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle the case where rvSuggestSong is null
                        Log.e("SuggestFragment", "RecyclerView is null");
                    }
                });
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            public void onStop() {
                suggestViewModel.getSongListLiveData().removeObservers(getViewLifecycleOwner());
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


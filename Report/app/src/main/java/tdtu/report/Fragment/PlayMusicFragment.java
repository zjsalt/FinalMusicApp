package tdtu.report.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import tdtu.report.Database.AppPreferences;
import tdtu.report.R;
import tdtu.report.Utils.MusicUtil;
import tdtu.report.Utils.PlaylistUtil;
import tdtu.report.ViewModel.MusicViewModel;

public class PlayMusicFragment extends Fragment {

    private MusicUtil musicUtil;
    private MusicViewModel musicViewModel;
    private PlaylistUtil playlistUtil;
    private ImageButton ibPlaySong, ibPreSong, ibPosSong, down, ibLikeSong, ibRandomSong;
    private AppPreferences appPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.play_music, container, false);

        // Initialize UI elements and set up event listeners
        ibPlaySong = view.findViewById(R.id.ibPlaySong);
        ibPreSong = view.findViewById(R.id.ibPreSong);
        ibPosSong = view.findViewById(R.id.ibPosSong);
        down = view.findViewById(R.id.ibDownloadSong);
        ibLikeSong = view.findViewById(R.id.ibLikeSong);
        ibRandomSong = view.findViewById(R.id.ibRandomSong);
        SeekBar seekBar = view.findViewById(R.id.seekBar);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView start = view.findViewById(R.id.start);
        TextView end = view.findViewById(R.id.end);

        musicViewModel = new ViewModelProvider(this).get(MusicViewModel.class);

        // Trong PlayMusicFragment
        int position = requireActivity().getIntent().getIntExtra("position", -1);
        String path = requireActivity().getIntent().getStringExtra("audioPath");

        Intent intent = requireActivity().getIntent();
        if (intent != null && intent.hasExtra("audioPath")) {
            String audioPath = intent.getStringExtra("audioPath");
            // Observe changes in the playlist LiveData and update PlaylistUtil accordingly
            musicViewModel.getPlaylist().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> result) {
                    if (result != null) {
                        playlistUtil = new PlaylistUtil(result);

                        // Set the current song in PlaylistUtil to the selected song
                        playlistUtil.playNext();
                        playlistUtil.playPrevious();

                        // Create a new instance of MusicUtil and pass the PlaylistUtil
                        musicUtil = new MusicUtil(requireContext(), position, audioPath, playlistUtil, ibPlaySong, tvTitle, seekBar, start, end, ibLikeSong, musicViewModel);
                        musicUtil.playMusic();

                        // Update the TextViews from SeekBar
                        MusicUtil.updateTextViewsFromSeekBar(seekBar, start, end);
                    } else {
                        // Handle the case where the playlist is null
                        // You may want to show an error message or take appropriate action
                        Toast.makeText(requireContext(), "Playlist is null", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        ibPlaySong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicUtil != null) {
                    if (musicUtil.isPlaying()) {
                        musicUtil.pauseMusic();
                    } else {
                        if (musicUtil.isPaused()) {
                            musicUtil.resumeMusic();
                        } else {
                            musicUtil.playMusic();
                        }
                    }
                }
            }
        });

        ibPreSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicUtil != null) {
                    playlistUtil.playPrevious();
                    musicUtil.playMusic();
                }
            }
        });

        ibPosSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicUtil != null) {
                    playlistUtil.playNext();
                    musicUtil.playMusic();
                }
            }
        });

        // Set up the click listener for the download button
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicUtil != null) {
                    // Call the downloadCurrentSong method when the download button is clicked
                    musicUtil.downloadCurrentSong();
                } else {
                    Toast.makeText(requireContext(), "music null", Toast.LENGTH_SHORT).show();
                }
            }
        });


        appPreferences = AppPreferences.getInstance(requireContext());

        // Lấy email của người dùng đã đăng nhập từ SharedPreferences
        String loggedInUserEmail = appPreferences.getLoggedInUserEmail();
        ibLikeSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicUtil != null) {
                    // Call the addSongToFavorites method when the "Like" button is clicked
                    musicUtil.addOrRemoveSongFromFavorites();
                }
            }
        });

        return view;
    }
}

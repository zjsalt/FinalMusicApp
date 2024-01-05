package tdtu.report.Fragment;

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
import tdtu.report.ViewModel.MusicSharedViewModel;
import tdtu.report.ViewModel.MusicViewModel;

public class PlayMusicFragment extends Fragment {

    private MusicUtil musicUtil;
    private MusicViewModel musicViewModel;
    private PlaylistUtil playlistUtil;
    private ImageButton ibPlaySong, ibPreSong, ibPosSong, down, ibLikeSong, ibRandomSong, ibRepeatSong;
    private AppPreferences appPreferences;
    private MusicSharedViewModel musicSharedViewModel;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.play_music, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        musicSharedViewModel = new ViewModelProvider(requireActivity()).get(MusicSharedViewModel.class);

        musicViewModel = new ViewModelProvider(requireActivity()).get(MusicViewModel.class);

        // Initialize UI elements and set up event listeners
        ibPlaySong = view.findViewById(R.id.ibPlaySong);
        ibPreSong = view.findViewById(R.id.ibPreSong);
        ibPosSong = view.findViewById(R.id.ibPosSong);
        down = view.findViewById(R.id.ibDownloadSong);
        ibLikeSong = view.findViewById(R.id.ibLikeSong);
        ibRandomSong = view.findViewById(R.id.ibRandomSong);
        ibRepeatSong = view.findViewById(R.id.ibRepeatSong);
        SeekBar seekBar = view.findViewById(R.id.seekBar);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView start = view.findViewById(R.id.start);
        TextView end = view.findViewById(R.id.end);

        int position = getArguments().getInt("position", -1);
        String path = getArguments().getString("audioPath");

        if (getArguments() != null && getArguments().containsKey("audioPath")) {
            String audioPath = getArguments().getString("audioPath");
            musicViewModel.getPlaylist().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> result) {
                    if (result != null) {
                        playlistUtil = new PlaylistUtil(result);
                        playlistUtil.playNext();
                        playlistUtil.playPrevious();
                        musicUtil = new MusicUtil(requireContext(), position, audioPath, playlistUtil, ibPlaySong, tvTitle, seekBar, start, end, ibLikeSong, musicViewModel);
                        musicUtil.playMusic();
                        MusicUtil.updateTextViewsFromSeekBar(seekBar, start, end);
                    } else {
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

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicUtil != null) {
                    musicUtil.downloadCurrentSong();
                } else {
                    Toast.makeText(requireContext(), "music null", Toast.LENGTH_SHORT).show();
                }
            }
        });

        appPreferences = AppPreferences.getInstance(requireContext());

        String loggedInUserEmail = appPreferences.getLoggedInUserEmail();
        ibLikeSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicUtil != null) {
                    musicUtil.addOrRemoveSongFromFavorites();
                }
            }
        });
    }
}

package tdtu.report.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import tdtu.report.R;
import tdtu.report.Utils.MusicUtil;
import tdtu.report.Utils.PlaylistUtil;
import tdtu.report.ViewModel.MusicViewModel;

public class PlayMusicActivity extends AppCompatActivity {

    private MusicUtil musicUtil;
    private MusicViewModel musicViewModel;
    private PlaylistUtil playlistUtil;
    private  ImageButton ibPlaySong,ibPreSong,ibPosSong, down;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_music);

        musicViewModel = new ViewModelProvider(this).get(MusicViewModel.class);

        // Initialize UI elements and set up event listeners
        ibPlaySong = findViewById(R.id.ibPlaySong);
        ibPreSong = findViewById(R.id.ibPreSong);
        ibPosSong = findViewById(R.id.ibPosSong);
        down = findViewById(R.id.ibDownloadSong);
        SeekBar seekBar = findViewById(R.id.seekBar);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView start = findViewById(R.id.start);
        TextView end = findViewById(R.id.end);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("audioPath")) {
            String audioPath = intent.getStringExtra("audioPath");
            // Observe changes in the playlist LiveData and update PlaylistUtil accordingly
            musicViewModel.getPlaylist().observe(this, new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> result) {
                    if (result != null) {
                        playlistUtil = new PlaylistUtil(result);

                        // Set the current song in PlaylistUtil to the selected song
                        playlistUtil.playNext();
                        playlistUtil.playPrevious();

                        // Create a new instance of MusicUtil and pass the PlaylistUtil
                        musicUtil = new MusicUtil(PlayMusicActivity.this, playlistUtil, ibPlaySong, tvTitle,seekBar, start, end);
                        musicUtil.playMusic();

                        // Update the TextViews from SeekBar
                        MusicUtil.updateTextViewsFromSeekBar(seekBar, start, end);
                    } else {
                        // Handle the case where the playlist is null
                        // You may want to show an error message or take appropriate action
                        Toast.makeText(PlayMusicActivity.this, "Playlist is null", Toast.LENGTH_SHORT).show();
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
                }
                else{
                    Toast.makeText(getApplicationContext(),"music null",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}

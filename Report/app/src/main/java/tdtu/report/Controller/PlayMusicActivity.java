package tdtu.report.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tdtu.report.R;

public class PlayMusicActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private MediaPlayer mediaPlayer;
    private List<String> playlist;
    private int currentSongIndex;
    private boolean isPlaying = false;
    private boolean isPaused = false;
    private int pausedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        // Initialize playlist
        playlist = new ArrayList<>();
        playlist.add("KhoaBietLy.mp3");
        playlist.add("Roi Bo.mp3");
        playlist.add("NgayMaiNguoiTaLayChong.mp3");
        playlist.add("Trước .mp3");

        // Initialize current song index
        currentSongIndex = 0;

        Button toggleButton = findViewById(R.id.btnToggle);
        Button preButton = findViewById(R.id.btnPre);
        Button nextButton = findViewById(R.id.btnNext);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    pauseMusic();
                } else {
                    if (isPaused) {
                        resumeMusic();
                    } else {
                        playMusic();
                    }
                }
            }
        });

        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPreviousSong();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNextSong();
            }
        });
    }

    private void playMusic() {
        try {
            Log.d("PlayMusicActivity", "PlayMusic() called");

            // Get the current song from the playlist
            String currentSong = playlist.get(currentSongIndex);

            AssetFileDescriptor afd = getAssets().openFd("audiofiles/" + currentSong);

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.start();

            isPlaying = true;
            isPaused = false;

            // Update button text
            Button toggleButton = findViewById(R.id.btnToggle);
            toggleButton.setText("Pause");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pauseMusic() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer.pause();
            pausedPosition = mediaPlayer.getCurrentPosition();
            isPlaying = false;
            isPaused = true;

            // Update button text
            Button toggleButton = findViewById(R.id.btnToggle);
            toggleButton.setText("Play");
        }
    }

    private void resumeMusic() {
        if (mediaPlayer != null && isPaused) {
            mediaPlayer.seekTo(pausedPosition);
            mediaPlayer.start();
            isPlaying = true;
            isPaused = false;

            // Update button text
            Button toggleButton = findViewById(R.id.btnToggle);
            toggleButton.setText("Pause");
        }
    }

    private void stopMusic() {
        if (mediaPlayer != null && (isPlaying || isPaused)) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;

            isPlaying = false;
            isPaused = false;

            // Update button text
            Button toggleButton = findViewById(R.id.btnToggle);
            toggleButton.setText("Play");
        }
    }

    private void playPreviousSong() {
        if (currentSongIndex > 0) {
            currentSongIndex--;
            stopMusic();
            playMusic();
        }
        else if (currentSongIndex == 0 ){
            currentSongIndex = playlist.size()-1;
            stopMusic();
            playMusic();
        }
    }

    private void playNextSong() {
        if (currentSongIndex < playlist.size() - 1) {
            currentSongIndex++;
            stopMusic();
            playMusic();
        } else if (currentSongIndex == playlist.size()-1){
            // If it's the last song in the playlist, stop the music
            currentSongIndex = 0;
            stopMusic();
            playMusic();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMusic();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playNextSong();
    }
}
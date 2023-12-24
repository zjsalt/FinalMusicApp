package tdtu.report.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tdtu.report.AppDatabase;
import tdtu.report.Dao.AlbumDao;
import tdtu.report.Dao.ArtistDao;
import tdtu.report.Dao.SongDao;
import tdtu.report.DatabaseMigrations;
import tdtu.report.R;
import tdtu.report.Repository.InsertDatabase;

public class PlayMusicActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private AppDatabase appDatabase;
    private MediaPlayer mediaPlayer;
    private List<String> playlist;
    private int currentSongIndex;
    private boolean isPlaying = false;
    private boolean isPaused = false;
    private int pausedPosition = 0;
    private SongDao songDao;


@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.play_music);
    appDatabase =  AppDatabase.getInstance(getApplicationContext());
    songDao = appDatabase.songDao();
//    artistDao = appDatabase.artistDao();
//    albumDao = appDatabase.albumDao();


    // Initialize current song index
    currentSongIndex = 0;
    ImageButton ibPlaySong = findViewById(R.id.ibPlaySong);
    ImageButton ibPreSong = findViewById(R.id.ibPreSong);
    ImageButton ibPosSong = findViewById(R.id.ibPosSong);
    // Khởi tạo danh sách phát
    playlist = new ArrayList<>();

    // Thực hiện AsyncTask để lấy danh sách bài hát từ cơ sở dữ liệu
    new AsyncTask<Void, Void, List<String>>() {
        @Override
        protected List<String> doInBackground(Void... voids) {
            return songDao.getAllSongPaths();
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);
            // Gán danh sách bài hát vào biến toàn cục
            playlist = result;

            if (!playlist.isEmpty()) {
                playMusic();
            } else {
                Toast.makeText(PlayMusicActivity.this, "Playlist Empty", Toast.LENGTH_SHORT).show();
            }
        }
    }.execute();


    // Initialize other UI elements and set up event listeners
    ibPlaySong.setOnClickListener(new View.OnClickListener() {
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

    ibPreSong.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            playPreviousSong();
        }
    });

    ibPosSong.setOnClickListener(new View.OnClickListener() {
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

            // Encode the file name to handle spaces and special characters
//            String encodedFileName = currentSong.replace(" ", "%20");

            AssetFileDescriptor afd = getAssets().openFd("audiofiles/" + currentSong);

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build());
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.start();

            isPlaying = true;
            isPaused = false;

            // Update button text
            ImageButton ibPlaySong = findViewById(R.id.ibPlaySong);
            ibPlaySong.setImageResource(R.drawable.ic_pause);
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
            ImageButton ibPlaySong = findViewById(R.id.ibPlaySong);
            ibPlaySong.setImageResource(R.drawable.ic_play50);
        }
    }

    private void resumeMusic() {
        if (mediaPlayer != null && isPaused) {
            mediaPlayer.seekTo(pausedPosition);
            mediaPlayer.start();
            isPlaying = true;
            isPaused = false;

            // Update button text
            ImageButton ibPlaySong = findViewById(R.id.ibPlaySong);
            ibPlaySong.setImageResource(R.drawable.ic_pause);
        }
    }

    private void stopMusic() {
        if (mediaPlayer != null && (isPlaying || isPaused)) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;

            isPlaying = false;
            isPaused = false;

            ImageButton ibPlaySong = findViewById(R.id.ibPlaySong);
            ibPlaySong.setImageResource(R.drawable.ic_play50);
        }
    }

    private void playPreviousSong() {
        if (currentSongIndex == 0 ){
            currentSongIndex = playlist.size()-1;
        }
        else {
            currentSongIndex--;
        }
        stopMusic();
        playMusic();
    }

    private void playNextSong() {
        if (currentSongIndex < playlist.size() - 1) {
            currentSongIndex++;
        } else if (currentSongIndex == playlist.size()-1){
            // If it's the last song in the playlist, stop the music
            currentSongIndex = 0;
        }
        stopMusic();
        playMusic();
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
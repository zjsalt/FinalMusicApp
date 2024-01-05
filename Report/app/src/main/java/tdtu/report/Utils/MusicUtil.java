package tdtu.report.Utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tdtu.report.Dao.SongDao;
import tdtu.report.Dao.UserDao;
import tdtu.report.Database.AppDatabase;
import tdtu.report.Database.AppPreferences;
import tdtu.report.Model.Song;
import tdtu.report.Model.User;
import tdtu.report.R;
import tdtu.report.Utils.FileUtil;
import tdtu.report.Utils.PlaylistUtil;
import tdtu.report.ViewModel.MusicViewModel;

public class MusicUtil implements MediaPlayer.OnCompletionListener {

    private Context context;
    private static MediaPlayer mediaPlayer;
    private PlaylistUtil playlistUtil;
    private int pausedPosition = 0;
    private ImageButton ibPlaySong;
    private TextView tvTitle, start, end;
    private AppDatabase appDatabase;
    private SongDao songDao;
    private UserDao userDao;
    private String currentSongTitle;
    private SeekBar seekBar;
    private Handler handler;
    private Runnable runnable;
    private String currentSong;
    private ExecutorService executorService;
    private AppPreferences appPreferences;
    private ImageButton ibLikeSong;
    private boolean isLiked = false;
    private User loggedInUser;
    private String loggedInUserEmail;
    private MusicViewModel musicViewModel;
    private Handler uiHandler = new Handler(Looper.getMainLooper());

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public boolean isPaused() {
        return mediaPlayer != null && !mediaPlayer.isPlaying() && pausedPosition > 0;
    }

    public MusicUtil(Context context, int currentPosition, String currentSong, PlaylistUtil playlistManager, ImageButton ibPlaySong, TextView tvTitle, SeekBar seekBar, TextView start, TextView end, ImageButton ibLikeSong, MusicViewModel musicViewModel) {
        this.context = context;
        this.currentSong = currentSong;
        this.playlistUtil = playlistManager;
        this.ibPlaySong = ibPlaySong;
        this.tvTitle = tvTitle;
        this.seekBar = seekBar;
        this.start = start;
        this.end = end;
        this.ibLikeSong = ibLikeSong;
        this.musicViewModel = musicViewModel;

        playlistUtil.setCurrentPosition(playlistUtil.findPositionByAudioPath(currentSong));

        appDatabase = AppDatabase.getInstance(context);
        songDao = appDatabase.songDao();
        userDao = appDatabase.userDao();
        handler = new Handler();
        executorService = Executors.newSingleThreadExecutor();
        if (appPreferences == null) {
            appPreferences = AppPreferences.getInstance(context);
        }
        loggedInUserEmail = appPreferences.getLoggedInUserEmail();

        // Lắng nghe sự thay đổi của LiveData<User> khi có thay đổi trong cơ sở dữ liệu
        musicViewModel.getUserByEmail(loggedInUserEmail).observeForever(new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    loggedInUser = user;
                    // Tiếp tục xử lý tại đây
                    isLiked = isSongInFavorites(currentSong, loggedInUser.getFavoritePlaylist());
                    updateLikeButton();
                }
            }
        });
    }

    private void prepareMediaPlayer(String songPath) throws IOException {
        try {
            AssetFileDescriptor afd = context.getAssets().openFd("audiofiles/" + songPath);

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build());
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isSongInFavorites(String currentSongPath, List<Song> favoriteSongs) {
        if (favoriteSongs != null) {
            for (Song song : favoriteSongs) {
                if (song.getAudioPath().equals(currentSongPath)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updateLikeButton() {
        // Dựa vào trạng thái isLiked để cập nhật hình ảnh của ImageButton
        int imageResource = isLiked ? R.drawable.ic_like35 : R.drawable.ic_unlike35;
        ibLikeSong.setImageResource(imageResource);
    }

    public void playMusic() {
        String currentSong = playlistUtil.getCurrentSong();
        if (currentSong != null) {
            uiHandler.post(() -> {
                try {
                    Log.d("MusicUtil", "playMusic() called");

                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }

                    mediaPlayer = new MediaPlayer();
                    prepareMediaPlayer(currentSong);

                    currentSongTitle = getTitleFromDatabase(currentSong);
                    tvTitle.setText(currentSongTitle);

                    mediaPlayer.start();

                    ibPlaySong.setImageResource(R.drawable.ic_pause);

                    // Update seekBar progress
                    updateSeekBar();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Failed to prepare media player", e);
                }
            });
        }
    }





    private String getTitleFromDatabase(String audioPath) {
        final MutableLiveData<String> titleLiveData = new MutableLiveData<>();

        // Use Kotlin Coroutines to perform the database operation in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                songDao = appDatabase.songDao();
                Song song = songDao.getSongByAudioPath(audioPath);
                String title = song != null ? song.getTitle() : "";

                // Update the LiveData on the main thread
                titleLiveData.postValue(title);
            }
        }).start();

        // Observe the LiveData and update the UI when the value changes
        titleLiveData.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String title) {
                uiHandler.post(() -> tvTitle.setText(title));
            }
        });

        // Return an empty string for now
        return "";
    }

    private void updateSeekBar() {
        if (mediaPlayer != null) {
            seekBar.setMax(mediaPlayer.getDuration());

            runnable = new Runnable() {
                @Override
                public void run() {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress(currentPosition);
                    start.setText(formatTime(currentPosition));
                    end.setText(formatTime(mediaPlayer.getDuration()));
                    handler.postDelayed(this, 1000);
                }
            };

            handler.postDelayed(runnable, 1000);
        }
    }

    public static void updateTextViewsFromSeekBar(SeekBar seekBar, final TextView startTextView, final TextView endTextView) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                String formattedTime = formatTime(progress);
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    startTextView.setText(MusicUtil.formatTime(progress));

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Không cần xử lý trong trường hợp này
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public static String formatTime

            (int progress) {
        int minutes = progress / 1000 / 60;
        int seconds = (progress / 1000) % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            pausedPosition = mediaPlayer.getCurrentPosition();
            ibPlaySong.setImageResource(R.drawable.ic_play50);
            handler.removeCallbacks(runnable);
        }
    }

    public void resumeMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(pausedPosition);
            mediaPlayer.start();
            ibPlaySong.setImageResource(R.drawable.ic_pause);
            updateSeekBar();
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;

            ibPlaySong.setImageResource(R.drawable.ic_play50);
            handler.removeCallbacks(runnable);
            seekBar.setProgress(0);
        }
    }

    public void playPreviousSong() {
        playlistUtil.playPrevious();
        stopMusic();
        playMusic();
    }

    public void playNextSong() {
        if (playlistUtil.hasNext()) {
            playlistUtil.playNext();
            stopMusic();
            playMusic();
        } else {
            // Handle the case where there is no next song (e.g., loop back to the first song)
            // For example:
            playlistUtil.playFirst();
            stopMusic();
            playMusic();
        }
    }


    public String getCurrentSongTitle() {
        return currentSongTitle;
    }

    public void downloadCurrentSong() {
        String currentSong = playlistUtil.getCurrentSong();
        if (currentSong != null) {
            // Copy the asset file to the external storage directory
            File file = copyAssetFileToExternalStorage(currentSong);

            if (file != null) {
                showToast("File downloaded to external storage");
            } else {
                showToast("Failed to download file");
                return;
            }

            // Trigger any additional actions you need for the downloaded file
            // For example, you might want to add it to the media store or perform other operations.

            // For demonstration purposes, let's just show a Toast indicating success.
            showToast("Download completed");
        } else {
            showToast("Current song is null");
        }
    }


    private File copyAssetFileToExternalStorage(String fileName) {
        // Use getExternalStoragePublicDirectory for compatibility with Android 10 and lower
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);

        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.e("MusicUtil", "Failed to create directory");
                return null;
            }
        }

        File file = new File(dir, fileName);

        try {
            AssetFileDescriptor assetFileDescriptor = context.getAssets().openFd("audiofiles/" + fileName);
            FileUtil.copyFile(assetFileDescriptor.createInputStream(), file);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addOrRemoveSongFromFavorites() {
        if (loggedInUser != null) {
            executorService.execute(() -> {
                String currentSongPath = playlistUtil.getCurrentSong();
                List<String> favoriteSongsPaths = new ArrayList<>(appPreferences.getFavoriteSongsPaths());
                List<Song> favoriteSongs = new ArrayList<>(loggedInUser.getFavoritePlaylist());
                Log.d("MusicUtil","favoriteSongPath:"+favoriteSongsPaths.size());
                Log.d("MusicUtil","favoriteSong:"+favoriteSongs.size());

                // Check if the current song is already in the favorite paths
//                boolean isCurrentlyLiked = favoriteSongsPaths.contains(currentSongPath);
                boolean isCurrentlyLiked = isSongInFavorites(currentSongPath, favoriteSongs);

                if (isCurrentlyLiked) {
                    // If the song is already liked, remove it
                    favoriteSongsPaths.remove(currentSongPath);
                    removeSongFromFavorites(currentSongPath, favoriteSongs);
                    showToast("Removed from favorites");


                } else {
                    // If the song is not liked, add it
                    favoriteSongsPaths.add(currentSongPath);
                    addSongToFavorites(currentSongPath, favoriteSongs);
                    showToast("Added to favorites");


                }

                // Save the updated list to SharedPreferences
                appPreferences.setFavoriteSongs(favoriteSongsPaths);

                // Update the like button
                isLiked = !isCurrentlyLiked;
                updateLikeButton();
            });
        } else {
            // Show a Toast or perform other actions indicating that the user is not found
            showToast("User not found");
        }
    }


    private void removeSongFromFavorites(String currentSongPath, List<Song> favoriteSongs) {
        Iterator<Song> iterator = favoriteSongs.iterator();
        while (iterator.hasNext()) {
            Song song = iterator.next();
            if (song.getAudioPath().equals(currentSongPath)) {
                iterator.remove();
                break;
            }
        }
        loggedInUser.setFavoritePlaylist(favoriteSongs);
        userDao.update(loggedInUser);

        // Show a Toast or perform other actions indicating success
//        showToast("Song removed from favorites");

        // Update the like button
        isLiked = false;
        updateLikeButton();
    }

    private void addSongToFavorites(String currentSongPath, List<Song> favoriteSongs) {
        // Use Room DAO to get the Song object by audioPath
        Song song = songDao.getSongByAudioPath(currentSongPath);
        if (song != null) {
            // Add the current song to the favorite playlist
            favoriteSongs.add(song);
            loggedInUser.setFavoritePlaylist(favoriteSongs);
            userDao.update(loggedInUser);

            // Show a Toast or perform other actions indicating success
//            showToast("Song added to favorites");

            // Update the like button
            isLiked = true;
            updateLikeButton();
        } else {
            // Show a Toast or perform other actions indicating failure
            showToast("Song not found in the database");
        }
    }
    public List<Song> getFavoriteSongs() {
        if (loggedInUser != null) {
            return new ArrayList<>(loggedInUser.getFavoritePlaylist());
        }
        return new ArrayList<>(); // Return an empty list if the user is not logged in
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playNextSong();
    }

    private void showToast(String message) {
        uiHandler.post(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }
}

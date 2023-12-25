package tdtu.report.Utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import tdtu.report.AppDatabase;
import tdtu.report.Dao.SongDao;
import tdtu.report.Model.Song;
import tdtu.report.R;

public class MusicUtil implements MediaPlayer.OnCompletionListener {

    private Context context;
    private static MediaPlayer mediaPlayer ;
    private PlaylistUtil playlistUtil;
    private int pausedPosition = 0;
    private ImageButton ibPlaySong;
    private TextView tvTitle, start, end;
    private AppDatabase appDatabase;
    private SongDao songDao;
    private String currentSongTitle;
//    private AudioUtil audioUtil;
    private SeekBar seekBar;
    private Handler handler;
    private Runnable runnable;

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public boolean isPaused() {
        return mediaPlayer != null && !mediaPlayer.isPlaying() && pausedPosition > 0;
    }

    public MusicUtil(Context context, PlaylistUtil playlistManager, ImageButton ibPlaySong, TextView tvTitle, SeekBar seekBar,TextView start, TextView end) {
        this.context = context;
        this.playlistUtil = playlistManager;
        this.ibPlaySong = ibPlaySong;
        this.tvTitle = tvTitle;
        this.seekBar = seekBar;
        this.start = start;
        this.end=end;
        appDatabase = AppDatabase.getInstance(context);
//        audioUtil = new AudioUtil();
        handler = new Handler();
    }

    private void prepareMediaPlayer(String songPath) throws IOException {
        AssetFileDescriptor afd = context.getAssets().openFd("audiofiles/" + songPath);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build());
        mediaPlayer.prepare();
        mediaPlayer.setOnCompletionListener(this);
    }

    public void playMusic() {
        String currentSong = playlistUtil.getCurrentSong();
        if (currentSong != null) {
            try {
                Log.d("MusicUtil", "playMusic() called");

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }

                prepareMediaPlayer(currentSong);

                currentSongTitle = getTitleFromDatabase(currentSong);
                tvTitle.setText(currentSongTitle);

                mediaPlayer.start();

                ibPlaySong.setImageResource(R.drawable.ic_pause);

                // Update seekBar progress
                updateSeekBar();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                tvTitle.setText(title);
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

    public static String formatTime(int progress) {
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
        playlistUtil.playNext();
        stopMusic();
        playMusic();
    }

    public String getCurrentSongTitle() {
        return currentSongTitle;
    }
//    public void downloadCurrentSong() {
//        String currentSong = playlistUtil.getCurrentSong();
//        if (currentSong != null) {
//            // Sao chép tệp từ thư mục assets vào bộ nhớ
//            copyAssetFileToStorage(currentSong);
//
//            // Tạo một Uri từ vị trí lưu trữ trên bộ nhớ và tải xuống
//            Uri fileUri = getStorageFileUri(currentSong);
//            if (fileUri != null) {
//                DownloadManager.Request request = new DownloadManager.Request(fileUri);
//                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//                request.setTitle("Downloading: " + currentSong);
//                request.setDescription("Downloading audio file");
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, currentSong);
//
//                // Get download service and enqueue file
//                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//                long downloadId = downloadManager.enqueue(request);
//
//                // Register a broadcast receiver to receive download completion event
//                context.registerReceiver(new BroadcastReceiver() {
//                    @Override
//                    public void onReceive(Context context, Intent intent) {
//                        long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//                        if (downloadId == id) {
//                            Toast.makeText(context, "Download completed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
//            } else {
//                Toast.makeText(context, "Failed to get file URI", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(context, "Current song is null", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void copyAssetFileToStorage(String fileName) {
//        File file = new File(context.getCacheDir(), fileName);
//        try {
//            AssetFileDescriptor assetFileDescriptor = context.getAssets().openFd("audiofiles/" + fileName);
//            FileUtil.copyFile(assetFileDescriptor.createInputStream(), file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Uri getStorageFileUri(String fileName) {
//        File file = new File(context.getCacheDir(), fileName);
//        if (file.exists()) {
//            return Uri.fromFile(file);
//        }
//        return null;
//    }

//    public void downloadCurrentSong() {
//        String currentSong = playlistUtil.getCurrentSong();
//        if (currentSong != null) {
//            // Copy the asset file to the app's private directory
//            File file = copyAssetFileToPrivateDirectory(currentSong);
//
//            if (file != null) {
//                Toast.makeText(context, "File downloaded to app's private directory", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(context, "Failed to download file", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            // Trigger any additional actions you need for the downloaded file
//            // For example, you might want to add it to the media store or perform other operations.
//
//            // For demonstration purposes, let's just show a Toast indicating success.
//            Toast.makeText(context, "Download completed", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Current song is null", Toast.LENGTH_SHORT).show();
//        }
//    }

//    private File copyAssetFileToPrivateDirectory(String fileName) {
//        File dir = new File(context.getFilesDir(), "audiofiles");
//
//        if (!dir.exists()) {
//            if (!dir.mkdirs()) {
//                Log.e("MusicUtil", "Failed to create directory");
//                return null;
//            }
//        }
//
//        File file = new File(dir, fileName);
//
//        try {
//            AssetFileDescriptor assetFileDescriptor = context.getAssets().openFd("audiofiles/" + fileName);
//            FileUtil.copyFile(assetFileDescriptor.createInputStream(), file);
//            return file;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public void downloadCurrentSong() {
        String currentSong = playlistUtil.getCurrentSong();
        if (currentSong != null) {
            // Copy the asset file to the external storage directory
            File file = copyAssetFileToExternalStorage(currentSong);

            if (file != null) {
                Toast.makeText(context, "File downloaded to external storage", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to download file", Toast.LENGTH_SHORT).show();
                return;
            }

            // Trigger any additional actions you need for the downloaded file
            // For example, you might want to add it to the media store or perform other operations.

            // For demonstration purposes, let's just show a Toast indicating success.
            Toast.makeText(context, "Download completed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Current song is null", Toast.LENGTH_SHORT).show();
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




    @Override
    public void onCompletion(MediaPlayer mp) {
        playNextSong();
    }
}
package tdtu.report.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.lang.ref.WeakReference;
import java.util.List;

import tdtu.report.Dao.AlbumDao;
import tdtu.report.Dao.PlaylistDao;
import tdtu.report.Database.AppDatabase;
import tdtu.report.Fragment.LikeFragment;
import tdtu.report.Model.Album;
import tdtu.report.Model.Playlist;

public class HomeUtil {

    private Context context;
    private LikeFragment likeFragment;
    private FrameLayout fragmentContainer;

    public HomeUtil(Context context, LikeFragment likeFragment, FrameLayout fragmentContainer) {
        this.context = context;
        this.likeFragment = likeFragment;
        this.fragmentContainer = fragmentContainer;
    }

    public void showLikeFragment() {
        // Thêm LikeFragment vào FragmentContainer
        if(likeFragment == null){
            Log.d("HomeUtil","likefragment ==null" );

        }
        if(fragmentContainer == null){
            Log.d("HomeUtil","fragmentContainer ==null" );

        }

        if (likeFragment != null && fragmentContainer != null) {
            Log.d("HomeUtil","likefragment !=null");
            FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(fragmentContainer.getId(), likeFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }



    public void setFragmentContainer(FrameLayout fragmentContainer) {
        this.fragmentContainer = fragmentContainer;
    }

    public void loadPlaylistsAsync(OnPlaylistsLoadedListener listener) {
        new LoadPlaylistsAsyncTask(context, listener).execute();
    }


    public interface OnPlaylistsLoadedListener {
        void onPlaylistsLoaded(List<Playlist> playlists);
    }

    private static class LoadPlaylistsAsyncTask extends AsyncTask<Void, Void, List<Playlist>> {
        private WeakReference<Context> context;
        private WeakReference<OnPlaylistsLoadedListener> listener;

        LoadPlaylistsAsyncTask(Context context, OnPlaylistsLoadedListener listener) {
            this.context = new WeakReference<>(context);
            this.listener = new WeakReference<>(listener);
        }


        @Override
        protected List<Playlist> doInBackground(Void... voids) {
            return getPlaylistsFromDatabase(context.get());
        }

        @Override
        protected void onPostExecute(List<Playlist> playlists) {
            super.onPostExecute(playlists);
            OnPlaylistsLoadedListener loadedListener = listener.get();
            if (loadedListener != null) {
                loadedListener.onPlaylistsLoaded(playlists);
            }
        }
    }

    private static List<Playlist> getPlaylistsFromDatabase(Context context) {
        PlaylistDao playlistDao = AppDatabase.getInstance(context).playlistDao();
        return playlistDao.getAllPlaylists();
    }
    public List<Playlist> getPlaylistsFromDatabase() {
        // Giả sử bạn đã có một lớp quản lý cơ sở dữ liệu, ví dụ PlaylistDatabaseManager

        PlaylistDao playlistDao = AppDatabase.getInstance(context).playlistDao();

        // Lấy danh sách Playlist từ cơ sở dữ liệu
        return playlistDao.getAllPlaylists();
    }
    public List<Album> getAlbumsFromDatabase() {
        // Tương tự như trên, giả sử bạn đã có một lớp quản lý cơ sở dữ liệu, ví dụ AlbumDatabaseManager
        AlbumDao albumDao = AppDatabase.getInstance(context).albumDao();

        // Lấy danh sách Album từ cơ sở dữ liệu
        return albumDao.getAllAlbums();
    }

}


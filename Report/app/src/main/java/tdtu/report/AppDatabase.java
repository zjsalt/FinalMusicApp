package tdtu.report;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import tdtu.report.Dao.AlbumDao;
import tdtu.report.Dao.ArtistDao;
import tdtu.report.Dao.PlaylistDao;
import tdtu.report.Dao.SongDao;
import tdtu.report.Dao.UserDao;
import tdtu.report.Model.Album;
import tdtu.report.Model.Artist;
import tdtu.report.Model.Converters;
import tdtu.report.Model.Playlist;
import tdtu.report.Model.Song;
import tdtu.report.Model.User;
//Album.class,
@Database(entities = {Artist.class, Playlist.class, User.class, Song.class, Album.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract SongDao songDao();
    public abstract UserDao userDao();

    public abstract AlbumDao albumDao();
    public abstract ArtistDao artistDao();
    public abstract PlaylistDao playlistDao();

}

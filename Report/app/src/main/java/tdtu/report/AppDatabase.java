package tdtu.report;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import tdtu.report.Dao.AlbumDao;
import tdtu.report.Dao.ArtistDao;
import tdtu.report.Dao.PlaylistDao;
import tdtu.report.Dao.PlaylistSongCrossRefDao;
import tdtu.report.Dao.SongDao;
import tdtu.report.Dao.UserDao;
import tdtu.report.Database.PlaylistSongCrossRef;
import tdtu.report.Model.Album;
import tdtu.report.Model.Artist;
import tdtu.report.Model.Converters;
import tdtu.report.Model.Playlist;
import tdtu.report.Model.Song;
import tdtu.report.Model.User;

@Database(entities = {Artist.class, Playlist.class, User.class, Song.class, Album.class, PlaylistSongCrossRef.class}, version = 6)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract SongDao songDao();
    public abstract UserDao userDao();
    public abstract AlbumDao albumDao();
    public abstract ArtistDao artistDao();
    public abstract PlaylistDao playlistDao();
    public abstract PlaylistSongCrossRefDao playlistSongCrossRefDao();

    private static AppDatabase instance;
    private static final String DATABASE_NAME = "app-database";

    public static AppDatabase getInstance() {
        if (instance == null) {
            throw new IllegalStateException("AppDatabase should be initialized with a context first");
        }
        return instance;
    }

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addMigrations(DatabaseMigrations.MIGRATION_2_3)
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            // Thêm mã để chèn dữ liệu mẫu hoặc tạo bảng trống ở đây
                            // Ví dụ: db.execSQL("INSERT INTO user (email, name, password) VALUES ('user@example.com', 'John Doe', 'password')");
                            db.execSQL("DROP TABLE user");
                            db.execSQL("DROP TABLE song");
                            db.execSQL("DROP TABLE album");
                            db.execSQL("DROP TABLE playlist");
                            db.execSQL("DROP TABLE artist");
                            db.execSQL("DROP TABLE playlist_song_cross_ref");



                        }
                    })
                    .build();
        }
        return instance;
    }
//    public static synchronized AppDatabase getInstance(Context context) {
//        if (instance == null) {
//            instance = Room.databaseBuilder(
//                    context.getApplicationContext(),
//                    AppDatabase.class,
//                    DATABASE_NAME
//            ).build();
//        }
//        return instance;
//    }
}


package tdtu.report.Database;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseMigrations {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Thực hiện các bước chuyển đổi dữ liệu từ version 1 lên version 2
            database.execSQL("ALTER TABLE song ADD COLUMN artistId INTEGER");
            // Thêm các câu lệnh SQL khác nếu cần
        }
    };
    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
                // Backup the old table
//                database.execSQL("CREATE TABLE user_backup (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, playlists TEXT, password TEXT, favoritePlaylist TEXT, email TEXT)");

                // Copy data from the old table to the new one
//                database.execSQL("INSERT INTO user_backup (name, playlists, password, favoritePlaylist, email) SELECT name, playlists, password, favoritePlaylist, email FROM user");

                // Remove the old table
                database.execSQL("DROP TABLE user");
                database.execSQL("DROP TABLE album");
                database.execSQL("DROP TABLE artist");
                database.execSQL("DROP TABLE song");
                database.execSQL("DROP TABLE song");


                // Create the new table with the updated schema
//                database.execSQL("CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, playlists TEXT, password TEXT, favoritePlaylist TEXT, email TEXT)");

                // Copy data back from the backup table to the new one
//                database.execSQL("INSERT INTO user (id, name, playlists, password, favoritePlaylist, email) SELECT id, name, playlists, password, favoritePlaylist, email FROM user_backup");

                // Remove the backup table
//                database.execSQL("DROP TABLE user_backup");
            }
        };
}


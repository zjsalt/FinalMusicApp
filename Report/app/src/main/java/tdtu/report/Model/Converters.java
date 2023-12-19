package tdtu.report.Model;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<Song> fromListSongJson(String value) {
        Type listType = new TypeToken<List<Song>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String toListSongJson(List<Song> songs) {
        return new Gson().toJson(songs);
    }

    @TypeConverter
    public static List<Playlist> fromListPlaylistJson(String value) {
        Type listType = new TypeToken<List<Playlist>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String toListPlaylistJson(List<Playlist> playlists) {
        return new Gson().toJson(playlists);
    }

    @TypeConverter
    public static Playlist fromPlaylistJson(String value) {
        Type playlistType = new TypeToken<Playlist>() {}.getType();
        return new Gson().fromJson(value, playlistType);
    }

    @TypeConverter
    public static String toPlaylistJson(Playlist playlist) {
        return new Gson().toJson(playlist);
    }
    @TypeConverter
    public static Artist fromArtistJson(String value) {
        Type artistType = new TypeToken<Artist>() {}.getType();
        return new Gson().fromJson(value, artistType);
    }

    @TypeConverter
    public static String toArtistJson(Artist artist) {
        return new Gson().toJson(artist);
    }
    @TypeConverter
    public static Album fromAlbumJson(String value) {
        Type albumType = new TypeToken<Album>() {}.getType();
        return new Gson().fromJson(value, albumType);
    }

    @TypeConverter
    public static String toAlbumJson(Album album) {
        return new Gson().toJson(album);
    }
}
package tdtu.report.Repository;

import java.util.List;

import tdtu.report.Dao.SongDao;
import tdtu.report.Model.Album;
import tdtu.report.Model.Song;

public class SongRepository {
    private SongDao songDao;

    public SongRepository(SongDao songDao) {
        this.songDao = songDao;
    }

    public List<Song> getAllSongs() {
        return songDao.getAllSongs();
    }

    public void insertSong(Song song) {
        songDao.insert(song);
    }

    public void updateSong(Song song) {
        songDao.update(song);
    }

    public void deleteSong(Song song) {
        songDao.delete(song);
    }

    public void insertAllSongs(List<Song> songs) {
        songDao.insertAll(songs);
    }

    public List<Song> getSongsByAlbum(Album album) {
        return songDao.getSongsByAlbum(album);
    }
}
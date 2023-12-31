package tdtu.report.Repository;

import java.util.List;

import tdtu.report.Dao.SongDao;
import tdtu.report.Model.Song;

public class SongRepository {
    private SongDao songDao;

    public SongRepository(SongDao songDao) {
        this.songDao = songDao;
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

//    public String getSongsByAlbumId(Album album) {
//        return songDao.getSAlbumId(album);
//    }
}
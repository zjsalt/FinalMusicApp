package tdtu.report.Repository;

import android.database.Observable;

import java.util.List;

import tdtu.report.Dao.AlbumDao;
import tdtu.report.Model.Album;

public class AlbumRepository {
    private AlbumDao albumDao;

    public AlbumRepository(AlbumDao albumDao) {
        this.albumDao = albumDao;
    }

    public List<Album> getAllAlbums() {
        return albumDao.getAllAlbums();
    }

    public Album getAlbumById(int albumId) {
        return albumDao.getAlbumById(albumId);
    }

    public void insertAlbum(Album album) {
        albumDao.insert(album);
    }

    public void deleteAlbum(Album album) {
        albumDao.delete(album);
    }

    public void updateAlbum(Album album) {
        albumDao.update(album);
    }

    public void insertAllAlbums(List<Album> albums) {
        albumDao.insertAll(albums);
    }
}
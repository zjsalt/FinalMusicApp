package tdtu.report.Repository;

import java.util.List;

import tdtu.report.Dao.ArtistDao;
import tdtu.report.Model.Artist;

public class ArtistRepository {
    private ArtistDao artistDao;

    public ArtistRepository(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }

    public List<Artist> getAllArtists() {
        return artistDao.getAllArtists();
    }

    public Artist getArtistById(int artistId) {
        return artistDao.getArtistById(artistId);
    }

    public void insertArtist(Artist artist) {
        artistDao.insert(artist);
    }

    public void deleteArtist(Artist artist) {
        artistDao.delete(artist);
    }

    public void updateArtist(Artist artist) {
        artistDao.update(artist);
    }

    public void insertAllArtists(List<Artist> artists) {
        artistDao.insertAll(artists);
    }
}
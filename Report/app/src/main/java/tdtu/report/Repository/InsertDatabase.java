package tdtu.report.Repository;

import android.os.AsyncTask;

import tdtu.report.Dao.AlbumDao;
import tdtu.report.Dao.ArtistDao;
import tdtu.report.Dao.SongDao;
import tdtu.report.Model.Album;
import tdtu.report.Model.Artist;
import tdtu.report.Model.Song;

public class InsertDatabase {
    private ArtistDao artistDao;
    private SongDao songDao;
    private AlbumDao albumDao;
    private boolean isDataInserted = false;

    public InsertDatabase(ArtistDao artistDao, SongDao songDao, AlbumDao albumDao) {
        this.artistDao = artistDao;
        this.songDao = songDao;
        this.albumDao = albumDao;
    }

    public void insertData() {
        if (!isDataInserted) {
            new InsertDataTask(artistDao, songDao, albumDao).execute();
            isDataInserted = true;
        }
    }

    private static class InsertDataTask extends AsyncTask<Void, Void, Void> {
        private ArtistDao artistDao;
        private SongDao songDao;
        private AlbumDao albumDao;

        public InsertDataTask(ArtistDao artistDao, SongDao songDao, AlbumDao albumDao) {
            this.artistDao = artistDao;
            this.songDao = songDao;
            this.albumDao = albumDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Thêm dữ liệu nghệ sĩ
            Artist artist1 = new Artist("Anh Tú");
            Artist artist2 = new Artist("Hòa Minzy");
            artistDao.insert(artist1);
            artistDao.insert(artist2);

            // Thêm dữ liệu bài hát
            Song song1 = new Song("Khoa Biet Ly", artist1.getId(), "KhoaBietLy.mp3");
            Song song2 = new Song("Truoc khi em ton tai", artist1.getId(), "Truoc khi em ton tai.mp3");
            songDao.insert(song1);
            songDao.insert(song2);

            // Thêm dữ liệu album
            Album album1 = new Album("Album 1", artist1.getId());
            Album album2 = new Album("Album 2", artist2.getId());
            albumDao.insert(album1);
            albumDao.insert(album2);

            return null;
        }
    }
}



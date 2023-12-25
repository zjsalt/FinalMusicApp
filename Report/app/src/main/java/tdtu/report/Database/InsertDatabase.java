package tdtu.report.Database;

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
            artistDao.insert(artist1);
            Song song1 = new Song("Khoa Biet Ly", artist1.getId(), "KhoaBietLy.mp3");
            songDao.insert(song1);
            songDao.insert(new Song("Ngày Mai Người Ta Lấy Chồng",artist1.getId(),"NgayMaiNguoiTaLayChong.mp3"));


            Artist artist = new Artist("Hòa Minzy");
            artistDao.insert(artist);
            Song song = new Song("Rời Bỏ", artist1.getId(), "Roi Bo.mp3");
            songDao.insert(song);
            songDao.insert(new Song("Bật Tình Yêu Lên", artist.getId(), "Bat Tinh Yeu Len.mp3"));



            artist = new Artist("HIEUTHUHAI");
            artistDao.insert(artist);
            songDao.insert(new Song("Ngủ Một Mình", artist.getId(), "Ngu mot minh.mp3"));

            artist = new Artist("WrenEvans");
            artistDao.insert(artist);
            songDao.insert(new Song("Từng Quen", artist.getId(), "TungQuen.mp3"));



            artist = new Artist("Mono");
            artistDao.insert(artist);
            songDao.insert(new Song("Waiting For You", artist.getId(), "Waiting For You.mp3"));


            artist = new Artist("Sơn Tùng MTP");
            artistDao.insert(artist);
            songDao.insert(new Song("Muộn Rồi Mà Sao Còn", artist.getId(), "Muon roi ma sao con.mp3"));


            artist = new Artist("Hoàng Thùy Linh");
            artistDao.insert(artist);
            songDao.insert(new Song("See Tình", artist.getId(), "See tinh.mp3"));


            artist = new Artist("Olew");
            artistDao.insert(artist);
            songDao.insert(new Song("Rồi Ta Sẽ Ngắm Pháo Hoa Cùng Nhau", artist.getId(), "Roi ta se ngam phap hoa cung nhau.mp3"));




            // Thêm dữ liệu bài hát
//            Song song3 = new Song("Truoc khi em ton tai", artist1.getId(), "Truoc khi em ton tai.mp3");
//            Song song4 = new Song("Truoc khi em ton tai", artist1.getId(), "Truoc khi em ton tai.mp3");
//            Song song5 = new Song("Truoc khi em ton tai", artist1.getId(), "Truoc khi em ton tai.mp3");
//            Song song6 = new Song("Truoc khi em ton tai", artist1.getId(), "Truoc khi em ton tai.mp3");
//            Song song7 = new Song("Truoc khi em ton tai", artist1.getId(), "Truoc khi em ton tai.mp3");
//

            // Thêm dữ liệu album
            Album album1 = new Album("Album 1", artist1.getId());
            Album album2 = new Album("Album 2", artist.getId());
            albumDao.insert(album1);
            albumDao.insert(album2);

            return null;
        }
    }
}



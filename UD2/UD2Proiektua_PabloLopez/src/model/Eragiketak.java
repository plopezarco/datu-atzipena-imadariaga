package model;

import global.Global;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.hibernate.query.Query;

public class Eragiketak {

    static SessionFactory sf = new Configuration().configure().buildSessionFactory();

    private static Connection connect() {

        String url = "jdbc:mysql://" + Global.ZERBITZARIA + "/" + Global.DATUBASEA;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, Global.getErabiltzailea(), Global.getPasahitza());
        } catch (SQLException e) {
            System.out.println("Error Code:" + e.getErrorCode() + "-" + e.getMessage());
        }
        return conn;
    }

    //ALBUM
    public static ObservableList albumakKargatu() {
        ObservableList<Album> albumOL = FXCollections.observableArrayList();

        String sql = "SELECT albumId, album.title, name as artistName from album inner join artist on album.artistId = artist.artistId";
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                albumOL.add(new Album(rs.getInt("albumId"),
                        rs.getString("title"),
                        rs.getString("artistName")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return albumOL;
    }

    public static boolean albumaAldatu(int albumId, String balioBerria) {
        String sql = "UPDATE album set title=? where albumId = " + albumId;
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, balioBerria);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean albumaGehitu(Album a) {
        String sql = "Select artistId from artist where name ='" + a.getArtistName() + "'";     //Daukagun artista izenarekin, artista Id-a lortuko dugu
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                String azkenAlbumSQL = "select max(albumId) as azkena from album";  //Azken album + 1 egingo dugu, autoincrement ez delako
                ResultSet rs2 = stmt.executeQuery(azkenAlbumSQL);
                int artistId = rs.getInt("artistId");
                if (rs2.next()) {
                    int azkenAlbumId = rs2.getInt("azkena");
                    azkenAlbumId = azkenAlbumId + 1;
                    String insertSql = "INSERT INTO Album values (?,?,?)";  //Album taulan id, izenburua eta artista ID gehituko ditugu
                    PreparedStatement pstmt = conn.prepareStatement(insertSql);
                    pstmt.setInt(1, azkenAlbumId);
                    pstmt.setString(2, a.getTitle());
                    pstmt.setInt(3, artistId);
                    pstmt.executeUpdate();
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean albumaEzabatu(Album a) {
        String sql = "DELETE from album where albumId = " + a.getAlbumId();
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //TRACK-ALBUM
    public static ObservableList trackAlbumKargatu() {
        ObservableList<TrackAlbum> trackAlbumOL = FXCollections.observableArrayList();

        String sql = "select album.title as albumTitle, count(track.name) as trackKop from album inner join track on album.albumId = track.albumId group by album.title;";
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                trackAlbumOL.add(new TrackAlbum(rs.getString("albumTitle"),
                        rs.getInt("trackKop")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return trackAlbumOL;

    }

    //ARTIST
    public static ObservableList artistakKargatu() {
        ObservableList<Artist> artistOL = FXCollections.observableArrayList();

        Session saioa = sf.openSession();
        saioa.beginTransaction();
        List result = saioa.createQuery("from Artist").list();
        for (Artist a : (List<Artist>) result) {
            artistOL.add(a);
        }
        saioa.getTransaction().commit();
        saioa.close();

        return artistOL;
    }

    public static boolean artistaAldatu(int artistId, String balioBerria) {
        try {
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            String qryString = "update Artist a set a.name='" + balioBerria + "' where a.artistId=" + artistId;
            Query query = saioa.createQuery(qryString);
            query.executeUpdate();
            saioa.getTransaction().commit();
            saioa.close();
            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }

    public static boolean artistaGehitu(Artist a) {
        try {
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            saioa.save(a);
            saioa.getTransaction().commit();
            saioa.close();
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public static boolean artistaEzabatu(Artist a) {
        try {
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            Query query = saioa.createQuery("delete from Artist a where a.artistId=" + a.getArtistId());
            query.executeUpdate();
            saioa.getTransaction().commit();
            saioa.close();
            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }

    //ALBUM-ARTIST
    public static ObservableList albumArtistKargatu() {
        ObservableList<AlbumArtist> albumArtistOL = FXCollections.observableArrayList();

        String sql = "select artist.name as artistName, count(album.title) as albumKop from album inner join artist on album.artistId = artist.artistId group by artist.name";
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                albumArtistOL.add(new AlbumArtist(rs.getString("artistName"),
                        rs.getInt("albumKop")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return albumArtistOL;
    }

    //GENRE
    public static ObservableList generoakKargatu() {
        ObservableList<Genre> genreOL = FXCollections.observableArrayList();

        Session saioa = sf.openSession();
        saioa.beginTransaction();
        List result = saioa.createQuery("from Genre").list();
        for (Genre g : (List<Genre>) result) {
            genreOL.add(g);
        }
        saioa.getTransaction().commit();
        saioa.close();

        return genreOL;
    }

    public static boolean generoaAldatu(int genreId, String balioBerria) {
        try {
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            String qryString = "update Genre g set g.name='" + balioBerria + "' where g.genreId=" + genreId;
            Query query = saioa.createQuery(qryString);
            query.executeUpdate();
            saioa.getTransaction().commit();
            saioa.close();
            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }

    public static boolean generoaGehitu(Genre g) {
        try {
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            saioa.save(g);
            saioa.getTransaction().commit();
            saioa.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean generoaEzabatu(Genre g) {
        try {
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            Query query = saioa.createQuery("delete from Genre g where g.genreId=" + g.getGenreId());
            query.executeUpdate();
            saioa.getTransaction().commit();
            saioa.close();
            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }

    //TRACK
    public static ObservableList trackakKargatu() {
        ObservableList<Track> trackOL = FXCollections.observableArrayList();

        String sql = "SELECT trackId, track.name as name, album.title as albumName, genre.name as genreName from album inner join track on album.albumId = track.albumId inner join genre on genre.genreId = track.genreId";
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                trackOL.add(new Track(rs.getInt("trackId"),
                        rs.getString("name"),
                        rs.getString("albumName"),
                        rs.getString("genreName")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return trackOL;
    }

    public static boolean trackaAldatu(int trackId, String balioBerria) {
        String sql = "UPDATE track set name='?' where trackId = " + trackId;
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, balioBerria);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean trackaGehitu(Track t) {
        String sql = "Select albumId from album where title ='" + t.getAlbumName() + "'";    //Daukagun album izenarekin, album Id-a lortuko dugu
        String sql2 = "Select genreId from genre where name ='" + t.getGenreName() + "'";   //Daukagun genero izenarekin, genero Id-a lortuko dugu
        String azkenTrack = "select max(trackId) as azkena from track";  //Azken track + 1 egingo dugu, autoincrement ez delako
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            ResultSet rs2 = stmt.executeQuery(sql2);
            ResultSet rs3 = stmt.executeQuery(azkenTrack);
            if (rs.next() && rs2.next() && rs3.next()) {

                int albumId = rs.getInt("albumId");
                int genreId = rs2.getInt("genreId");
                int azkenTrackId = rs3.getInt("azkena");
                azkenTrackId = azkenTrackId + 1;

                String insertSql = "INSERT INTO Track (trackId, name, albumId, genreId, mediatypeId, milliseconds, unitprice) values (?,?,?,?, 1, 0, 0)";  //Track taulan trackId, izena, albumId eta genreId gehituko ditugu
                PreparedStatement pstmt = conn.prepareStatement(insertSql);
                pstmt.setInt(1, azkenTrackId);
                pstmt.setString(2, t.getName());
                pstmt.setInt(3, albumId);
                pstmt.setInt(4, genreId);
                pstmt.executeUpdate();
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean trackaEzabatu(Track t) {
        String sql = "DELETE from track where trackId = " + t.getTrackId();
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}

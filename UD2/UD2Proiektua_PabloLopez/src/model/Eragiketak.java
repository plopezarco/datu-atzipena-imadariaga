package model;

import global.Global;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.hibernate.query.Query;

public class Eragiketak {

    static Scanner in = new Scanner(System.in);

    public static SessionFactory sf = new Configuration().configure().buildSessionFactory();

    public static Connection connect() {

        String url = "jdbc:mysql://" + Global.ZERBITZARIA + "/" + Global.DATUBASEA;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, Global.getErabiltzailea(), Global.getPasahitza());
            //System.out.println("Konektatu zara");
        } catch (SQLException e) {
            System.out.println("Error Code:" + e.getErrorCode() + "-" + e.getMessage());
        }
        return conn;
    }

    //ALBUM
    public static ObservableList albumakKargatu() {
        ObservableList<Album> albumOL = FXCollections.observableArrayList();

        Session saioa = sf.openSession();
        saioa.beginTransaction();
        List result = saioa.createQuery("from Album").list();
        for (Album a : (List<Album>) result) {
            albumOL.add(a);
        }
        saioa.getTransaction().commit();
        saioa.close();

        return albumOL;
    }

    public static boolean albumaAldatu(int albumId, String zutabea, String balioBerria) {
        try {
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            String qryString = "update Album a set a." + zutabea + "='" + balioBerria + "' where a.albumId=" + albumId;
            Query query = saioa.createQuery(qryString);
            query.executeUpdate();
            saioa.getTransaction().commit();
            saioa.close();
            return true;
        } catch (HibernateException ex) {
            return false;
        }
    }

    public static boolean albumGehitu(Album a) {
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

    public static boolean albumaEzabatu(Album a) {
        try {
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            Query query = saioa.createQuery("delete from Album a where a.albumId=" + a.getAlbumId());
            query.executeUpdate();
            saioa.getTransaction().commit();
            saioa.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
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

    /*public static void getAlbumbyArtist() {
        ArrayList<Integer> artistId = new ArrayList();
        ArrayList<ArrayList<Album>> albumak = new ArrayList();

        SessionFactory sf = new Configuration().configure().buildSessionFactory();

        Session saioa = sf.openSession();
        List result = saioa.createQuery("from Album").list();

        for (Album a : (List<Album>) result) {                      //Album guztietatik iteratzen du
            int artistIdIndex = artistId.indexOf(a.getArtistId());  //Zein artistarenaren id-a daukan
            if (artistIdIndex == -1) {                              //Oraindik agertu ez den artistaId-a
                if (a.getArtistId() == 0) {                         //Albumen taulako ArtistId zutabea beteta ez balego
                    artistId.add(0);
                } else {
                    artistId.add(a.getArtistId());
                }
                albumak.add(new ArrayList());
                albumak.get(albumak.size() - 1).add(a);
            } else {
                albumak.get(artistIdIndex).add(a);
            }
        }

        for (int artistIdIndex = 0; artistIdIndex < albumak.size(); artistIdIndex++) {
            int zenbat = 0;

            Query querySelect = saioa.createQuery("select a.name from Artist a where a.artistId =" + artistIdIndex);
            String izena = (String) querySelect.uniqueResult();

            System.out.println(izena + ":");

            for (Album a : albumak.get(artistIdIndex)) {
                System.out.printf("\t%s\n", a.getTitle());
                zenbat++;
            }
            System.out.println("------------------------------------------------------------");
            System.out.printf("\tGuztira, %s artistak: %d album ditu\n\n", izena, zenbat);
        }

        saioa.close();
    }*/
}

package exekutagarriak;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;
import model.Album;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import model.Artist;
import model.Genre;

public class OinarrizkoEragiketak {
    //conection
    public final static String ZERBITZARIA = "localhost";
    public final static String DATUBASEA = "chinook";
    
    static String url = "";
    
    public static String erabiltzailea = "root";
    public static String pasahitza = "Admin123";
    
    //
    static String opzioa;
    static String opzioa2;
    static String taula;
    static Scanner sc = new Scanner(System.in);

    public static SessionFactory sf = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {  
        menu();
    }

    public static void datuaGorde(String taula) {
        int artistid, albumid, genreid;
        String title, name, genreizena;

        if (taula.equals("Artist")) {

            System.out.print("Sartu id bat: ");
            artistid = sc.nextInt();
            System.out.print("Sartu izena bat: ");
            name = sc.next();

            //Artist artist=new Artist(276, "Jorge");
            try {
                Artist artist = new Artist(artistid, name);
                Session saioa = sf.openSession();
                saioa.beginTransaction();
                saioa.save(artist);
                saioa.getTransaction().commit();
                saioa.close();
            } catch (Exception e) {
                System.out.println("Datos Duplicados en el Artista");
            }

        } else if (taula.equals("Album")) {
            System.out.print("Sartu albunaren id: ");
            albumid = sc.nextInt();
            System.out.print("Sartu albunaren izena: ");
            title = sc.next();
            System.out.print("Sartu artistaren id: ");
            artistid = sc.nextInt();

            //Album album=new Album(348, "ASEREHE", 276);
            try {
                Album album = new Album(albumid, title, artistid);
                Session saioa = sf.openSession();
                saioa.beginTransaction();
                saioa.save(album);
                saioa.getTransaction().commit();
                saioa.close();
            } catch (Exception e) {
                System.out.println("Datos Duplicados en el Album");
            }

        } else if (taula.equals("Genre")) {
            System.out.print("Sartu generoaren id: ");
            genreid = sc.nextInt();
            System.out.print("Sartu generoaren izena: ");
            genreizena = sc.next();

            //Genre genre=new Genre(26, "Kpop");
            try {
                Genre genre = new Genre(genreid, genreizena);
                Session saioa = sf.openSession();
                saioa.beginTransaction();
                saioa.save(genre);
                saioa.getTransaction().commit();
                saioa.close();
            } catch (Exception e) {
                System.out.println("Datos Duplicados en el Genero");
            }

        }

        System.out.println();

    }

    public static void datuakIkusi(String taula) {

        Session saioa = sf.openSession();
        saioa.beginTransaction();
        List result = saioa.createQuery("from " + taula).list(); // HQL deitzen dan lengoaia idatziko dugu Querya
        if (taula.equals("Artist")) {
            for (Artist a : (List<Artist>) result) {
                System.out.println(a);
            }
        } else if (taula.equals("Album")) {
            for (Album a : (List<Album>) result) {
                System.out.println(a);
            }
        } else if (taula.equals("Genre")) {
            for (Genre a : (List<Genre>) result) {
                System.out.println(a);
            }
        }

        saioa.getTransaction().commit();
        saioa.close();

        System.out.println();

    }

    public static void datuakIkusiOrder(String taula) {
        String orden;
        System.out.println("Ascendente edo Descendente");
        System.out.print("Asc || Desc : ");
        orden = sc.next();

        if (taula.equals("Artist") && orden.equalsIgnoreCase("asc") || taula.equals("Artist") && orden.equalsIgnoreCase("desc")) {
            String hql = "from Artist order by Name " + orden;
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            List result4 = saioa.createQuery(hql).list(); // HQL deitzen dan lengoaia idatziko dugu Querya
            for (Artist q : (List<Artist>) result4) {
                System.out.println(q);
            }
            saioa.getTransaction().commit();
            saioa.close();
        } else if (taula.equals("Album") && orden.equalsIgnoreCase("asc") || taula.equals("Album") && orden.equalsIgnoreCase("desc")) {
            String hql = "from Album order by Title " + orden;
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            List result4 = saioa.createQuery(hql).list(); // HQL deitzen dan lengoaia idatziko dugu Querya
            for (Album q : (List<Album>) result4) {
                System.out.println(q);
            }
            saioa.getTransaction().commit();
            saioa.close();
        } else if (taula.equals("Genre") && orden.equalsIgnoreCase("asc") || taula.equals("Genre") && orden.equalsIgnoreCase("desc")) {
            String hql = "from Genre order by Name " + orden;
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            List result4 = saioa.createQuery(hql).list(); // HQL deitzen dan lengoaia idatziko dugu Querya
            for (Genre q : (List<Genre>) result4) {
                System.out.println(q);
            }
            saioa.getTransaction().commit();
            saioa.close();
        } else {
            System.out.println("Txarto sartu duzu");
        }

        System.out.println();

    }

    public static void datuakIkusiLike(String taula) {
        String eremua;
        System.out.print("Nola hasiko da ikusi nahi duzuna... :");
        eremua = sc.next();

        if (taula.equals("Artist")) {
            String hql = "from Artist WHERE Name LIKE '" + eremua + "%'";
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            List result5 = saioa.createQuery(hql).list(); // HQL deitzen dan lengoaia idatziko dugu Querya
            for (Artist w : (List<Artist>) result5) {
                System.out.println(w);
            }
            saioa.getTransaction().commit();
            saioa.close();

        } else if (taula.equals("Album")) {
            String hql = "from Album WHERE Title LIKE '" + eremua + "%'";
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            List result5 = saioa.createQuery(hql).list(); // HQL deitzen dan lengoaia idatziko dugu Querya
            for (Album w : (List<Album>) result5) {
                System.out.println(w);
            }
            saioa.getTransaction().commit();
            saioa.close();
        } else if (taula.equals("Genre")) {
            String hql = "from Genre WHERE Name LIKE '" + eremua + "%'";
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            List result5 = saioa.createQuery(hql).list(); // HQL deitzen dan lengoaia idatziko dugu Querya
            for (Genre w : (List<Genre>) result5) {
                System.out.println(w);
            }
            saioa.getTransaction().commit();
            saioa.close();
        }

        System.out.println();

    }

    public static void datuakEzabatu(String taula) {
        int id;

        try {
            if (taula.equals("Artist")) {
                System.out.print("Sartu id bat: ");
                id = sc.nextInt();

                Session saioa = sf.openSession();
                saioa.beginTransaction();

                //Artist artist = new Artist(276, "Jorge");  //Creamos el objeto
                Artist artist = new Artist(id, "name");
                saioa.delete(artist);

                saioa.getTransaction().commit();
                saioa.close();

            } else if (taula.equals("Album")) {
                System.out.print("Sartu albunaren id: ");
                id = sc.nextInt();

                Session saioa = sf.openSession();
                saioa.beginTransaction();

                //Album album = new Album(348, "Jorge", 276);  //Creamos el objeto
                Album album = new Album(id, "title", id);

                saioa.delete(album);

                saioa.getTransaction().commit();
                saioa.close();

            } else if (taula.equals("Genre")) {
                System.out.print("Sartu generoaren id: ");
                id = sc.nextInt();

                Session saioa = sf.openSession();
                saioa.beginTransaction();

                //Genre genre = new Genre(26, "Kpop");  //Creamos el objeto
                Genre genre = new Genre(id, "genreizena");

                saioa.delete(genre);

                saioa.getTransaction().commit();
                saioa.close();
            }

            System.out.println();

        } catch (Exception e) {
            System.out.println("Sartu duzu id okerra");
        }

    }

    public static void datuakAldatu(String taula) {
        int id, artistid;
        String berria;
        try{
             if (taula.equals("Artist")) {
            System.out.print("Aldatu nahi duzun idea : ");
            id = sc.nextInt();
            System.out.print("Datu berriak : ");
            berria = sc.next();

            Session saioa = sf.openSession();
            saioa.beginTransaction();

            Artist artist = new Artist(id, berria);  //Creamos el objeto

            saioa.update(artist);

            saioa.getTransaction().commit();
            saioa.close();

        } else if (taula.equals("Album")) {
            System.out.print("Aldatu nahi duzun idea : ");
            id = sc.nextInt();
            System.out.print("Datu berriak : ");
            berria = sc.next();
            System.out.print("Artistaren IDa: ");
            artistid = sc.nextInt();

            Session saioa = sf.openSession();
            saioa.beginTransaction();

            Album album = new Album(id, berria, artistid);  //Creamos el objeto

            saioa.update(album);

            saioa.getTransaction().commit();
            saioa.close();
        } else if (taula.equals("Genre")) {
            System.out.print("Aldatu nahi duzun idea : ");
            id = sc.nextInt();
            System.out.print("Datu berriak : ");
            berria = sc.next();

            Session saioa = sf.openSession();
            saioa.beginTransaction();

            Genre genre = new Genre(id, berria);  //Creamos el objeto

            saioa.update(genre);

            saioa.getTransaction().commit();
            saioa.close();

        }
        }catch(Exception e){
            System.out.println("Zerbait oker sartu duzu");
        }
       

    }

    public static void menu() {

        do {
            System.out.println("................Menu................");
            System.out.println("....................................");
            System.out.println("1 = Begiratu datuak");
            System.out.println("2 = Gehitu datuak");
            System.out.println("3 = Aldatu datuak");
            System.out.println("4 = Ezabatu datuak");
            System.out.println("5 = Datuak ordenatu");
            System.out.println("6 = Begiratu datuak zehatzagoa");
            System.out.println("7 = Begiratu datuak inner join");
            System.out.println("0 = Irten");
            System.out.print("Sartu zenbakia : ");

            opzioa = sc.next();

            System.out.println();

            if (opzioa.equals("1") || opzioa.equals("2") || opzioa.equals("3") || opzioa.equals("4") || opzioa.equals("5") || opzioa.equals("6")) {
                menu2();
            }

            if (opzioa.equals("1")) {

                datuakIkusi(taula);

            } else if (opzioa.equals("2")) {

                datuaGorde(taula);

            } else if (opzioa.equals("3")) {

                datuakAldatu(taula);

            } else if (opzioa.equals("4")) {

                datuakEzabatu(taula);

            } else if (opzioa.equals("5")) {

                datuakIkusiOrder(taula);

            } else if (opzioa.equals("6")) {

                datuakIkusiLike(taula);

            } else if (opzioa.equals("7")) {

                datuakInnerJoin();

            }else if (opzioa.equals("0")) {

                System.out.println("Isten ....");

            } else {
                System.out.println("Opzio okerra sartu duzu");
            }

        } while (!opzioa.equals("0"));

    }

    public static void menu2() {
        boolean ondo = false;
        //Bigarren menu bat egin behar dut
        do {
            System.out.println("....Taulak.....");
            System.out.println("...............");
            System.out.println("1 = Albuma");
            System.out.println("2 = Artista");
            System.out.println("3 = Generoa");
            System.out.print("Sartu zenbakia : ");

            opzioa2 = sc.next();

            System.out.println();

            if (opzioa2.equals("1")) {

                taula = "Album";
                ondo = true;

            } else if (opzioa2.equals("2")) {

                taula = "Artist";
                ondo = true;

            } else if (opzioa2.equals("3")) {

                taula = "Genre";
                ondo = true;

            } else {
                System.out.println("Opzio okerra sartu duzu");
                opzioa2 = "";
                ondo = false;
            }

        } while (ondo == false);

    }
    
    public static Connection konektatu() {
        Connection conn = null;
        try {

            url = "jdbc:mysql://" + ZERBITZARIA + "/" + DATUBASEA;
            
            // create a connection to the database
            
            conn = DriverManager.getConnection(url, erabiltzailea, pasahitza);
            
            //System.out.println("Konetatu zara");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public static void datuakInnerJoin(){
            
          
            String sql = "SELECT Album.AlbumId, Album.Title, Artist.Name FROM Album INNER JOIN Artist ON Album.ArtistId = Artist.ArtistId;";
        
        try (Connection conn = konektatu();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                
                
                System.out.printf("%10s %100s %100s",rs.getInt("AlbumId"),
                                   rs.getString("Title"),
                                   rs.getString("Name") + "\n");


            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " ==> " + e.getMessage());
        }
          
       
    }
}

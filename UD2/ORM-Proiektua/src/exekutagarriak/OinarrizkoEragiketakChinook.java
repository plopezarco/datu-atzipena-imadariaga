/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exekutagarriak;

import com.sun.xml.bind.v2.TODO;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityNotFoundException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import model.Artista;
import model.Genre;
import model.Album;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.tool.schema.ast.SqlScriptParserException;

/**
 *
 * @author maneru.mikel
 */
public class OinarrizkoEragiketakChinook {

    public static SessionFactory sf = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {

        menua();
    }

    public static void datuaGordeArtista() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sartu nahi duzun artistaren ID gehitzeko:");
        int id = sc.nextInt();
        System.out.println("Sartu nahi duzun artistaren IZENA gehitzeko:");
        String izena = sc.next();
        Artista m = new Artista(id, izena);
        try {
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            saioa.save(m);
            saioa.getTransaction().commit();
            saioa.close();
            System.out.println(id + "-arekin," + izena + " -izenarekin ondo gehitu da artist taulan!");
        } catch (ConstraintViolationException e) {
            System.out.println("Sartu duzun artistaren ID taulan dago!");
        }

    }

    public static void datuaGordeGenre() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sartu nahi duzun generoaren ID gehitzeko:");
        int id = sc.nextInt();
        System.out.println("Sartu nahi duzun generoaren IZENA gehitzeko:");
        String izena = sc.next();
        Genre m = new Genre(id, izena);
        try {
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            saioa.save(m);
            saioa.getTransaction().commit();
            saioa.close();
            System.out.println(id + "-arekin," + izena + " -izenarekin ondo gehitu da genre taulan!");
        } catch (ConstraintViolationException e) {
            System.out.println("Sartu duzun generoaren ID taulan dago!");
        }

    }

    public static void datuaGordeAlbum() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sartu nahi duzun album ID gehitzeko:");
        int id = sc.nextInt();
        System.out.println("Sartu nahi duzun album IZENA gehitzeko:");
        String izena = sc.next();
        System.out.println("Sartu nahi duzun artista ID asignatzeko:");
        int idArtist = sc.nextInt();
        Album m = new Album(id, izena, idArtist);
        try {
            Session saioa = sf.openSession();
            saioa.beginTransaction();
            saioa.save(m);
            saioa.getTransaction().commit();
            saioa.close();
            System.out.println(id + "-arekin," + izena + " -izenarekin ondo gehitu da album taulan!");
        } catch (ConstraintViolationException e) {
            System.out.println("Sartu duzun album ID taulan dago!");
        }

    }

    public static void datuakIkusiArtista() {

        Session saioa = sf.openSession();
        saioa.beginTransaction();
        List result = saioa.createQuery("from Artista").list();// HQL deitzen dan lengoaia idatziko dugu Querya ES EL NOMBRE DE LA KLASE
        System.out.println("Artista");
        System.out.println("------------------");
        for (Artista m : (List<Artista>) result) {
            System.out.printf("ID|%d,Izena:%s \n", m.getId(), m.getIzena());
        }
        saioa.getTransaction().commit();
        saioa.close();
    }

    public static void datuakIkusiAlbum() {
        Session saioa = sf.openSession();
        saioa.beginTransaction();

        List result2 = saioa.createQuery("from Album").list();// HQL deitzen dan lengoaia idatziko dugu Querya ES EL NOMBRE DE LA KLASE
        System.out.println("Album");
        System.out.println("------------------------------");
        for (Album j : (List<Album>) result2) {
            System.out.printf("ID:%d | Album Izena:%s | Artista ID:%d\n", j.getAlbumId(), j.getTitle(), j.getArtistId());
        }

        saioa.getTransaction().commit();
        saioa.close();
    }

    public static void datuakIkusiArtistaIzena() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sartu nahi duzun artistaren IZENA:");
        int id = 0;
        String izena = sc.next();
        Session saioa = sf.openSession();
        saioa.beginTransaction();
        List result = saioa.createQuery("from Artista").list();// HQL deitzen dan lengoaia idatziko dugu Querya ES EL NOMBRE DE LA KLASE
        System.out.println("Artista");
        System.out.println("------------------");
        for (Artista m : (List<Artista>) result) {
            if (izena.equalsIgnoreCase(m.getIzena())) {
                System.out.println("Aukeratu duzun artistaren datuak ==> ID: " + m.getId() + " Izena: " + m.getIzena());
            }
        }

        saioa.getTransaction().commit();
        saioa.close();
    }

    public static void datuakIkusiAlbumIzena() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sartu nahi duzun album IZENA:");
        int id = 0;
        String izena = sc.next();
        Session saioa = sf.openSession();
        saioa.beginTransaction();
        List result = saioa.createQuery("from Album").list();// HQL deitzen dan lengoaia idatziko dugu Querya ES EL NOMBRE DE LA KLASE
        System.out.println("Album");
        System.out.println("------------------");
        for (Album m : (List<Album>) result) {
            String[] s = m.getTitle().split(":");
            if (s[0].contains(izena)) {
                System.out.println("Aukeratu duzun artistaren datuak ==> " + "ID: " + m.getAlbumId() + " | Izena: " + s[0] + " | ArtistaID: " + m.getArtistId());
            }
        }

        saioa.getTransaction().commit();
        saioa.close();
    }

    public static void datuakIkusiAlfabetikoki() {
        Session saioa = sf.openSession();
        saioa.beginTransaction();
        List result = saioa.createQuery("from Artista ORDER BY NAME ASC").list();// HQL deitzen dan lengoaia idatziko dugu Querya ES EL NOMBRE DE LA KLASE
        System.out.println("Artista");
        System.out.println("------------------");
        for (Artista m : (List<Artista>) result) {
            System.out.printf("ID|%d,Izena:%s \n", m.getId(), m.getIzena());
        }
        saioa.getTransaction().commit();
        saioa.close();
    }

    public static void datuakIkusiGenre() {

        Session saioa = sf.openSession();
        saioa.beginTransaction();
        List result = saioa.createQuery("from Genre").list();// HQL deitzen dan lengoaia idatziko dugu Querya ES EL NOMBRE DE LA KLASE
        System.out.println("Genre");
        System.out.println("------------------");
        for (Genre m : (List<Genre>) result) {
            System.out.printf("ID|%d,Izena:%s \n", m.getGenreId(), m.getIzena());

        }

        saioa.getTransaction().commit();
        saioa.close();
    }

    public static void datuaEzabatuArtista() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sartu nahi duzun artistaren ID ezabatzeko:");
        int id = sc.nextInt();
        Session saioa = null;
        Artista ik = null;

        Transaction tx = null;
        try {
            saioa = sf.openSession();
            tx = saioa.beginTransaction();
            ik = (Artista) saioa.load(Artista.class, id);
            //ik = (Ikaslea)saioa.load("Ikaslea", idIkaslea); //horrela ere bai, ezta?
            //get metodoa antzekoa da baina ez du eszepziorik eragiten erregistroa existitzen ez bada.
            saioa.delete(ik);
            tx.commit();

            System.out.println(ik.getIzena() + " Artista ezabatu da datubasetik");
            saioa.close();
        } catch (EntityNotFoundException onfe) {
            System.out.println("Artista hori ez dago");
        }
    }

    public static void datuaEzabatuGenre() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sartu nahi duzun generoaren ID ezabatzeko:");
        int id = sc.nextInt();
        Session saioa = null;
        Genre ik = null;
        Transaction tx = null;
        try {
            saioa = sf.openSession();
            tx = saioa.beginTransaction();
            ik = (Genre) saioa.load(Genre.class, id);
            //ik = (Ikaslea)saioa.load("Ikaslea", idIkaslea); //horrela ere bai, ezta?
            //get metodoa antzekoa da baina ez du eszepziorik eragiten erregistroa existitzen ez bada.
            saioa.delete(ik);
            tx.commit();
            System.out.println(id + " Artistaren datuak ondo ezabatu dira!");
        } catch (EntityNotFoundException onfe) {
            System.out.println("Genero hori ez dago");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            saioa.close();
        }

    }

    public static void datuaEzabatuAlbum() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sartu nahi duzun album ID ezabatzeko:");
        int id = sc.nextInt();
        Session saioa = null;
        Album ik = null;
        Transaction tx = null;
        try {
            saioa = sf.openSession();
            tx = saioa.beginTransaction();
            ik = (Album) saioa.load(Album.class, id);
            //ik = (Ikaslea)saioa.load("Ikaslea", idIkaslea); //horrela ere bai, ezta?
            //get metodoa antzekoa da baina ez du eszepziorik eragiten erregistroa existitzen ez bada.
            saioa.delete(ik);
            tx.commit();
            System.out.println(id + " Albumen datuak ondo ezabatu dira!");
        } catch (EntityNotFoundException onfe) {
            System.out.println("Album hori ez dago");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            saioa.close();
        }

    }

    public static void datuaEguneratuGenre() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sartu nahi duzun generoaren ID aldatzeko:");
        int id = sc.nextInt();
        System.out.println(id + "Generoaren izen berria aldatzeko :");
        String izena = sc.next();
        Session saioa = null;
        Genre ik = null;
        Transaction tx = null;
        try {
            saioa = sf.openSession();
            tx = saioa.beginTransaction();
            ik = (Genre) saioa.load(Genre.class, id);
            ik.setIzena(izena);
            tx.commit();

            System.out.println(ik.getIzena() + " Genre hau aldatu da datubasetik. Horain: " + ik + " da.");
            saioa.close();
            System.out.println(id + " Generoaren datuak ondo aldatu dira!");
        } catch (EntityNotFoundException onfe) {
            System.out.println("Genero hori ez dago");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            saioa.close();
        }
    }

    public static void datuaEguneratuArtista() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sartu nahi duzun artistaren ID aldatzeko:");
        int id = sc.nextInt();
        System.out.println(id + "artistaren izen berria aldatzeko :");
        String izena = sc.next();
        Session saioa = null;
        Artista ik = null;
        Transaction tx = null;
        try {
            saioa = sf.openSession();
            tx = saioa.beginTransaction();
            ik = (Artista) saioa.load(Artista.class, id);
            ik.setIzena(izena);
            tx.commit();

            System.out.println(ik.getIzena() + " Artista hau aldatu da datubasetik. Horain: " + ik + " da.");
            saioa.close();
            System.out.println(id + " Artista datuak ondo aldatu dira!");
        } catch (EntityNotFoundException onfe) {
            System.out.println("Artista hori ez dago");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            saioa.close();
        }
    }

    public static void datuaEguneratuAlbum() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sartu nahi duzun album ID aldatzeko:");
        int id = sc.nextInt();
        System.out.println(id + "album izen berria aldatzeko :");
        String izena = sc.next();
        Session saioa = null;
        Album ik = null;
        Transaction tx = null;
        try {
            saioa = sf.openSession();
            tx = saioa.beginTransaction();
            ik = (Album) saioa.load(Album.class, id);
            ik.setTitle(izena);
            tx.commit();

            System.out.println(ik.getTitle() + " Album hau aldatu da datubasetik. Horain: " + ik + " da.");
            saioa.close();
            System.out.println(id + " Album datuak ondo aldatu dira!");
        } catch (EntityNotFoundException onfe) {
            System.out.println("Album hori ez dago");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            saioa.close();
        }
    }

    private static void datuakIkusiGuztira() {
        Session saioa = sf.openSession();
        saioa.beginTransaction();
        List<Object[]> result = saioa.createQuery("from Album AS al INNER JOIN Artista AS art ON al.artistId = art.id").list();// HQL deitzen dan lengoaia idatziko dugu Querya ES EL NOMBRE DE LA KLASE;
        System.out.println("Album eta Arista");
        System.out.println("------------------------------------");
        for (Object[] aRow : result) {
            Album alb = (Album) aRow[0];
            Artista art = (Artista) aRow[1];
            System.out.printf("ID| %d, Album: %s, ID| %d, Izena:%s \n", alb.getAlbumId(), alb.getTitle(), art.getId(), art.getIzena());
        }

        saioa.getTransaction().commit();
        saioa.close(); //To change body of generated methods, choose Tools | Templates.
    }

    public static void datuakAlbumArtista() {
        ArrayList<Integer> artistId = new ArrayList();
        ArrayList<ArrayList<Album>> albumak = new ArrayList();

        SessionFactory sf = new Configuration().configure().buildSessionFactory();

        Session saioa = sf.openSession();
        List result = saioa.createQuery("from Album").list();

        for (Album a : (List<Album>) result) { //taula Album bakoitzagaitik
            int artistIdIndex = artistId.indexOf(a.getArtistId()); //Zein Artistarena den
            if (artistIdIndex == -1) { //oraindik agertu ez den probintzia
                if (a.getArtistId() == 0) { //albumen taulako artistId zutabea beteta ez balego
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
        //saioa.getTransaction().commit();
        saioa.close();

        for (int artistIdIndex = 0; artistIdIndex < albumak.size(); artistIdIndex++) {
            int zenbat = 0;
            System.out.println(artistId.get(artistIdIndex));
            for (Album a : albumak.get(artistIdIndex)) {
                System.out.printf("%-18s\n", a.getTitle());
                zenbat++;
            }
            System.out.println("-------------------------------");
            System.out.printf("Guztira, %d artistak: %d album ditu\n\n", artistId.get(artistIdIndex), zenbat);
        }
    }

    public static void menua() {
        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int aukera1;
        int aukera2;//Guardaremos la opcion del usuario
        while (!salir) {
            System.out.println("\n\nCHINOOK DATABASE");
            System.out.println("|---------------------------|");
            System.out.println("| 1.-Artista tabla aukeratu |");
            System.out.println("| 2.-Album tabla aukeratu   |");
            System.out.println("| 3.-Genre tabla aukeratu   |");
            System.out.println("| 4.-Irten                  |");
            System.out.println("|---------------------------|");
            try {
                System.out.println("Aukeratu bat:");
                aukera1 = sn.nextInt();
                switch (aukera1) {
                    case 1:
                        System.out.println("Artista taula aukeratu duzu!");
                        System.out.println("--------------------------------------------|");
                        System.out.println("Artista taulan zer nahi duzu egin           |");
                        System.out.println("1.-Datuak guztiak ikusi                     |");
                        System.out.println("2.-Datuak bilatu IZENA-gaitik               |");
                        System.out.println("3.-Datuak Ezabatu                           |");
                        System.out.println("4.-Datuak Insertatu                         |");
                        System.out.println("5.-Datuak Alfabetikoki ordenatu             |");
                        System.out.println("6.-Datuak Aldatu                            |");
                        System.out.println("7.-Artista bakoitzeko album kopurua ikusi   |");
                        System.out.println("--------------------------------------------|");
                        System.out.println("Sartu Aukera:");
                        aukera2 = sn.nextInt();
                        switch (aukera2) {
                            case 1:
                                datuakIkusiArtista();
                                break;
                            case 2:
                                datuakIkusiArtistaIzena();
                                break;
                            case 3:
                                datuaEzabatuArtista();
                                break;
                            case 4:
                                datuaGordeArtista();
                                break;
                            case 5:
                                datuakIkusiAlfabetikoki();
                                break;
                            case 6:
                                datuaEguneratuArtista();
                                break;
                            case 7:
                                datuakAlbumArtista();
                                break;
                            default:
                                System.out.println("Sartu dauden aukeraren bat");
                        }

                        break;

                    case 2:
                        System.out.println("Album taula aukeratu duzu!");
                        System.out.println("-----------------------------------------------------------|");
                        System.out.println("Album taulan zer nahi duzu egin:                           |");
                        System.out.println("1.-Datuak Ikusi                                            |");
                        System.out.println("2.-Datuak Ezabatu                                          |");
                        System.out.println("3.-Datuak Insertatu                                        |");
                        System.out.println("4.-Datuak Aldatu                                           |");
                        System.out.println("5.-Titulua bilatu (Titulua izena bakarrik jarri ahal duzu) |");
                        System.out.println("6.-Datuak Album eta Artista ikusi                          |");
                        System.out.println("-----------------------------------------------------------|");
                        System.out.println("Sartu Aukera:");
                        aukera2 = sn.nextInt();
                        switch (aukera2) {
                            case 1:
                                datuakIkusiAlbum();
                                break;
                            case 2:
                                datuaEzabatuAlbum();
                                break;
                            case 3:
                                datuaGordeAlbum();
                                break;
                            case 4:
                                datuaEguneratuAlbum();
                                break;
                            case 5:
                                datuakIkusiAlbumIzena();
                                break;
                            case 6:
                                datuakIkusiGuztira();
                            default:
                                System.out.println("Sartu dauden aukeraren bat!");
                        }
                        break;
                    case 3:
                        System.out.println("Genre taula aukeratu duzu!");
                        System.out.println("-----------------------------------|");
                        System.out.println("Genre taulan zer nahi duzu egin:   |");
                        System.out.println("1.-Datuak Ikusi                    |");
                        System.out.println("2.-Datuak Ezabatu                  |");
                        System.out.println("3.-Datuak Insertatu                |");
                        System.out.println("4.-Datuak Aldatu                   |");
                        System.out.println("-----------------------------------|");
                        System.out.println("Sartu Aukera:");
                        aukera2 = sn.nextInt();
                        switch (aukera2) {
                            case 1:
                                datuakIkusiGenre();
                                break;
                            case 2:
                                datuaEzabatuGenre();
                                break;
                            case 3:
                                datuaGordeGenre();
                                break;
                            case 4:
                                datuaEguneratuGenre();
                                break;
                            default:
                                System.out.println("Sartu dauden aukeraren bat!");
                        }
                        break;
                    case 4:
                        salir = true;
                        System.out.println("Eskerrik asko nire programa erabiltzeagatik!");
                        System.out.println("AGUR!");

                        break;
                    default:
                        System.out.println("Sartu dauden aukeraren bat");
                }
            } catch (InputMismatchException e) {
                System.out.println("Zenbaki bat insertatu behar duzu!");
                sn.next();
            }
            System.out.println("========================================================================================================================");
        }
    }

}

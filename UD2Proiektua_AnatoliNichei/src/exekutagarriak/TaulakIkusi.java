/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exekutagarriak;

import static exekutagarriak.Main.in;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Even
 */
public class TaulakIkusi {

    static String dbName = "chinook";
    static String userName = "root";
    static String password = "root";
    static String url = "jdbc:mariadb://localhost/chinook";

    static Connection conn = null;
    static boolean loop = true;

    public static void taulakIkusiMenua() {
        int aukera = 99;
        loop = true;
        while (loop) {
            System.out.println(" +----------------------------------------------------------------------+");
            String name = centerString(70, "ONGI ETORRI TAULEN MENURA");
            System.out.println(" |" + name + "|");
            System.out.println(" +----------------------------------------------------------------------+");
            System.out.println(" | Taula baten datu guztiak ikusi                                    (1)|");
            System.out.println(" | Bilaketa espezifikoak:                                               |");
            System.out.println(" |      -Artista baten albumak bilatu                                (2)|");
            System.out.println(" |      -Album baten abesti guztiak bilatu                           (3)|");
            System.out.println(" |      -Genero baten abesti guztiak bilatu                          (4)|");
            System.out.println(" |      -Top X Abesti luzeenak                                       (5)|");
            System.out.println(" |      -Albumen prezioak                                            (6)|");
            System.out.println(" | Atzera                                                            (0)|");
            System.out.println(" +----------------------------------------------------------------------+");

            while (true) {
                try {
                    System.out.print("Sartu zenbakia: ");
                    aukera = in.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Mesdez, zenbaki bat sartu!");
                    in.next();
                }
            }

            in.nextLine();
            switch (aukera) {
                case 1:
                    System.out.println("");
                    taulaAukeratu();
                    TaulenKudeaketaJDBC.datuakIkusi(KudeaketaMenua.taula);
                    break;
                case 2:
                    System.out.println("");
                    artistaAlbum();
                    break;
                case 3:
                    System.out.println("");
                    trackAlbum();
                    break;
                case 4:
                    System.out.println("");
                    trackGenre();
                    break;
                case 5:
                    System.out.println("");
                    abestiLuzeenak();
                    break;
                case 6:
                    System.out.println("");
                    trackAlbumPrezio();
                    break;
                case 0:
                    loop = false;
                    break;
                default:
                    System.out.println("Zenbaki hori ez du funtziorik menuan, saiatu berriro.");
                    break;
            }
        }

    }

    public static void taulaAukeratu() {

        try {
            conn = DriverManager.getConnection(url, userName, password);
            DatabaseMetaData meta = conn.getMetaData();
            int count = 1;
            int aukera = 99;
            String strCount = "";
            ResultSet rs1 = meta.getTables(null, null, null, new String[]{"TABLE"});
            System.out.println(" +----------------------------------------------------------------------+");
            String out = centerString(70, "Aukeratu nahi duzun taula.");
            System.out.println(" |" + out + "|");
            System.out.println(" +----------------------------------------------------------------------+");
            while (rs1.next()) {
                if (!rs1.getString("TABLE_NAME").equals("users")) {
                    strCount = "(" + count + ")";
                    System.out.printf(" | %-64s %4s| \n", rs1.getString("TABLE_NAME").toUpperCase(), strCount);
                    count++;
                } else if (Main.userRole.equals("Admin")) {
                    strCount = "(" + count + ")";
                    System.out.printf(" | %-64s %4s| \n", rs1.getString("TABLE_NAME").toUpperCase(), strCount);
                    count++;
                }
            }
            System.out.println(" | ITZULI                                                            (0)|");
            System.out.println(" +----------------------------------------------------------------------+");
            System.out.println("");
            while (true) {
                try {
                    System.out.print("Sartu taulari dagokion zenbakia: ");
                    aukera = in.nextInt();
                    if (aukera < 0 || aukera > count - 1) {
                        System.out.println("Mesdez, agertzen den zenbaki bat sartu!");
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Mesdez, zenbaki bat sartu!");
                    in.next();
                }
            }
            count = 1;
            ResultSet rs1Picker = meta.getTables(null, null, null, new String[]{"TABLE"});
            while (rs1Picker.next()) {
                if (count == aukera) {
                    if (!rs1Picker.getString("TABLE_NAME").equals("users")) {
                        KudeaketaMenua.taula = rs1Picker.getString("TABLE_NAME");
                    } else if (Main.userRole.equals("Admin")) {
                        KudeaketaMenua.taula = rs1Picker.getString("TABLE_NAME");
                    }
                }
                count++;
            }
            System.out.println(KudeaketaMenua.taula + " taula aukeratu egin da.");
        } catch (SQLException ex) {
            Logger.getLogger(TaulakIkusi.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(TaulakIkusi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    private static void artistaAlbum() {
        try {
            boolean exists = false;
            conn = DriverManager.getConnection(url, userName, password);
            String artista = "";
            System.out.print("Sartu ezazu artistaren izena: ");
            artista = in.nextLine();

            System.out.println();
            Statement stmt = null;
            String query = "SELECT Album.Title FROM album INNER JOIN Artist ON Album.ArtistId = Artist.ArtistId WHERE Artist.Name = '" + artista + "'";
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                System.out.println(" +----------------------------------------------------------------------+");
                String out = centerString(70, "Hauek dira " + artista + " artistaren album guztiak");
                System.out.println(" |" + out + "|");

                System.out.println(" +----------------------------------------------------------------------+");
                while (rs.next()) {

                    String name = centerString(70, rs.getString("title"));
                    System.out.println(" |" + name + "|");
                    exists = true;
                }
                if (!exists) {
                    out = centerString(70, artista + " artista ez dauka albumik oraindik.");
                    System.out.println(" |" + out + "|");
                }
                System.out.println(" +----------------------------------------------------------------------+");
            } catch (SQLException e) {
                throw new Error("Problem", e);
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException ex) {
            throw new Error("Problem", ex);
        }
    }

    private static void trackGenre() {
        try {
            boolean exists = false;
            conn = DriverManager.getConnection(url, userName, password);
            String genre = "";
            System.out.print("Sartu ezazu generoaren izena: ");
            genre = in.nextLine();

            System.out.println();
            Statement stmt = null;
            String query = "SELECT Track.Name FROM track INNER JOIN Genre ON Track.GenreId = Genre.GenreId WHERE Genre.Name = '" + genre + "'";
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                System.out.println(" +----------------------------------------------------------------------+");
                String out = centerString(70, "Hauek dira " + genre + " generodun abesti guztiak");
                System.out.println(" |" + out + "|");

                System.out.println(" +----------------------------------------------------------------------+");
                while (rs.next()) {

                    String name = centerString(70, rs.getString("name"));
                    System.out.println(" |" + name + "|");
                    exists = true;
                }
                if (!exists) {
                    out = centerString(70, genre + " generoak ez dauka abestirikoraindik.");
                    System.out.println(" |" + out + "|");
                }
                System.out.println(" +----------------------------------------------------------------------+");
            } catch (SQLException e) {
                throw new Error("Problem", e);
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException ex) {
            throw new Error("Problem", ex);
        }
    }

    private static void trackAlbum() {
        try {
            boolean exists = false;
            conn = DriverManager.getConnection(url, userName, password);
            String album = "";
            System.out.print("Sartu ezazu albumaren izena: ");
            album = in.nextLine();

            System.out.println();
            Statement stmt = null;
            String query = "SELECT Track.Name, Track.UnitPrice FROM Track INNER JOIN Album ON Track.AlbumId = Album.AlbumId WHERE Album.Title = '" + album + "'";
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                System.out.println(" +-----------------------------------------------------------------------+");
                String out = centerString(71, "Hauek dira " + album + " albuma duen abezti guztiak");
                System.out.println(" |" + out + "|");
                System.out.println(" +-----------------------------------------------------------------------+");
                String name = centerString(35, "Abestia");
                String kop = centerString(35, "Prezioa unitateko");
                System.out.println(" |" + name + "|" + kop + "|");
                System.out.println(" +-----------------------------------------------------------------------+");
                while (rs.next()) {
                    name = centerString(35, rs.getString("name"));
                    kop = centerString(35, rs.getString("UnitPrice"));
                    System.out.println(" |" + name + "|" + kop + "|");
                    exists = true;
                }
                if (!exists) {
                    out = centerString(70, album + " albuma ez dauka abestirik edo ez da existitzen oraindik.");
                    System.out.println(" |" + out + "|");
                }
                System.out.println(" +-----------------------------------------------------------------------+");
            } catch (SQLException e) {
                throw new Error("Problem", e);
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException ex) {
            throw new Error("Problem", ex);
        }
    }

    private static void abestiLuzeenak() {
        try {
            conn = DriverManager.getConnection(url, userName, password);
            int top = 0;
            System.out.print("Sartu ezazu zenbat abesti agertzea nahi duzun: ");

            while (true) {
                try {
                    top = in.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Mesdez, zenbaki bat sartu!");
                    in.next();
                }
            }

            System.out.println();
            Statement stmt = null;
            String query = "SELECT Track.Name, Track.Milliseconds FROM Track ORDER BY Milliseconds DESC LIMIT " + top;
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                System.out.println(" +-----------------------------------------------------------------------+");
                String out = centerString(71, "Hauek dira TOP " + top + " abestirik luzeenak");
                System.out.println(" |" + out + "|");

                System.out.println(" +-----------------------------------------------------------------------+");

                String name = centerString(35, "Abestiaren Izena");
                String time = centerString(35, "Iraupena (ms-tan)");
                System.out.println(" |" + name + "|" + time + "|");
                System.out.println(" +-----------------------------------------------------------------------+");
                while (rs.next()) {
                    name = centerString(35, rs.getString("name"));
                    time = centerString(35, rs.getString("milliseconds"));
                    System.out.println(" |" + name + "|" + time + "|");
                }

                System.out.println(" +-----------------------------------------------------------------------+");
            } catch (SQLException e) {
                throw new Error("Problem", e);
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException ex) {
            throw new Error("Problem", ex);
        }
    }

    private static void trackAlbumPrezio() {
        try {
            conn = DriverManager.getConnection(url, userName, password);

            System.out.println();
            Statement stmt = null;
            String query = "SELECT Album.Title, SUM(Track.UnitPrice) AS NoOfTracks FROM Album INNER JOIN Track ON Album.AlbumId = Track.AlbumId GROUP BY Album.Title ORDER BY NoOfTracks DESC";
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                System.out.println(" +-----------------------------------------------------------------------+");
                String out = centerString(71, "Hauek dira albumen prezioa.");
                System.out.println(" |" + out + "|");
                System.out.println(" +-----------------------------------------------------------------------+");
                String name = centerString(35, "Albumaren izena");
                String kop = centerString(35, "Abesti guztien prezioen batura.");
                System.out.println(" |" + name + "|" + kop + "|");

                System.out.println(" +-----------------------------------------------------------------------+");
                while (rs.next()) {
                    name = centerString(35, rs.getString("Title"));
                    kop = centerString(35, rs.getString("NoOfTracks"));
                    System.out.println(" |" + name + "|" + kop + "|");
                }
                System.out.println(" +-----------------------------------------------------------------------+");
            } catch (SQLException e) {
                throw new Error("Problem", e);
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException ex) {
            throw new Error("Problem", ex);
        }
    }

    public static String centerString(int width, String s) {
        return String.format("%-" + width + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

}

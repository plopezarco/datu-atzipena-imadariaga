/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exekutagarriak;

import static exekutagarriak.Main.in;
import static exekutagarriak.TaulakIkusi.centerString;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.UnknownFormatConversionException;

/**
 *
 * @author Even
 */
public class TaulenKudeaketaJDBC {

    static String dbName = "chinook";
    static String userName = "root";
    static String password = "root";
    static String url = "jdbc:mariadb://localhost/chinook";

    static Connection conn = null;

    public static void taulenMenua() {
        int aukera = 99;
        boolean loop = true;
        System.out.println("Hasi baino lehen aukeratu taula bat:");
        TaulakIkusi.taulaAukeratu();
        while (loop) {
            System.out.println(" +----------------------------------------------------------------------+");
            String name = centerString(70, "Zer egin nahi duzu " + KudeaketaMenua.taula + " taularekin?");
            System.out.println(" |" + name + "|");
            System.out.println(" +----------------------------------------------------------------------+");
            System.out.println(" | Datuak ikusi                                                      (1)|");
            System.out.println(" | Datuak ezabatu                                                    (2)|");
            System.out.println(" | Datuak gehitu                                                     (3)|");
            System.out.println(" | Datuak eguneratu                                                  (4)|");
            System.out.println(" | Taula aldatu                                                      (5)|");
            System.out.println(" | Atzera                                                            (0)|");
            System.out.println(" +----------------------------------------------------------------------+");

            while (true) {
                try {
                    System.out.print("Sartu zenbakia: ");
                    aukera = in.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Mesedez, zenbaki bat sartu!");
                    in.next();
                }
            }
            switch (aukera) {
                case 1:
                    datuakIkusi(KudeaketaMenua.taula);
                    break;
                case 2:
                    datuaEzabatuMenua(KudeaketaMenua.taula);
                    break;
                case 3:
                    datuaGorde(KudeaketaMenua.taula);
                    break;
                case 4:
                    datuaEguneratuMenua(KudeaketaMenua.taula);
                    break;
                case 5:
                    TaulakIkusi.taulaAukeratu();
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

    private static void datuaEguneratuMenua(String tableName) {
        int aukera = 99;
        boolean loop = true;
        while (loop) {
            System.out.println(" +----------------------------------------------------------------------+");
            String name = centerString(70, "ONGI ETORRI EGUNERAKETA MENURA");
            System.out.println(" |" + name + "|");
            System.out.println(" +----------------------------------------------------------------------+");
            System.out.println(" | Eremu bakar bat aldatu                                            (1)|");
            System.out.println(" | Eremu guztiak aldatu                                              (2)|");
            //System.out.println(" | Eguneraketa espezifikoak:                                            |");
            //System.out.println(" |      -Album baten abesti guztien prezioa aldatu                   (3)|");
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
            switch (aukera) {
                case 1:
                    System.out.println("");
                    datuaEguneratuEremuBat(tableName);
                    break;
                case 2:
                    System.out.println("");
                    datuaEguneratuOsoa(tableName);
                    break;
                /*case 3:
                    System.out.println("");
                    prezioaAldatu();
                    break;*/
                case 0:
                    loop = false;
                    break;
                default:
                    System.out.println("Zenbaki hori ez du funtziorik menuan, saiatu berriro.");
                    break;
            }
        }
    }

    private static void datuaGorde(String tableName) {
        try {
            conn = DriverManager.getConnection(url, userName, password);
            Statement stmt = null;
            String query = "SELECT * FROM " + tableName;
            String input;
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();
                int column_count = rsmd.getColumnCount();

                String sql = "INSERT INTO " + tableName + " VALUES (";
                in.nextLine();
                for (int i = 1; column_count > i - 1; i++) {
                    System.out.println("Sartu " + tableName + " berriaren " + rsmd.getColumnName(i) + " balorea. (" + rsmd.getColumnTypeName(i) + ") Formatuan mesedez.");
                    input = in.nextLine();
                    if (rsmd.getColumnTypeName(i).equals("VARCHAR")) {
                        sql += "'" + input + "'";
                    } else {
                        sql += input;
                    }
                    if (i != column_count) {
                        sql += ", ";
                    }
                }
                sql += ")";
                try {
                    stmt.executeUpdate(sql);
                } catch (SQLIntegrityConstraintViolationException ex) {
                    System.out.println("Primary Key Hori badago datu basean!!");
                } catch (SQLSyntaxErrorException e) {
                    System.out.println("Datu guztiak eskatutako formatuan bete itzazu!");
                }
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

    public static void datuakIkusi(String tableName) {
        try {
            conn = DriverManager.getConnection(url, userName, password);
            Statement stmt = null;
            String query = "SELECT * FROM " + tableName;
            String row = " | ";

            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();
                int column_count = rsmd.getColumnCount();

                printLine(column_count);
                String out = centerString(column_count * 27 - 1, "HONA HEMEN " + tableName.toUpperCase() + " TAULAREN DATUAK");
                System.out.println(" | " + out + " |");
                printLine(column_count);

                for (int i = 1; column_count > i - 1; i++) {
                    row = centerString(25, rsmd.getColumnName(i));
                    System.out.printf(" | " + row);
                }
                System.out.println(" |");
                printLine(column_count);
                while (rs.next()) {
                    for (int i = 1; column_count > i - 1; i++) {
                        try {
                            row = centerString(25, rs.getString(i));
                            System.out.printf(" | " + row);
                        } catch (NullPointerException npe) {
                            row = centerString(25, "-");
                            System.out.printf(" | " + row);
                        } catch (UnknownFormatConversionException e) {
                            row = centerString(25, "-");
                            System.out.printf(" | " + row);
                        }

                    }
                    System.out.println(" | ");
                }
                printLine(column_count);
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

    private static void datuaEzabatuMenua(String tableName) {
        int aukera = 99;
        boolean loop = true;
        while (loop) {
            System.out.println(" +----------------------------------------------------------------------+");
            String name = centerString(70, "ONGI ETORRI EZABAKETA MENURA");
            System.out.println(" |" + name + "|");
            System.out.println(" +----------------------------------------------------------------------+");
            System.out.println(" | Eremu bakar bat ezabatu                                           (1)|");
            System.out.println(" | Erregistro bat ezabatu                                            (2)|");
            //System.out.println(" | Eguneraketa espezifikoak:                                            |");
            //System.out.println(" |      -Album baten abesti guztien prezioa aldatu                   (3)|");
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
            switch (aukera) {
                case 1:
                    System.out.println("");
                    datuaEzabatuEremuBat(tableName);
                    break;
                case 2:
                    System.out.println("");
                    datuaEzabatuOsoa(tableName);
                    break;
                /*case 3:
                    System.out.println("");
                    prezioaAldatu();
                    break;*/
                case 0:
                    loop = false;
                    break;
                default:
                    System.out.println("Zenbaki hori ez du funtziorik menuan, saiatu berriro.");
                    break;
            }
        }
    }

    private static void datuaEzabatuOsoa(String tableName) {
        try {
            conn = DriverManager.getConnection(url, userName, password);
            Statement stmt = null;
            String query = "SELECT * FROM " + tableName;
            String input;
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();
                int id;
                in.nextLine();
                System.out.println("Sartu ezabatu nahi duzun erregistroaren ID-a:");
                while (true) {
                    try {
                        id = in.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Mesdez, zenbaki bat sartu!");
                        in.next();
                    }
                }
                String sql = "DELETE FROM " + tableName + " WHERE " + rsmd.getColumnName(1) + " = " + id;
                try {
                    System.out.println(sql);
                    stmt.execute(sql);
                } catch (SQLSyntaxErrorException e) {
                    System.out.println("Datu guztiak eskatutako formatuan bete itzazu!");
                }
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        } catch (SQLException ex) {
            throw new Error("Problem", ex);
        }
    }

    private static void datuaEzabatuEremuBat(String tableName) {
        try {
            conn = DriverManager.getConnection(url, userName, password);
            Statement stmt = null;
            String query = "SELECT * FROM " + tableName;
            String input;
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();
                int column_count = rsmd.getColumnCount();
                int id, aukera;
                String sql = "UPDATE " + tableName + " SET ";

                in.nextLine();
                String strCount = "";
                int count = 1;

                while (true) {
                    try {
                        System.out.println("Sartu ezabatu nahi duzun " + tableName + "-ren ID-a:");
                        aukera = in.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Mesdez, zenbaki bat sartu!");
                        in.next();
                    }
                }

                System.out.println(" +----------------------------------------------------------------------+");
                String out = centerString(70, "Aukeratu ezabatu nahi duzun eremua.");
                System.out.println(" |" + out + "|");
                System.out.println(" +----------------------------------------------------------------------+");
                for (int i = 1; column_count > i - 1; i++) {
                    strCount = "(" + count + ")";
                    System.out.printf(" | %-64s %4s| \n", rsmd.getColumnName(i).toUpperCase(), strCount);
                    count++;
                }
                System.out.println(" +----------------------------------------------------------------------+");
                while (true) {
                    try {
                        System.out.println("Sartu zenbaki bat:");
                        id = in.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Mesdez, zenbaki bat sartu!");
                        in.next();
                    }
                }
                if (rsmd.getColumnTypeName(id).equals("VARCHAR")) {
                    sql += rsmd.getColumnName(id) + "='-'";
                } else {
                    sql += rsmd.getColumnName(id) + "= 0";
                }

                sql += " WHERE " + rsmd.getColumnName(1) + "=" + aukera;
                try {
                    stmt.executeUpdate(sql);
                } catch (SQLIntegrityConstraintViolationException ex) {
                    System.out.println("Primary Key Hori badago datu basean!!");
                } catch (SQLSyntaxErrorException e) {
                    System.out.println("Datu guztiak eskatutako formatuan bete itzazu!");
                }
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

    private static void datuaEguneratuOsoa(String tableName) {
        try {
            conn = DriverManager.getConnection(url, userName, password);
            Statement stmt = null;
            String query = "SELECT * FROM " + tableName;
            String input;
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();
                int column_count = rsmd.getColumnCount();
                int id;
                String sql = "UPDATE " + tableName + " SET ";
                System.out.println("Sartu " + tableName + " taulan aldatu nahi duzun erregistroaren " + rsmd.getColumnName(1) + "-a");
                while (true) {
                    try {
                        id = in.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Mesdez, zenbaki bat sartu!");
                        in.next();
                    }
                }

                in.nextLine();
                for (int i = 2; column_count > i - 1; i++) {
                    System.out.println("Sartu " + rsmd.getColumnName(i) + "-ren balore berria. (" + rsmd.getColumnTypeName(i) + ") Formatuan mesedez.");
                    input = in.nextLine();
                    if (rsmd.getColumnTypeName(i).equals("VARCHAR")) {
                        sql += rsmd.getColumnName(i) + "='" + input + "'";
                    } else {
                        sql += rsmd.getColumnName(i) + "=" + input;
                    }
                    if (i != column_count) {
                        sql += ", ";
                    }
                }
                sql += " WHERE " + rsmd.getColumnName(1) + "=" + id;
                try {
                    stmt.executeUpdate(sql);
                } catch (SQLIntegrityConstraintViolationException ex) {
                    System.out.println("Kontuz erreferentziekin!!");
                } catch (SQLSyntaxErrorException e) {
                    System.out.println("Datu guztiak eskatutako formatuan bete itzazu!");
                }
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

    private static void datuaEguneratuEremuBat(String tableName) {
        try {
            conn = DriverManager.getConnection(url, userName, password);
            Statement stmt = null;
            String query = "SELECT * FROM " + tableName;
            String input;
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();
                int column_count = rsmd.getColumnCount();
                int id, aukera;
                String sql = "UPDATE " + tableName + " SET ";

                in.nextLine();
                String strCount = "";
                int count = 1;

                while (true) {
                    try {
                        System.out.println("Sartu aldatu nahi duzun " + tableName + "-ren ID-a:");
                        aukera = in.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Mesdez, zenbaki bat sartu!");
                        in.next();
                    }
                }

                System.out.println(" +----------------------------------------------------------------------+");
                String out = centerString(70, "Aukeratu aldatu nahi duzun eremua.");
                System.out.println(" |" + out + "|");
                System.out.println(" +----------------------------------------------------------------------+");
                for (int i = 1; column_count > i - 1; i++) {
                    strCount = "(" + count + ")";
                    System.out.printf(" | %-64s %4s| \n", rsmd.getColumnName(i).toUpperCase(), strCount);
                    count++;
                }
                System.out.println(" +----------------------------------------------------------------------+");
                while (true) {
                    try {
                        System.out.println("Sartu zenbaki bat:");
                        id = in.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Mesdez, zenbaki bat sartu!");
                        in.next();
                    }
                }
                in.nextLine();
                System.out.println("Sartu " + rsmd.getColumnName(id) + "-ren balore berria. (" + rsmd.getColumnTypeName(id) + ") Formatuan mesedez.");
                input = in.nextLine();
                if (rsmd.getColumnTypeName(id).equals("VARCHAR")) {
                    sql += rsmd.getColumnName(id) + "='" + input + "'";
                } else {
                    sql += rsmd.getColumnName(id) + "=" + input;
                }

                sql += " WHERE " + rsmd.getColumnName(1) + "=" + aukera;
                try {
                    stmt.executeUpdate(sql);
                } catch (SQLIntegrityConstraintViolationException ex) {
                    System.out.println("Primary Key Hori badago datu basean!!");
                } catch (SQLSyntaxErrorException e) {
                    System.out.println("Datu guztiak eskatutako formatuan bete itzazu!");
                }
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

    private static void erabiltzaileRola() {
        try {
            conn = DriverManager.getConnection(url, userName, password);
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                int id = 0, aukera;
                String sql = "UPDATE users SET ";

                in.nextLine();

                while (true) {
                    try {
                        System.out.println("Sartu aldatu nahi duzun erabiltzailearen ID-a:");
                        aukera = in.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Mesdez, zenbaki bat sartu!");
                        in.next();
                    }
                }

                System.out.println(" +----------------------------------------------------------------------+");
                String out = centerString(70, "Aukeratu zein rol jarri nahi diozun.");
                System.out.println(" |" + out + "|");
                System.out.println(" +----------------------------------------------------------------------+");
                System.out.println(" | Bisitaria                                                         (1)|");
                System.out.println(" | Moderadorea                                                       (2)|");
                System.out.println(" | Administradorea                                                   (3)|");
                System.out.println(" +----------------------------------------------------------------------+");

                while (true) {
                    try {
                        System.out.println("Sartu zenbaki bat:");
                        id = in.nextInt();
                        if (id >= 1 && id <= 3) {
                            break;
                        }
                        System.out.println("Mesedez balio duen zenbaki bat sartu.");
                    } catch (InputMismatchException e) {
                        System.out.println("Mesdez, zenbaki bat sartu!");
                        in.next();
                    }
                }

                switch (id) {
                    case 1:
                        sql += " Role = 'Bisitaria'";
                        break;
                    case 2:
                        sql += " Role = 'Moderadorea'";
                        break;
                    case 3:
                        sql += " Role = 'Admin'";
                        break;
                }

                sql += " WHERE UserId = " + aukera;
                try {
                    stmt.executeUpdate(sql);
                } catch (SQLIntegrityConstraintViolationException ex) {
                    System.out.println("Primary Key Hori badago datu basean!!");
                } catch (SQLSyntaxErrorException e) {
                    System.out.println("Datu guztiak eskatutako formatuan bete itzazu!");
                }
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

    public static void erabiltzaileakKudeatuMenua() {
        int aukera = 99;
        boolean loop = true;
        while (loop) {
            System.out.println(" +----------------------------------------------------------------------+");
            String name = centerString(70, "ONGI ETORRI ERABILTZAILEEN KUDEAKETA MENURA");
            System.out.println(" |" + name + "|");
            System.out.println(" +----------------------------------------------------------------------+");
            System.out.println(" | Erabiltzaile bat ezabatu                                          (1)|");
            System.out.println(" | Erabiltzaile baten rola aldatu                                    (2)|");
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
            switch (aukera) {
                case 1:
                    System.out.println("");
                    datuaEzabatuOsoa("users");
                    break;
                case 2:
                    System.out.println("");
                    erabiltzaileRola();
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

    public static void printLine(int width) {
        System.out.print(" +-");
        for (int i = 1; width * 27 > i; i++) {
            System.out.print("-");
        }
        System.out.println("-+");
    }

    /* public static void prezioaAldatu(){
        try {
            conn = DriverManager.getConnection(url, userName, password);
            String album = "";
            String price = "";
            System.out.print("Sartu ezazu albumaren izena: ");
            album = in.nextLine();
            in.nextLine();
            System.out.print("Sartu ezazu abestien prezio berria '.' batekin (Adibidez 2.99): ");
            price = in.next();
            Statement stmt = null;
            String query = "UPDATE Track INNER JOIN Album ON Track.AlbumId = Album.AlbumId SET Track.UnitPrice = " + price + " WHERE Album.Title = '" + album + "'";
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                throw new Error("Problem", e);
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
            System.out.println("Prezioa ondo eguneratu egin da.");
        } catch (SQLException ex) {
            throw new Error("Problem", ex);
        }
    } */
}

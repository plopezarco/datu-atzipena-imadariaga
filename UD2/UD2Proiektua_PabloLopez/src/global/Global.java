/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

/**
 *
 * @author lopez.pablo
 */
public class Global {

    public static String ZERBITZARIA = "localhost";
    public static String DATUBASEA = "chinook";
    private static String erabiltzailea = "root";
    private static String pasahitza = "root";

    public static String getErabiltzailea() {
        return erabiltzailea;
    }

    public static void setErabiltzailea(String erabiltzailea) {
        Global.erabiltzailea = erabiltzailea;
    }

    public static String getPasahitza() {
        return pasahitza;
    }

    public static void setPasahitza(String pasahitza) {
        Global.pasahitza = pasahitza;
    }
}

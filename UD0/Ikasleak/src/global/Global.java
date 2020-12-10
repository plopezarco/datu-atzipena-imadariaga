/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package global;

/**
 *
 * @author izarcelaya.aritz
 */
public class Global {
    
    public final static String ZERBITZARIA = "localhost";
    public final static String DATUBASEA = "ikasleak";
    
    private static String erabiltzailea = "root";
    private static String pasahitza = "Admin123";

    public static String getErabiltzailea() {
        return erabiltzailea;
    }

    public static String getPasahitza() {
        return pasahitza;
    }

    public static void setErabiltzailea(String erabiltzailea) {
        Global.erabiltzailea = erabiltzailea;
    }

    public static void setPasahitza(String pasahitza) {
        Global.pasahitza = pasahitza;
    }
    
    
}

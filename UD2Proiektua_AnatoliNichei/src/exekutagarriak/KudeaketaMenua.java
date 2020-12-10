/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exekutagarriak;

import static exekutagarriak.Main.in;
import static exekutagarriak.Main.sf;
import static exekutagarriak.Main.user;
import static exekutagarriak.TaulakIkusi.centerString;
import java.util.InputMismatchException;

/**
 *
 * @author Even
 */
public class KudeaketaMenua {

    public static String taula;

    public static void menua() {
        int aukera = 99;
        boolean loop = true;
        while (loop) {
            System.out.println(" +----------------------------------------------------------------------+");
            String name = centerString(70, "ONGI ETORRI DATUBASERA " + user.toUpperCase());
            System.out.println(" |" + name + "|");
            System.out.println(" +----------------------------------------------------------------------+");
            System.out.println(" | Taulak Ikusi                                                      (1)|");
            System.out.println(" | Erabiltzailea Aldatu                                              (2)|");
            if ("Moderadorea".equals(Main.userRole) || "Admin".equals(Main.userRole)) {
                System.out.println(" | Taulak Kudeatu                                                    (3)|");
                if ("Admin".equals(Main.userRole)) {
                    System.out.println(" | Erabiltzaileak Kudeatu                                            (4)|");
                }
            }
            System.out.println(" | Programatik Irten                                                 (0)|");
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
                    TaulakIkusi.taulakIkusiMenua();
                    break;
                case 2:
                    Login.loginMenua();
                    loop = false;
                    break;
                case 3:
                    if ("Moderadorea".equals(Main.userRole) || "Admin".equals(Main.userRole)) {
                        taulakKudeatu();
                        break;
                    } else {
                        System.out.println("Zenbaki hori ez du funtziorik menuan, saiatu berriro.");
                        break;
                    }
                case 4:
                    if ("Admin".equals(Main.userRole)) {
                        TaulenKudeaketaJDBC.erabiltzaileakKudeatuMenua();
                        break;
                    } else {
                        System.out.println("Zenbaki hori ez du funtziorik menuan, saiatu berriro.");
                        break;
                    }
                case 0:
                    sf.close();
                    in.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Zenbaki hori ez du funtziorik menuan, saiatu berriro.");
                    break;
            }
        }
    }

    private static void taulakKudeatu() {

        int aukera = 99;
        boolean loop = true;
        while (loop) {
            System.out.println(" +----------------------------------------------------------------------+");
            String name = centerString(70, "Zer erabili nahi duzu?");
            System.out.println(" |" + name + "|");
            System.out.println(" +----------------------------------------------------------------------+");
            System.out.println(" | JDBC                                                              (1)|");
            System.out.println(" | ORM (Generoak soikik)                                             (2)|");
            System.out.println(" | Itzuli                                                            (0)|");
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
                    TaulenKudeaketaJDBC.taulenMenua();
                    break;
                case 2:
                    TaulenKudeaketaORM.taulenMenua();
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

}

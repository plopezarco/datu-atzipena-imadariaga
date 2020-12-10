/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exekutagarriak;

import static exekutagarriak.Main.in;
import static exekutagarriak.Main.sf;
import static exekutagarriak.TaulakIkusi.centerString;
import java.util.InputMismatchException;
import java.util.List;
import model.User;
import org.hibernate.Session;

/**
 *
 * @author Even
 */
public class Login {

    private static int aukera = 99;
    private static boolean loop;

    public static void loginMenua() {
        loop = true;
        while (loop) {
            System.out.println(" +----------------------------------------------------------------------+");
            String name = centerString(70, "ONGI ETORRI CHINOOK DATUBASERA");
            System.out.println(" |" + name + "|");
            System.out.println(" +----------------------------------------------------------------------+");

            System.out.println(" | Kontu bat daukat                                                  (1)|");
            System.out.println(" | Kontu berri bat sortu                                             (2)|");
            System.out.println(" | Irten                                                             (0)|");
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
                    login();
                    break;
                case 2:
                    System.out.println("");
                    erabiltzaileBerria();
                    break;
                case 0:
                    sf.close();
                    in.close();
                    System.out.println("Ondo ibili!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Zenbaki hori ez du funtziorik menuan, saiatu berriro.");
                    break;
            }
        }

        KudeaketaMenua.menua();

    }

    private static void login() {
        boolean flag = false;
        String username, password;
        System.out.println("Sartu ezazu zure erabiltzailea: ");
        username = in.next();
        Session saioa = sf.openSession();
        saioa.beginTransaction();
        List result = saioa.createQuery("from User").list(); // "Album" Klasearen izena izan behar da!!
        for (User m : (List<User>) result) {
            if (m.getIzena().equals(username)) {
                System.out.println("Sartu ezazu zure pasahitza: ");
                password = in.next();
                if (m.getPasahitza().equals(password)) {
                    Main.userRole = m.getRola();
                    Main.user = m.getIzena();
                    flag = true;
                }
            }
        }
        saioa.getTransaction().commit();
        saioa.close();
        if (flag) {
            loop = false;
        } else {
            System.out.println("Erabiltzailea edo pasahitza txarto sartu egin dituzu.");
        }
    }

    private static void erabiltzaileBerria() {
        User erabiltzailea = new User();
        boolean flag = true;
        String username, password;
        System.out.println("Sartu zure erabiltzailearen izena: ");
        username = in.next();
        Session saioa = sf.openSession();
        saioa.beginTransaction();
        List result = saioa.createQuery("from User").list(); // "Album" Klasearen izena izan behar da!!
        for (User m : (List<User>) result) {
            if (m.getIzena().equals(username)) {
                System.out.println("Izen hori duen erabiltzailea existitzen da.");
                flag = false;
            }
        }
        if (flag) {
            System.out.println("Sartu zure erabiltzailearen pasahitza: ");
            password = in.next();
            erabiltzailea.setIzena(username);
            erabiltzailea.setPasahitza(password);
            erabiltzailea.setRola("Bisitaria");
            saioa.save(erabiltzailea);
            Main.userRole = erabiltzailea.getRola();
            Main.user = erabiltzailea.getIzena();
            loop = false;
        }
        saioa.getTransaction().commit();
        saioa.close();
    }
}

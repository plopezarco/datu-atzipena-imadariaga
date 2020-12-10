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
import model.Genre;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Even
 */
public class TaulenKudeaketaORM {

    public static void taulenMenua() {
        int aukera = 99;
        boolean loop = true;

        while (loop) {
            System.out.println(" +----------------------------------------------------------------------+");
            String name = centerString(70, "ONGI ETORRI ORM GENRE TAULAREN KUDEAKETARA");
            System.out.println(" |" + name + "|");
            System.out.println(" +----------------------------------------------------------------------+");
            System.out.println(" | Generoak ikusi                                                    (1)|");
            System.out.println(" | Generoak ezabatu                                                  (2)|");
            System.out.println(" | Generoak gehitu                                                   (3)|");
            System.out.println(" | Generoak eguneratu                                                (4)|");
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
                    datuakIkusi();
                    break;
                case 2:
                    datuaEzabatu();
                    break;
                case 3:
                    datuaGorde();
                    break;
                case 4:
                    datuaEguneratu();
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

    private static void datuaGorde() {
        boolean f = true;
        int idGenre = 0;
        String nameGenre = "";
        in.nextLine();
        System.out.println("Sartu jarri nahi diozun balore berria: ");
        nameGenre = in.nextLine();
        
        Genre g = new Genre(idGenre, nameGenre);
        Session saioa = sf.openSession();
        saioa.beginTransaction();
        saioa.save(g);
        saioa.getTransaction().commit();
        saioa.close();
    }

    private static void datuakIkusi() {
        System.out.println("");
        System.out.println(" +-----------------------------------------------------------------------+");
        Session session = sf.openSession();
        session.beginTransaction();
        String row = "";
        List result = session.createQuery("from Genre").list();
        for (Genre m : (List<Genre>) result) {
            row = centerString(70, m.toString());
            System.out.printf(" | " + row + " |\n");
        }
        session.getTransaction().commit();
        session.close();
        System.out.println(" +-----------------------------------------------------------------------+");
    }

    private static void datuaEzabatu() {
        datuakIkusi();
        System.out.println("Sartu ezabatu nahi duzun generoaren ID-a");
        int idGenre = 0;
        while (true) {
            try {
                System.out.print("Sartu zenbakia: ");
                idGenre = in.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Mesdez, zenbaki bat sartu!");
                in.next();
            }
        }
        Session saioa = null;
        Genre ik = null;
        Transaction tx = null;
        try {
            saioa = sf.openSession();
            tx = saioa.beginTransaction();
            ik = (Genre) saioa.load(Genre.class, idGenre);
            //ik = (Ikaslea)saioa.load("Ikaslea", idIkaslea); //horrela ere bai, ezta?
            //get metodoa antzekoa da baina ez du eszepziorik eragiten erregistroa existitzen ez bada.
            saioa.delete(ik);
            tx.commit();
        } catch (ObjectNotFoundException onfe) {
            System.out.println("Genero hori ez dago");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            saioa.close();
        }

    }

    private static void datuaEguneratu() {
        boolean f = true;
        System.out.println("");
        datuakIkusi();
        int idGenre = 0;
        String nameGenre = "";
        System.out.println("Sartu aldatu nahi duzun generoaren ID-a");
        while (f) {
            try {
                System.out.print("Sartu zenbakia: ");
                idGenre = in.nextInt();
                f = false;
            } catch (InputMismatchException e) {
                System.out.println("Mesdez, zenbaki bat sartu!");
                in.next();
            }
        }
        in.nextLine();
        System.out.println("Sartu jarri nahi diozun balore berria: ");

        nameGenre = in.nextLine();
        Session saioa = null;
        Genre ik = null;
        Transaction tx = null;
        try {
            Genre g = new Genre(idGenre, nameGenre);
            saioa = sf.openSession();
            tx = saioa.beginTransaction();
            saioa.update(g);
            tx.commit();
        } catch (ObjectNotFoundException onfe) {
            System.out.println("Genero hori ez dago");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            saioa.close();
        }
    }

    /* public static void datuakIkusiProba(String tableName) {

        try {
            Class tableClass = Class.forName(tableName);
            Session session = sf.openSession();
            session.beginTransaction();
            List result = session.createQuery("from " + tableName).list();
            for (tableClass m : (List<tableClass>) result) {
                System.out.println(m);
            }
            session.getTransaction().commit();
            session.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TaulenKudeaketa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     */
}

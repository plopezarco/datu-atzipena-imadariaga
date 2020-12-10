package exekutagarriak;

import static exekutagarriak.Login.*;
import java.util.Scanner;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Main {

    public static SessionFactory sf = new Configuration().configure().buildSessionFactory();
    public static Scanner in = new Scanner(System.in);
    public static String user;
    public static String userRole;

    public static void main(String[] args) {
        loginMenua();
        //probaMultitable();

    }
}

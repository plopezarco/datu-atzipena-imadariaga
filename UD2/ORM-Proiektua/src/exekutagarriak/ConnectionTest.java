package exekutagarriak;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class ConnectionTest {
	public static void main(String[] args) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		System.out.println("Chinoooook!!!");
	}
}

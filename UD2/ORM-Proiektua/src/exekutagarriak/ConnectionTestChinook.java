/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exekutagarriak;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
public class ConnectionTestChinook {
	public static void main(String[] args) {
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		System.out.println("RiauRiau!!!");
	}
}

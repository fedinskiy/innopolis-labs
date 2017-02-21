package dbworker;

import databaseclasses.SendedEmail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;

/**
 * Created by fedinskiy on 21.02.17.
 */
public class HibernateHelper {
	private final SessionFactory sessionFactory;
	
	HibernateHelper() {
		File hibernateConfiguration = new File("lab2/src/main/resources/config/hibernate.cfg.xml");
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure(hibernateConfiguration) // configures settings from hibernate.cfg.xml
				.build();
		sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
	}
	public synchronized SendedEmail sendedEmail(){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		SendedEmail result = (SendedEmail) session.createQuery("from GroupDataDB").list().iterator().next();
		session.getTransaction().commit();
		session.close();
		return result;
	}
	
}

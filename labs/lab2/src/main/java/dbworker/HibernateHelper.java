package dbworker;

import databaseclasses.SendedEmail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fedinskiy on 21.02.17.
 */
public class HibernateHelper extends SQLHelper {
	private final SessionFactory sessionFactory;
	private Session session;
		
	HibernateHelper() {
		File hibernateConfiguration = new File("lab2/src/main/resources/config/hibernate.cfg.xml");
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure(hibernateConfiguration) // configures settings from hibernate.cfg.xml
				.build();
		sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		session = sessionFactory.openSession();
	}
	
	public synchronized SendedEmail sendedEmail() {
		SendedEmail result;
		
		getSession().beginTransaction();
		result = (SendedEmail) getSession().createQuery("from SendedEmail").list().iterator().next();
		getSession().getTransaction().commit();
		return result;
	}
	
	public synchronized void toSendEmail(SendedEmail newEmail) {
			getSession().beginTransaction();
			getSession().save(newEmail);
			getSession().getTransaction().commit();
	}
	
	@Override
	protected ResultSet getAllFromTable(String email) throws SQLException {
		return null;
	}
	
	private Session getSession() {
		if (null==session||!session.isOpen()){
			session=sessionFactory.openSession();
		}
		return session;
	}
	
	public void stopSession(){
		session.close();
		session=null;
	}
	@Override
	protected void finalize() throws Throwable {
		stopSession();
		sessionFactory.close();
	}
}

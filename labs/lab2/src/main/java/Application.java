import models.SendedEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import processors.sqltoxml.*;
import xmlclasses.Email;
import xmlclasses.ObjectFactory;

import javax.xml.bind.*;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static dbworker.SQLHelperFactory.getSQLHelper;


/**
 * Created by fedinskiy on 21.02.17.
 */
public class Application {
	private final static Logger logger = LogManager.getLogger
			(Application.class);
	
	public static void main(String[] args) {
		try {
			Thread[] threads = new Thread[4];
			
			threads[0] = new Thread(new XMLEmailCreator());
			threads[1] = new Thread(new XMLEmailTemplateCreator());
			threads[2] = new Thread(new XMLAdminCreator());
			threads[3] = new Thread(new XMLPersonCreator());
			
			for (Thread thread : threads) {
				thread.setDaemon(true);
				thread.start();
			}
			
			for (Thread thread : threads) {
				thread.join();
			}
			
			getSQLHelper().stopSession();
		} catch (RuntimeException ex) {
			logger.error(ex.getMessage(), ex);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private static void XMLCreatorTest() throws JAXBException, SQLException, DatatypeConfigurationException,
			IOException, IllegalAccessException {
		XMLCreator[] creator = new XMLCreator[4];
		creator[0] = new XMLEmailCreator();
		creator[1] = new XMLEmailTemplateCreator();
		creator[2] = new XMLAdminCreator();
		creator[3] = new XMLPersonCreator();
		
		for (XMLCreator xmlCreator : creator) {
			xmlCreator.createXMLs();
		}
	}
	
	private static void SQLCreatorTest() throws JAXBException, SQLException, DatatypeConfigurationException,
			IOException, IllegalAccessException {
		XMLCreator[] creator = new XMLCreator[4];
		creator[0] = new XMLEmailCreator();
		creator[1] = new XMLEmailTemplateCreator();
		creator[2] = new XMLAdminCreator();
		creator[3] = new XMLPersonCreator();
		
		for (XMLCreator xmlCreator : creator) {
			xmlCreator.createSQL();
		}
		//creator[1].createSQL();
	}
	
	private static void XMLtoSQL(File file) {
		JAXBContext jaxbContext = null;
		try {
			jaxbContext = JAXBContext.newInstance((xmlclasses.ObjectFactory.class));
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			
			SendedEmail email = new SendedEmail((xmlclasses.Email) JAXBIntrospector.getValue(unmarshaller.unmarshal
					(file)));
			final databaseclasses.SendedEmail toSQL = email.toSQL();
			getSQLHelper().toSQL(toSQL, getSQLHelper().getQueue(toSQL));
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private static void SQLtoXML(File out) {
		JAXBContext jaxbContext = null;
		try {
			ObjectFactory objectFactory;
			Marshaller marshaller;
			JAXBElement<Email> je;
			SendedEmail email;
			
			email = new SendedEmail(getSQLHelper().sendedEmail());
			objectFactory = new ObjectFactory();
			jaxbContext = JAXBContext.newInstance((objectFactory.getClass()));
			marshaller = jaxbContext.createMarshaller();
			
			je = objectFactory.createSendedEmail(email.toXML());
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(je, out);
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		} catch (DatatypeConfigurationException e) {
			logger.error(e.getMessage(), e);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		
	}
}

import models.SendedEmail;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.File;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static dbworker.SQLHelperFactory.getSQLHelper;


/**
 * Created by fedinskiy on 21.02.17.
 */
public class Application {
	private final static Logger logger = LogManager.getLogger
			(Application.class);
	public static void main(String[] args) {
		try {
			File file = new File("results/tst.xml");
			XMLtoSQL(file);
			getSQLHelper().stopSession();
		}catch (RuntimeException ex){
			logger.error(ex.getMessage(), ex);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
	//		getSQLHelper().stopSession();
		}
	}
	
	private static void XMLtoSQL(File file){
		JAXBContext jaxbContext = null;
		try {
			jaxbContext = JAXBContext.newInstance((xmlclasses.Email.class));
			Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();
			
			SendedEmail email = new SendedEmail((xmlclasses.Email) unmarshaller.unmarshal(file));
			getSQLHelper().toSendEmail(email.toSQL());
		} catch (JAXBException e) {
			logger.error(e.getMessage(),e);
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
		}
		
	}
	
	private static void SQLtoXML(File out){
		JAXBContext jaxbContext = null;
		try {
			SendedEmail email = new SendedEmail(getSQLHelper().sendedEmail());
			jaxbContext = JAXBContext.newInstance((xmlclasses.Email.class));
			
			Marshaller marshaller = jaxbContext.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(email.toXML(), out);
		} catch (JAXBException e) {
			logger.error(e.getMessage(),e);
		} catch (DatatypeConfigurationException e) {
			logger.error(e.getMessage(),e);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		
	}
}

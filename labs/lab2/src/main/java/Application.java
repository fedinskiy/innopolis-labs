import models.SendedEmail;

import javax.xml.bind.*;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xmlclasses.Email;
import xmlclasses.ObjectFactory;
import processors.sqltoxml.*;
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
			if(!file.exists()){
				file.createNewFile();
			}
			//XMLtoSQL(file);
			XMLCreatorTest();
			getSQLHelper().stopSession();
		}catch (RuntimeException ex){
			logger.error(ex.getMessage(), ex);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		} catch (DatatypeConfigurationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} finally {
	//		getSQLHelper().stopSession();
		}
	}
	
	private static void XMLCreatorTest() throws JAXBException, SQLException, DatatypeConfigurationException,
			IOException, IllegalAccessException {
		XMLCreator creator=new XMLEmailCreator();
		creator.createXMLs();
	}
	
	private static void XMLtoSQL(File file){
		JAXBContext jaxbContext = null;
		try {
			jaxbContext = JAXBContext.newInstance((xmlclasses.ObjectFactory.class));
			Unmarshaller unmarshaller=jaxbContext.createUnmarshaller();
			
			SendedEmail email = new SendedEmail((xmlclasses.Email) JAXBIntrospector.getValue(unmarshaller.unmarshal(file)));
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
			logger.error(e.getMessage(),e);
		} catch (DatatypeConfigurationException e) {
			logger.error(e.getMessage(),e);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		
	}
}

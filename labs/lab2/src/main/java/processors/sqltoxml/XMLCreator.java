package processors.sqltoxml;

import xmlclasses.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dbworker.SQLHelperFactory.getSQLHelper;

/**
 * Created by fedinskiy on 22.02.17.
 */
public abstract class XMLCreator {
	private final ObjectFactory objectFactory;
	private final JAXBContext jaxbContext;
	private final Marshaller marshaller;
	private final String FILENAME_TEMPLATE = "results/%1$s/tst%2$d.xml";
	
	public final String getFileName(int counter){
		return String.format(FILENAME_TEMPLATE, this.getDirectoryName(),counter);
	}
	public XMLCreator() throws JAXBException {
		objectFactory = new ObjectFactory();;
		jaxbContext = JAXBContext.newInstance((objectFactory.getClass()));
		marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);;
	}
	
	protected int getMaxSize(){
		return 10;
	}
	
	protected ObjectFactory getObjectFactory() {
		return objectFactory;
	}
	
	protected Marshaller getMarshaller() {
		return marshaller;
	}
	
	protected abstract String getDirectoryName();

	protected abstract ResultSet getResultSet() throws SQLException;
	public abstract void createXMLs() throws SQLException, JAXBException, DatatypeConfigurationException, IOException,
			IllegalAccessException;
	
}

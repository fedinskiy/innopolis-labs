package processors.sqltoxml;

import databaseclasses.SendedEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xmlclasses.Email;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dbworker.SQLHelperFactory.getSQLHelper;

/**
 * Created by fedinskiy on 22.02.17.
 */
public class XMLEmailCreator extends XMLCreator {
	private final static Logger logger = LogManager.getLogger
			(XMLPersonCreator.class);
	
	public XMLEmailCreator() throws JAXBException {
		super();
	}
	
	@Override
	protected String getDirectoryName() {
		return "emails";
	}
	
	@Override
	protected ResultSet getResultSet() throws SQLException {
		return getSQLHelper().allEmail();
	}
	
	@Override
	public void createXMLs() throws SQLException, JAXBException, DatatypeConfigurationException {
		
		JAXBElement<Email> je;
		int counter = 0;
		
		final ResultSet resultSet = getResultSet();
		createDirectory();
		while (resultSet.next() && counter < getMaxSize()) {
			final models.SendedEmail e = new models.SendedEmail(new databaseclasses.SendedEmail(resultSet));
			final File file = new File(getFileName(++counter));
			
			if (!createFile(file)) return;
			
			je = getObjectFactory().createSendedEmail(e.toXML());
			getMarshaller().marshal(je, file);
		}
	}
	
	@Override
	public void createSQL() throws SQLException, JAXBException, DatatypeConfigurationException, IOException,
			IllegalAccessException {
		try {
			File[] listfiles = getFiles();
			for (File file : listfiles) {
				models.SendedEmail email = new models.SendedEmail((xmlclasses.Email) JAXBIntrospector.getValue
						(getUnmarshaller()
								.unmarshal(file)));
				final SendedEmail toSQL = email.toSQL();
				getSQLHelper().toSQL(toSQL, getSQLHelper().getQueue(toSQL));
			}
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
}

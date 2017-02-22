package processors.sqltoxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xmlclasses.BitrixPerson;

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
public class XMLPersonCreator extends XMLCreator {
	private final static Logger logger = LogManager.getLogger
			(XMLPersonCreator.class);
	
	public XMLPersonCreator() throws JAXBException {
		super();
	}
	
	@Override
	protected String getDirectoryName() {
		return "persons";
	}
	
	@Override
	protected ResultSet getResultSet() throws SQLException {
		return getSQLHelper().allUsers();
	}
	
	@Override
	public void createXMLs() throws SQLException, JAXBException, DatatypeConfigurationException,
			IllegalAccessException {
		JAXBElement<BitrixPerson> je;
		int counter = 0;
		
		final ResultSet resultSet = getResultSet();
		createDirectory();
		while (resultSet.next() && counter < getMaxSize()) {
			final models.User e = new models.User(new databaseclasses.User(resultSet));
			final File file = new File(getFileName(++counter));
			
			if (!createFile(file)) return;
			je = getObjectFactory().createEnrollee(e.toXML());
			getMarshaller().marshal(je, file);
		}
	}
	
	@Override
	public void createSQL() throws SQLException, JAXBException, DatatypeConfigurationException, IOException,
			IllegalAccessException {
		try {
			File[] listfiles = getFiles();
			for (File file : listfiles) {
				models.User email = new models.User((xmlclasses.BitrixPerson) JAXBIntrospector.getValue
						(getUnmarshaller()
								.unmarshal(file)));
				final databaseclasses.User toSQL = email.toSQL();
				getSQLHelper().toSQL(toSQL, getSQLHelper().getQueue(toSQL));
			}
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
}

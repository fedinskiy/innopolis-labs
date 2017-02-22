package processors.sqltoxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xmlclasses.EmailTemplate;

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
public class XMLEmailTemplateCreator extends XMLCreator {
	private final static Logger logger = LogManager.getLogger
			(XMLPersonCreator.class);
	
	public XMLEmailTemplateCreator() throws JAXBException {
		super();
	}
	
	@Override
	protected String getDirectoryName() {
		return "templates";
	}
	
	@Override
	protected ResultSet getResultSet() throws SQLException {
		return getSQLHelper().allEmailTemplates();
	}
	
	@Override
	public void createXMLs() throws SQLException, JAXBException, DatatypeConfigurationException,
			IllegalAccessException {
		JAXBElement<EmailTemplate> je;
		int counter = 0;
		
		final ResultSet resultSet = getResultSet();
		createDirectory();
		while (resultSet.next() && counter < getMaxSize()) {
			final models.EmailTemplate e = new models.EmailTemplate(new databaseclasses.EmailTemplate(resultSet));
			final File file = new File(getFileName(++counter));
			
			if (!createFile(file)) return;
			
			je = getObjectFactory().createEmailTemplate(e.toXML());
			getMarshaller().marshal(je, file);
		}
	}
	
	@Override
	public void createSQL() throws SQLException, JAXBException, DatatypeConfigurationException, IOException,
			IllegalAccessException {
		try {
			File[] listfiles = getFiles();
			for (File file : listfiles) {
				models.EmailTemplate email = new models.EmailTemplate((xmlclasses.EmailTemplate) JAXBIntrospector
						.getValue(getUnmarshaller()
								.unmarshal(file)));
				final databaseclasses.EmailTemplate toSQL = email.toSQL();
				getSQLHelper().toSQL(toSQL, getSQLHelper().getQueue(toSQL));
			}
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
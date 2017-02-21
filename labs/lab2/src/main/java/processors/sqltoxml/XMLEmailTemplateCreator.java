package processors.sqltoxml;

import xmlclasses.Email;
import xmlclasses.EmailTemplate;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
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
	public void createXMLs() throws SQLException, JAXBException, DatatypeConfigurationException, IOException,
			IllegalAccessException {
		JAXBElement<EmailTemplate> je;
		int counter = 0;
		
		final ResultSet resultSet = getResultSet();
		
		while (resultSet.next()&&counter<getMaxSize()) {
			final models.EmailTemplate e = new models.EmailTemplate(new databaseclasses.EmailTemplate(resultSet));
			final File file = new File(getFileName(++counter));
			
			if (!file.exists()){
				file.createNewFile();
			}
			
			je = getObjectFactory().createEmailTemplate(e.toXML());
			getMarshaller().marshal(je, file);
		}
	}
}

package processors.sqltoxml;

import xmlclasses.Email;
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
public class XMLEmailCreator extends XMLCreator{
	
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
	public void createXMLs() throws SQLException, JAXBException, DatatypeConfigurationException, IOException {
		JAXBElement<Email> je;
		int counter = 0;
	
		final ResultSet resultSet = getResultSet();
	
		while (resultSet.next()&&counter<getMaxSize()) {
			final models.SendedEmail e = new models.SendedEmail(new databaseclasses.SendedEmail(resultSet));
			final File file = new File(getFileName(++counter));
			
			if (!file.exists()){
				file.createNewFile();
			}
			
			je = getObjectFactory().createSendedEmail(e.toXML());
			getMarshaller().marshal(je, file);
		}
	}
}

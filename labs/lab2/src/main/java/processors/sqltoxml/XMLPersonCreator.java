package processors.sqltoxml;

import xmlclasses.BitrixPerson;
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
public class XMLPersonCreator extends XMLCreator {
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
	public void createXMLs() throws SQLException, JAXBException, DatatypeConfigurationException, IOException, IllegalAccessException {
		JAXBElement<BitrixPerson> je;
		int counter = 0;
		
		final ResultSet resultSet = getResultSet();
		
		while (resultSet.next()&&counter<getMaxSize()) {
			final models.User e = new models.User(new databaseclasses.User(resultSet));
			final File file = new File(getFileName(++counter));
			
			if (!file.exists()){
				file.createNewFile();
			}
			
			je = getObjectFactory().createEnrollee(e.toXML());
			getMarshaller().marshal(je, file);
		}
	}
}

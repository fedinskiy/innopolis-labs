package processors.sqltoxml;

import xmlclasses.Employee;

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
public class XMLAdminCreator extends XMLCreator {
	public XMLAdminCreator() throws JAXBException {
		super();
	}
	
	@Override
	protected String getDirectoryName() {
		return "admins";
	}
	
	@Override
	protected ResultSet getResultSet() throws SQLException {
		return getSQLHelper().allAdmins();
	}
	
	@Override
	public void createXMLs() throws SQLException, JAXBException, DatatypeConfigurationException, IOException,
			IllegalAccessException {
		JAXBElement<Employee> je;
		int counter = 0;
		
		final ResultSet resultSet = getResultSet();
		
		while (resultSet.next() && counter < getMaxSize()) {
			final models.Employee e = new models.Employee(new databaseclasses.Administrator(resultSet));
			final File file = new File(getFileName(++counter));
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			je = getObjectFactory().createEmployee(e.toXML());
			getMarshaller().marshal(je, file);
		}
	}
}

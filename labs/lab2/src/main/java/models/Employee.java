package models;

import javax.xml.datatype.DatatypeConfigurationException;
import java.sql.SQLException;

/**
 * Created by fedinskiy on 22.02.17.
 */
public class Employee extends BaseModel {
	
	protected long id;
	protected String firstname;
	protected String middlename;
	protected String lastname;
	protected String email;
	
	
	public Employee(xmlclasses.Employee xmlobject) throws DatatypeConfigurationException {
		super(xmlobject);
		
		firstname = xmlobject.getFirstname();
		middlename = xmlobject.getMiddlename();
		lastname = xmlobject.getLastname();
		email = xmlobject.getEmail();
	}
	
	public Employee(databaseclasses.Administrator dbClass) {
		super(dbClass);
		firstname = dbClass.getFirstname();
		middlename = dbClass.getMiddlename();
		lastname = dbClass.getLastname();
		email = dbClass.getEmail();
	}
	
	@Override
	public xmlclasses.Employee toXML() throws DatatypeConfigurationException {
		xmlclasses.Employee retval = new xmlclasses.Employee();
		
		retval.setId(null);
		retval.setEmail(getEmail());
		retval.setFirstname(getFirstname());
		retval.setMiddlename(getMiddlename());
		retval.setLastname(getLastname());
		return retval;
	}
	
	@Override
	public databaseclasses.Administrator toSQL() throws SQLException, IllegalAccessException {
		databaseclasses.Administrator administrator = new databaseclasses.Administrator();
		administrator.setEmail(getEmail());
		administrator.setFirstname(getFirstname());
		administrator.setMiddlename(getMiddlename());
		administrator.setLastname(getLastname());
		return administrator;
	}
	
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getMiddlename() {
		return middlename;
	}
	
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
}

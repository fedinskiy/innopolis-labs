package models;

import xmlclasses.BitrixPerson;

import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Created by fedinskiy on 22.02.17.
 */
public class User extends BaseModel {
	
	protected long id;
	protected long bitrixId;
	protected String firstname;
	protected String middlename;
	protected String lastname;
	protected String email;
	protected String phone;
	protected LocalDate birthdate;
	
	public User(xmlclasses.BitrixPerson xmlobject) throws DatatypeConfigurationException {
		super(xmlobject);
		
		bitrixId = xmlobject.getBitrixId().longValueExact();
		firstname = xmlobject.getFirstname();
		middlename = xmlobject.getMiddlename();
		lastname = xmlobject.getLastname();
		email = xmlobject.getEmail();
		phone = xmlobject.getPhone();
		birthdate = fromXMLDate(xmlobject.getBirthdate());
	}
	
	public User(databaseclasses.User dbClass) {
		super(dbClass);
		bitrixId = dbClass.getBitrixId();
		firstname = dbClass.getFirstname();
		middlename = dbClass.getMiddlename();
		lastname = dbClass.getLastname();
		email = dbClass.getEmail();
		phone = dbClass.getPhone();
		birthdate = dbClass.getBirthdate();
	}
	
	@Override
	public BitrixPerson toXML() throws DatatypeConfigurationException {
		BitrixPerson retval = new BitrixPerson();
		
		retval.setBirthdate(XMLDate(getBirthdate()));
		retval.setId(null);
		retval.setBitrixId(BigInteger.valueOf(getBitrixId()));
		retval.setEmail(getEmail());
		retval.setFirstname(getFirstname());
		retval.setMiddlename(getMiddlename());
		retval.setLastname(getLastname());
		retval.setPhone(getPhone());
		return retval;
	}
	
	@Override
	public databaseclasses.User toSQL() throws SQLException, IllegalAccessException {
		databaseclasses.User user = new databaseclasses.User();
		user.setBirthdate(getBirthdate());
		user.setBitrixId(getBitrixId());
		user.setEmail(getEmail());
		user.setFirstname(getFirstname());
		user.setMiddlename(getMiddlename());
		user.setLastname(getLastname());
		user.setPhone(getPhone());
		return user;
	}
	
	public long getBitrixId() {
		return bitrixId;
	}
	
	public void setBitrixId(long bitrixId) {
		this.bitrixId = bitrixId;
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
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public LocalDate getBirthdate() {
		return birthdate;
	}
	
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
}

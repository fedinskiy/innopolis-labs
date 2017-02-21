package databaseclasses;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Created by fedinskiy on 21.02.17.
 */

@Entity
@Table(name = "email")
public class User extends DBClass{
	
	protected long id;

	protected long bitrixId;
	protected String firstname;
	protected String middlename;
	protected String lastname;
	protected String email;
	protected String phone;
	protected LocalDate birthdate;
	
	public User() {
	}
	
	public User(ResultSet resultSet) throws SQLException {
		if(resultSet.next()){
			id=resultSet.getLong("id");
			bitrixId=resultSet.getLong("bitrixId");
			firstname=resultSet.getString("firstname");
			middlename=resultSet.getString("middlename");
			lastname=resultSet.getString("lastname");
			email=resultSet.getString("email");
			phone=resultSet.getString("phone");
			birthdate=resultSet.getDate("birthdate").toLocalDate();
		}
	}
	
	public PreparedStatement toStatement(PreparedStatement statement) throws SQLException {
		int order =0;
		statement.setLong(++order, bitrixId);
		statement.setString(++order, firstname);
		statement.setString(++order, middlename);
		statement.setString(++order, lastname);
		statement.setString(++order, email);
		statement.setString(++order, phone);
		statement.setDate(++order, java.sql.Date.valueOf(birthdate));
		
		return statement;
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

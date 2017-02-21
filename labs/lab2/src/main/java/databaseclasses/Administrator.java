package databaseclasses;

import javax.persistence.Entity;
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
public class Administrator extends DBClass{
	
	protected long id;
	
	protected String firstname;
	protected String middlename;
	protected String lastname;
	protected String email;
	
	public Administrator() {
	}
	
	public Administrator(ResultSet resultSet) throws SQLException {
		if(resultSet.next()){
			id=resultSet.getLong("id");
			firstname=resultSet.getString("firstname");
			middlename=resultSet.getString("middlename");
			lastname=resultSet.getString("lastname");
			email=resultSet.getString("email");
		}
	}
	
	public PreparedStatement toStatement(PreparedStatement statement) throws SQLException {
		int order =0;
		statement.setString(++order, firstname);
		statement.setString(++order, middlename);
		statement.setString(++order, lastname);
		statement.setString(++order, email);
		
		return statement;
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

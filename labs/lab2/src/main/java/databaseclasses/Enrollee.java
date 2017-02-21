package databaseclasses;

import javax.persistence.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fedinskiy on 21.02.17.
 */
@Entity
@javax.persistence.Table(name = "user")
public class Enrollee {
	@Id
	private long id;
	private String email;
	
	public Enrollee(ResultSet resultSet) throws SQLException {
		id =resultSet.getLong("user_id");
		email=resultSet.getString("email");
	}
	
	public String getEmail() {
		return email;
	}
	
	public long getId() {
		return id;
	}
}

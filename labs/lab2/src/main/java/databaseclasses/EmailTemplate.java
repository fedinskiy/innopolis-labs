package databaseclasses;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fedinskiy on 21.02.17.
 */
public class EmailTemplate extends DBClass {
	private Long id;
	private String name;
	private String content;
	private String subject;
	
	public EmailTemplate() {
		super();
	}
	
	public EmailTemplate(ResultSet resultSet) throws SQLException, IllegalAccessException {
		super(resultSet);
		id = resultSet.getLong("id");
		name = resultSet.getString("name");
		subject = resultSet.getString("subject");
		content = resultSet.getString("template");
	}
	
	@Override
	public PreparedStatement toStatement(PreparedStatement statement) throws SQLException {
		statement.setString(1, name);
		statement.setString(2, subject);
		statement.setString(3, content);
		
		return statement;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
}

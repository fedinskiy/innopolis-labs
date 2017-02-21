package databaseclasses;

import javax.persistence.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by fedinskiy on 21.02.17.
 */

@Entity
@javax.persistence.Table(name = "email")
public class SendedEmail extends DBClass{
	
	@Id
	private Long id;
	private Long user_id;
	private Long superuser_id;
	private Long template_id;
	private LocalDate sended_at;
	private String content;
	private String subject;

	
	public SendedEmail() {
	}
	
	public SendedEmail(ResultSet resultSet) throws SQLException {
			id=resultSet.getLong("id");
			user_id=resultSet.getLong("user_id");
			superuser_id=resultSet.getLong("superuser_id");
			template_id=resultSet.getLong("email_reason_id");
		sended_at=resultSet.getDate("sended_at").toLocalDate();
			subject=resultSet.getString("subject");
			content=resultSet.getString("content");
	}
	
	public PreparedStatement toStatement(PreparedStatement statement) throws SQLException {
		statement.setLong(1, user_id);
		statement.setLong(2, superuser_id);
		statement.setLong(3, template_id);
		statement.setDate(4, java.sql.Date.valueOf(sended_at));
		statement.setString(5, content);
		statement.setString(6, subject);
		
		return statement;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setSended_at(LocalDate sended_at) {
		this.sended_at = sended_at;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public LocalDate getSended_at() {
		return sended_at;
	}
		
}

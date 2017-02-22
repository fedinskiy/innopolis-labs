package databaseclasses;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Created by fedinskiy on 21.02.17.
 */

@Entity
@javax.persistence.Table(name = "email")
public class SendedEmail extends DBClass {
	
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
		id = resultSet.getLong("id");
		user_id = resultSet.getLong("user_id");
		superuser_id = resultSet.getLong("superuser_id");
		template_id = resultSet.getLong("email_reason_id");
		sended_at = resultSet.getDate("sended_at").toLocalDate();
		subject = resultSet.getString("subject");
		content = resultSet.getString("content");
	}
	
	@Override
	public PreparedStatement toStatement(PreparedStatement statement) throws SQLException {
		statement.setLong(1, user_id);
		statement.setLong(2, superuser_id);
		statement.setLong(3, template_id);
		statement.setDate(4, java.sql.Date.valueOf(sended_at));
		statement.setString(5, content);
		statement.setString(6, subject);
		
		return statement;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public LocalDate getSended_at() {
		return sended_at;
	}
	
	public void setSended_at(LocalDate sended_at) {
		this.sended_at = sended_at;
	}
	
	public Long getUser_id() {
		return user_id;
	}
	
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
	public Long getSuperuser_id() {
		return superuser_id;
	}
	
	public void setSuperuser_id(Long superuser_id) {
		this.superuser_id = superuser_id;
	}
	
	public Long getTemplate_id() {
		return template_id;
	}
	
	public void setTemplate_id(Long template_id) {
		this.template_id = template_id;
	}
}

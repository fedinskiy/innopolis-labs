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
public class SendedEmail {
	private String subject;
	private String content;
	private LocalDate sended_at;
	
	@ManyToOne( cascade = CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=Enrollee.class )
	@JoinColumn(name = "user_id")
	private Enrollee addressee;
	
	@Id
	private Long id;
	
	public SendedEmail() {
	}
	
	public SendedEmail(ResultSet resultSet) throws SQLException {
		if(resultSet.next()){
			subject=resultSet.getString("subject");
			content=resultSet.getString("content");
			sended_at=resultSet.getDate("sended_at").toLocalDate();
			id=resultSet.getLong("email_id");
			this.addressee=new Enrollee(resultSet);
		}
	}
	
	public PreparedStatement toStatement(PreparedStatement statement) throws SQLException {
		final long MOCK_ID=1;
		if(null!=addressee) {
			statement.setLong(1, addressee.getId());
		}else{
			statement.setLong(1, MOCK_ID);
		}
		statement.setLong(2, MOCK_ID);
		statement.setLong(3, MOCK_ID);
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
	
	public Enrollee getAddressee() {
		return addressee;
	}
	public String getEmail(){
		return addressee.getEmail();
	}
	
}

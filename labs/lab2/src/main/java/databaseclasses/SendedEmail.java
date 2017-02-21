package databaseclasses;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by fedinskiy on 21.02.17.
 */

@Entity
@javax.persistence.Table(name = "email")
public class SendedEmail {
	private String subject;
	private String content;
	private Date sended_at;
	@Id
	private Integer id;
	public String getSubject() {
		return subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public Date getSended_at() {
		return sended_at;
	}
}

package databaseclasses;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by fedinskiy on 21.02.17.
 */

@Entity
@javax.persistence.Table(name = "email")
public class SendedEmail {
	private String subject;
	private String content;
	
}

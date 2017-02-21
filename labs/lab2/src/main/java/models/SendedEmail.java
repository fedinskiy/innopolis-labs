package models;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import xmlclasses.EmailContent;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by fedinskiy on 20.02.17.
 */
public class SendedEmail extends Email{
	private LocalDate date;
	
	public SendedEmail(xmlclasses.Email email) {
		super(email);
		this.date=email.getSendedAt().toGregorianCalendar().toZonedDateTime().toLocalDate();
	}
	
	public SendedEmail(databaseclasses.SendedEmail email){
		super(email.getSubject(),email.getContent(),null);
		this.date=email.getSended_at();
	}
	
	public xmlclasses.Email toXML() throws DatatypeConfigurationException {
		xmlclasses.Email retval=new xmlclasses.Email();
		EmailContent content=new EmailContent();
		content.setTextContent(this.getContent());
		retval.setContent(content);
		
		retval.setSendedAt(XMLDate(this.getDate()));
		retval.setSubject(this.getSubject());
		
		retval.setId(null);
		retval.setAddresseeId(null);
		retval.setReasonId(null);
		retval.setSenderId(null);
		return retval;
	}
	public LocalDate getDate() {
		return date;
	}
	
	public databaseclasses.SendedEmail toSQL() throws SQLException {
		databaseclasses.SendedEmail email= new databaseclasses.SendedEmail();
		email.setContent(getContent());
		email.setSended_at(this.getDate());
		email.setSubject(getSubject());
		return email;
	}
	
}

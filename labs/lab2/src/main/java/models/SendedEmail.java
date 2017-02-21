package models;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import xmlclasses.EmailContent;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by fedinskiy on 20.02.17.
 */
public class SendedEmail extends Email{
	private Date date;
	public SendedEmail(xmlclasses.Email email) {
		super(email);
		this.setAddresee(email.getAddressee().getEmail());
		this.date=email.getSendedAt().toGregorianCalendar().getGregorianChange();
	}
	
	public SendedEmail(databaseclasses.SendedEmail email){
		super(email.getSubject(),email.getContent(),null);
		this.date=email.getSended_at();
	}
	
	public xmlclasses.Email toXML(){
		xmlclasses.Email retval=new xmlclasses.Email();
		EmailContent content=new EmailContent();
		content.setTextContent(this.getContent());
		retval.setContent(content);
		
		retval.setSendedAt(XMLDate(date));
		retval.setSubject(this.getSubject());
		
		retval.setId(null);
		retval.setAddressee(null);
		retval.setReasonId(null);
		retval.setSenderId(null);
		return retval;
	}
	private static XMLGregorianCalendar XMLDate(Date date){
		GregorianCalendar cal;
		XMLGregorianCalendar xcal;
		
		cal = new GregorianCalendar();
		cal.setTime(date);
		xcal = new XMLGregorianCalendarImpl(cal);
		
		return xcal;
	}
}

package models;

import xmlclasses.EmailContent;

import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Created by fedinskiy on 20.02.17.
 */
public class SendedEmail extends Email {
	private LocalDate date;
	private Long id;
	private Long user_id;
	private Long superuser_id;
	private Long template_id;
	
	
	public SendedEmail(xmlclasses.Email email) {
		super(email);
		this.date = email.getSendedAt().toGregorianCalendar().toZonedDateTime().toLocalDate();
		this.user_id = email.getAddresseeId().longValueExact();
		this.superuser_id = email.getSenderId().longValueExact();
		this.template_id = email.getReasonId().longValueExact();
	}
	
	public SendedEmail(databaseclasses.SendedEmail email) {
		super(email.getSubject(), email.getContent(), null);
		this.date = email.getSended_at();
		this.user_id = email.getUser_id();
		this.superuser_id = email.getSuperuser_id();
		this.template_id = email.getTemplate_id();
	}
	
	@Override
	public xmlclasses.Email toXML() throws DatatypeConfigurationException {
		xmlclasses.Email retval = new xmlclasses.Email();
		EmailContent content = new EmailContent();
		content.setTextContent(this.getContent());
		retval.setContent(content);
		
		retval.setSendedAt(XMLDate(this.getDate()));
		retval.setSubject(this.getSubject());
		
		retval.setId(null);
		retval.setAddresseeId(BigInteger.valueOf(getUser_id()));
		retval.setReasonId(BigInteger.valueOf(getTemplate_id()));
		retval.setSenderId(BigInteger.valueOf(getSuperuser_id()));
		return retval;
	}
	
	@Override
	public databaseclasses.SendedEmail toSQL() throws SQLException {
		databaseclasses.SendedEmail email = new databaseclasses.SendedEmail();
		email.setUser_id(getUser_id());
		email.setSuperuser_id(getSuperuser_id());
		email.setTemplate_id(getTemplate_id());
		email.setContent(getContent());
		email.setSended_at(this.getDate());
		email.setSubject(getSubject());
		return email;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
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

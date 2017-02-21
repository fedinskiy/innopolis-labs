package models;

import xmlclasses.EmailContent;

import java.sql.SQLException;

/**
 * Created by fedinskiy on 20.02.17.
 */
public class EmailTemplate extends Email {
	private String name;
	public EmailTemplate(xmlclasses.EmailTemplate email) {
		super(email.getSubject(),email.getContentTemplate().getTextContent(),null);
	}
	
	public EmailTemplate(databaseclasses.EmailTemplate template) {
		super(template.getSubject(),template.getContent(),null);
	}
	
	
	public xmlclasses.EmailTemplate toXML() {
		xmlclasses.EmailTemplate retval=new xmlclasses.EmailTemplate();
		EmailContent content=new EmailContent();
		content.setTextContent(this.getContent());
		retval.setContentTemplate(content);
		retval.setSubject(this.getSubject());
		retval.setReason(this.getName());
		retval.setId(null);

		return retval;
	}
	
	@Override
	public Object toSQL() throws SQLException, IllegalAccessException {
		databaseclasses.EmailTemplate template= new databaseclasses.EmailTemplate();
		template.setName(getName());
		template.setContent(getContent());
		template.setSubject(getSubject());
		return template;
	}
	
	public String getName() {
		return name;
	}
}

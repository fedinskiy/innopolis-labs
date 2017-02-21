package models;

/**
 * Created by fedinskiy on 20.02.17.
 */
public class Email {
	private String subject;
	private String content;
	private String addresee;
	
	
	public Email(xmlclasses.Email email) {
		this.subject = email.getSubject();
		this.content = email.getContent().getTextContent();
		addresee=null; //может быть пустым для шаблонов
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setAddresee(String addresee) {
		this.addresee = addresee;
	}
}

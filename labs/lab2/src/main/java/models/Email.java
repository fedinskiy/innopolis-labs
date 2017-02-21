package models;

/**
 * Created by fedinskiy on 20.02.17.
 */
public abstract class Email extends BaseModel{

	private String subject;
	private String content;
	private String addresee;
	
	public Email(String subject, String content, String addresee) {
		super(null);
		this.subject = subject;
		this.content = content;
		this.addresee = addresee;
	}
	
	
	public Email(xmlclasses.Email email) {
		this(email.getSubject(), email.getContent().getTextContent(),null);
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

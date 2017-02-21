package models;

import java.util.Date;

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
}

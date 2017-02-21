import dbworker.HibernateHelper;
import dbworker.SQLHelperFactory;
import models.SendedEmail;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;


/**
 * Created by fedinskiy on 21.02.17.
 */
public class Application {
	public static void main(String[] args) throws JAXBException {
		File test = new File("lab2/src/main/resources/config/hibernate.cfg.xml");
		System.out.println(test.length());
		HibernateHelper hh = SQLHelperFactory.getHibernateHelper();
		SendedEmail email=new SendedEmail (hh.sendedEmail());
		JAXBContext jaxbContext= JAXBContext.newInstance((xmlclasses.Email.class));
		Marshaller marshaller=jaxbContext.createMarshaller();
		File out = new File("out/xml/tst.xml");
		
		marshaller.marshal(email.toXML(),out);
	}
}

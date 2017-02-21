package models;

import databaseclasses.DBClass;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;

/**
 * Created by fedinskiy on 22.02.17.
 */
public abstract class BaseModel {
	public BaseModel(Object xmlobject) {}
	
	public BaseModel(DBClass dbClass){}
	
	public abstract Object toXML() throws DatatypeConfigurationException;
	public abstract Object toSQL() throws SQLException, IllegalAccessException;
	
	protected static XMLGregorianCalendar XMLDate(LocalDate date) throws DatatypeConfigurationException {
		XMLGregorianCalendar xcal;
		
		GregorianCalendar gcal = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
		xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		
		return xcal;
	}
	protected static LocalDate fromXMLDate(XMLGregorianCalendar  date) throws DatatypeConfigurationException {
		return date.toGregorianCalendar().toZonedDateTime().toLocalDate();
	}
}

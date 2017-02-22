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
	public BaseModel(Object xmlobject) {
	}
	
	public BaseModel(DBClass dbClass) {
	}
	
	/**
	 * @implSpec преобразует дату в пригодный для хранения в XML формат
	 * @param date
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	protected static XMLGregorianCalendar XMLDate(LocalDate date) throws DatatypeConfigurationException {
		if (null == date) return null;
		XMLGregorianCalendar xcal;
		
		GregorianCalendar gcal = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
		xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		
		return xcal;
	}
	
	/**
	 * @implSpec преобразует дату из формата для XML в дату в текущем часовом поясе
	 * @param date
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	protected static LocalDate fromXMLDate(XMLGregorianCalendar date) throws DatatypeConfigurationException {
		if (null == date) return null;
		return date.toGregorianCalendar().toZonedDateTime().toLocalDate();
	}
	
	/**
	 *
	 * @return возвращает предсталение объекта, пригодное для записи в XML
	 * @throws DatatypeConfigurationException
	 */
	public abstract Object toXML() throws DatatypeConfigurationException;
	
	/**
	 *
	 * @return возвращает предсталение объекта, пригодное для записи в SQL
	 * @throws SQLException
	 * @throws IllegalAccessException
	 */
	public abstract Object toSQL() throws SQLException, IllegalAccessException;
}

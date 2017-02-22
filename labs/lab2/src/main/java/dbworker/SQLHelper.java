package dbworker;

import databaseclasses.DBClass;
import databaseclasses.SendedEmail;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fedinskiy on 21.02.17.
 */
public abstract class SQLHelper {
	
	/**
	 * @implSpec прекращение работы с sql
	 * @throws SQLException
	 */
	public abstract void stopSession() throws SQLException;
	
	/**
	 *
	 * @param toSQL класс для отправки в sql
	 * @param sqlQ «рыба» запроса
	 * @throws SQLException
	 */
	public abstract void toSQL(DBClass toSQL, String sqlQ) throws SQLException;
	
	/**
	 *
	 * @param toSQl
	 * @return «рыба» sql-запрос для записи файла этого класса
	 */
	public abstract String getQueue(databaseclasses.User toSQl);
	
	public abstract String getQueue(databaseclasses.EmailTemplate toSQl);
	
	public abstract String getQueue(databaseclasses.Administrator toSQl);
	
	public abstract String getQueue(databaseclasses.SendedEmail toSQl);
	
	/**
	 *
	 * @return возвращает все запросы из определенной таблицы в БД
	 * @throws SQLException
	 */
	protected abstract ResultSet getAllFromTable(String table) throws SQLException;
	
	public synchronized ResultSet allEmail() throws SQLException {
		return getAllFromTable("email");
	}

	public synchronized ResultSet allUsers() throws SQLException {
		return getAllFromTable("bitrixusers");
	}
	
	public synchronized ResultSet allAdmins() throws SQLException {
		return getAllFromTable("superuser");
	}
	
	public synchronized ResultSet allEmailTemplates() throws SQLException {
		return getAllFromTable("email_reason");
	}
	
	/**
	 *
	 * @return получение информации о последнем отправленном письме
	 * @throws SQLException
	 */
	public abstract SendedEmail sendedEmail() throws SQLException;
	
}

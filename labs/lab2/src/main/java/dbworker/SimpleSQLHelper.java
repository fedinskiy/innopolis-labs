package dbworker;

import databaseclasses.DBClass;
import databaseclasses.EmailTemplate;
import databaseclasses.SendedEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * Created by fedinskiy on 21.02.17.
 */
public class SimpleSQLHelper extends SQLHelper {
	private final static Logger logger = LogManager.getLogger(SimpleSQLHelper.class);
	private final String url = "jdbc:postgresql://localhost:5432/stc?user=test&password=test";
	private Connection conn;
	
	protected SimpleSQLHelper() {
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Override
	protected ResultSet getAllFromTable(String table) throws SQLException {
		String sqlQ = "SELECT * FROM " + table;
		final ResultSet resultSet = conn.createStatement().executeQuery(sqlQ);
		return resultSet;
	}
	
	public synchronized SendedEmail sendedEmail() throws SQLException {
		SendedEmail result;
		String sqlQ = "SELECT email.id AS email_id, email.sended_at, email.content, " +
				"       email.subject, public.user.id AS user_id, public.user.email" +
				"  FROM email" +
				"  INNER JOIN public.user ON email.user_id=public.user.id";
		final ResultSet resultSet = conn.createStatement().executeQuery(sqlQ);
		result = new SendedEmail(resultSet);
		return result;
	}
	
	
	public synchronized void toSQL(DBClass toSQL, String sqlQ) throws SQLException {
		PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQ);
		preparedStatement = toSQL.toStatement(preparedStatement);
		System.out.println(preparedStatement.toString());
		preparedStatement.execute();
	}
	
	public String getQueue(SendedEmail toSQl) {
		return "INSERT INTO  public.email(  user_id, superuser_id, email_reason_id, sended_at, content, subject) " +
				"Values (?,?,?,?,?,?)";
	}
	
	public String getQueue(EmailTemplate toSQl) {
		return "INSERT INTO  public.email_reason( name, template,subject) " +
				"Values (?,?,?)";
	}
	
	public String getQueue(databaseclasses.User toSQl) {
		return "INSERT INTO  public.bitrixusers(  bitrix_id, firstname, middlename, lastname, email, phone, birthdate)" +
				" " +
				"Values (?,?,?,?,?,?,?)";
	}
	
	public String getQueue(databaseclasses.Administrator toSQl) {
		return "INSERT INTO  public.superuser(  firstname, middlename, lastname, email) " +
				"Values (?,?,?,?)";
	}
	
	private Connection getConnection() throws SQLException {
		if (null == conn || conn.isClosed()) {
			conn = DriverManager.getConnection(url);
		}
		return conn;
	}
	
	public void stopSession() throws SQLException {
		conn.close();
	}
	
	@Override
	protected void finalize() throws Throwable {
		stopSession();
	}
}

package dbworker;

import databaseclasses.SendedEmail;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fedinskiy on 21.02.17.
 */
public abstract class SQLHelper {
	public abstract void stopSession() throws SQLException;
	public abstract void toSendEmail(SendedEmail sendedEmail) throws SQLException;
	
	public synchronized ResultSet allEmail() throws SQLException {
		return getAllFromTable("email");
	}
	
	protected abstract ResultSet getAllFromTable(String email)throws SQLException ;
	
	public synchronized ResultSet allUsers() throws SQLException {
		return getAllFromTable("users");
	}
	
	public synchronized ResultSet allAdmins() throws SQLException {
		return getAllFromTable("superusers");
	}
	
	public synchronized ResultSet allEmailTemplates() throws SQLException {
		return getAllFromTable("email_reason");
	}
	
	public abstract SendedEmail sendedEmail() throws SQLException;
}

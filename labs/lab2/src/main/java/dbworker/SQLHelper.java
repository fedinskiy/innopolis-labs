package dbworker;

import databaseclasses.SendedEmail;

import java.sql.SQLException;

/**
 * Created by fedinskiy on 21.02.17.
 */
public abstract class SQLHelper {
	public abstract void stopSession() throws SQLException;
	public abstract void toSendEmail(SendedEmail sendedEmail) throws SQLException;
	public abstract SendedEmail sendedEmail() throws SQLException;
}

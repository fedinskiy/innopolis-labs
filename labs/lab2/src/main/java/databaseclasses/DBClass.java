package databaseclasses;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fedinskiy on 21.02.17.
 */
public abstract class DBClass {
	public DBClass() {
	}
	
	public DBClass(ResultSet resultSet) throws SQLException, IllegalAccessException {
	}
	
	/**
	 * @implSpec  Записывает информацию о себе в SQL-запрос
	 * @param statement
	 * @return
	 * @throws SQLException
	 */
	public abstract PreparedStatement toStatement(PreparedStatement statement) throws SQLException;
}
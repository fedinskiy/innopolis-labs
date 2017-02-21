package databaseclasses;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
	
	public abstract PreparedStatement toStatement(PreparedStatement statement) throws SQLException;
}
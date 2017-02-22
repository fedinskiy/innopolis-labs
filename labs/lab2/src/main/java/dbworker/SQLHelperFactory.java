package dbworker;

/**
 * Created by fedinskiy on 21.02.17.
 */
public class SQLHelperFactory {
	private static SQLHelper sqlHelper = new SimpleSQLHelper();
	
	/**
	 * @return синглтон для работы с SQL
	 */
	public static SQLHelper getSQLHelper() {
		return sqlHelper;
	}
	
}

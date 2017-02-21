package dbworker;

/**
 * Created by fedinskiy on 21.02.17.
 */
public class SQLHelperFactory {
	//private static HibernateHelper sqlHelper = new HibernateHelper();
	private static SQLHelper sqlHelper = new SimpleSQLHelper();
	public static SQLHelper getSQLHelper(){
		return sqlHelper;
	}
	public static void stop(){
		sqlHelper=null;
	}
}

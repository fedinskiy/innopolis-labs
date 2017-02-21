package dbworker;

/**
 * Created by fedinskiy on 21.02.17.
 */
public class SQLHelperFactory {
	private static final HibernateHelper hibernateHelper = new HibernateHelper();
	public static HibernateHelper getHibernateHelper(){
		return hibernateHelper;
	}
}

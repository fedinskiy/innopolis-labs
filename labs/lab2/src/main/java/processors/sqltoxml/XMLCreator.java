package processors.sqltoxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xmlclasses.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fedinskiy on 22.02.17.
 */
public abstract class XMLCreator implements Runnable {
	private final static Logger logger = LogManager.getLogger
			(XMLCreator.class);
	private final ObjectFactory objectFactory;
	private final JAXBContext jaxbContext;
	private final Marshaller marshaller;
	private final Unmarshaller unmarshaller;
	private final String DIRECTORY_TEMPLATE = "results/%1$s";
	private final String FILENAME_TEMPLATE = DIRECTORY_TEMPLATE + "/tst%2$d.xml";
	
	public XMLCreator() throws JAXBException {
		objectFactory = new ObjectFactory();
		jaxbContext = JAXBContext.newInstance((objectFactory.getClass()));
		marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		unmarshaller = jaxbContext.createUnmarshaller();
	}
	
	public final String getFileName(int counter) {
		return String.format(FILENAME_TEMPLATE, this.getDirectoryName(), counter);
	}
	
	protected int getMaxSize() {
		return 10;
	}
	
	protected ObjectFactory getObjectFactory() {
		return objectFactory;
	}
	
	protected Marshaller getMarshaller() {
		return marshaller;
	}
	
	protected Unmarshaller getUnmarshaller() {
		return unmarshaller;
	}
	
	protected abstract String getDirectoryName();
	
	protected File[] getFiles() {
		File directory = new File(String.format(DIRECTORY_TEMPLATE, getDirectoryName()));
		return directory.listFiles();
	}
	
	public boolean createDirectory() {
		File directory = new File(String.format(DIRECTORY_TEMPLATE, getDirectoryName()));
		if (!directory.exists()) {
			directory.mkdirs();
		}
		return true;
	}
	
	protected boolean createFile(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				this.logger.error(e1.getMessage() + ":" + file.getAbsolutePath(), e1);
				return false;
			}
		}
		return true;
	}
	
	protected abstract ResultSet getResultSet() throws SQLException;
	
	/**
	 *
	 * @throws SQLException
	 * @throws JAXBException
	 * @throws DatatypeConfigurationException
	 * @throws IOException
	 * @throws IllegalAccessException
	 */
	public abstract void createXMLs() throws SQLException, JAXBException, DatatypeConfigurationException, IOException,
			IllegalAccessException;
	
	/**
	 *
	 * @throws SQLException
	 * @throws JAXBException
	 * @throws DatatypeConfigurationException
	 * @throws IOException
	 * @throws IllegalAccessException
	 */
	public abstract void createSQL() throws SQLException, JAXBException, DatatypeConfigurationException, IOException,
			IllegalAccessException;
	
	@Override
	public void run() {
		try {
			createXMLs();
			createSQL();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		} catch (DatatypeConfigurationException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
		
	}
}

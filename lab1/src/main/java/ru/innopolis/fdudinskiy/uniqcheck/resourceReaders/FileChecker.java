package ru.innopolis.fdudinskiy.uniqcheck.resourceReaders;

import ru.innopolis.fdudinskiy.uniqcheck.exceptions.IllegalSymbolsException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WrongResourceException;

import java.io.*;

/**
 * Created by fedinskiy on 08.02.17.
 */
public class FileChecker extends ResourceChecker {
	/**
	 * @param resource
	 * @throws WrongResourceException
	 * @throws IOException
	 * @throws IllegalSymbolsException
	 */
	public FileChecker(File resource) throws WrongResourceException, IOException, IllegalSymbolsException {
		super(resource.getAbsolutePath(), Math.toIntExact(resource.length()));
		final String DEFAULT_ENCODING = "UTF-8";
		
		String filePath = resource.getAbsolutePath();
		if (!resource.exists()) {
			throw new WrongResourceException("Файл " + filePath + " не существует");
		}
		if (!resource.isFile()) {
			throw new WrongResourceException("По пути " + filePath + " расположена папка!");
		}
		if (!resource.canRead()) {
			throw new WrongResourceException("Невозможно прочесть файл " + filePath + " !");
		}
		if (resource.length() > Integer.MAX_VALUE) {
			throw new WrongResourceException("Файл " + filePath + " слишком велик!");
		}
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(resource), DEFAULT_ENCODING))) {
			this.read(in);
			in.close();
		}
	}
}

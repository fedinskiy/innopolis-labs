package ru.innopolis.fdudinskiy.uniqcheck.resourceReaders;

import ru.innopolis.fdudinskiy.uniqcheck.exceptions.IllegalSymbolsException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.ResourceTooLargeException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WrongResourceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by fedinskiy on 08.02.17.
 */
public class URLChecker extends ResourceChecker {
	
	public URLChecker(URL url) throws IOException, IllegalSymbolsException,
										WrongResourceException {
		super(url.toString());
		try {
			super.prepareStore(
					Math.toIntExact(url.openConnection().getContentLength()));
		} catch (ResourceTooLargeException ex) {
			throw new WrongResourceException("Файл, расположенный по пути"
					+ url.toString()
					+ " слишком велик!", ex);
		}
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(url.openStream()))) {
			this.read(in);
			in.close();
		}
	}
}

package ru.innopolis.fdudinskiy.uniqcheck.resourceReaders;

import ru.innopolis.fdudinskiy.uniqcheck.exceptions.IllegalSymbolsException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by fedinskiy on 08.02.17.
 */
public class URLChecker extends ResourceChecker {
	
	public URLChecker(URL url) throws IOException, IllegalSymbolsException {
		super(url.toString(),
				Math.toIntExact(url.openConnection().getContentLength()));
		try (BufferedReader in = new BufferedReader(
				new InputStreamReader(url.openStream()))) {
			this.read(in);
			in.close();
		}
	}
}

package ru.innopolis.fdudinskiy.uniqcheck;

import ru.innopolis.fdudinskiy.uniqcheck.exceptions.IllegalSymbolsException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WordAlredyAddedException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WrongResourceException;

import java.io.*;

/**
 * Created by fedinskiy on 06.02.17.
 */
public class ResourceChecker {
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final String SPACES = "[\\s]";
	private File resource;

	public ResourceChecker(String path) throws WrongResourceException {
		if (null == path) {
			throw new WrongResourceException("Не передан путь!");
		}
		resource = new File(path);
		if (!resource.exists()) {
			throw new WrongResourceException("Файл " + path + " не существует");
		}
		if (!resource.isFile()) {
			throw new WrongResourceException("По пути " + path + " расположена папка!");
		}
		if (!resource.canRead()) {
			throw new WrongResourceException("Невозможно прочесть файл " + path + " !");
		}
	}

	public void checkForRepeats(WordsStore store) throws IOException, WordAlredyAddedException, IllegalSymbolsException {
		String line;
		String[] lineContent;

		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(resource), DEFAULT_ENCODING))) {
			line = in.readLine();
			while (null != line) {
				if(line.isEmpty()){
					line=in.readLine();
					continue;
				}
				if (!checkString(line)){
					throw new IllegalSymbolsException(line, resource.getAbsolutePath());
				}
				lineContent = line.split(SPACES);
				for (String word : lineContent) {
					if(!isNotWord(word)){
						store.addNewWord(word);
					}
				}
				line=in.readLine();
			}
		}
	}

	private boolean isNotWord(String word) {
		final String NOT_WORD="[^\\p{Alpha}]+";
		return word.matches(NOT_WORD);
	}

	private boolean checkString(String wordForCheck) {
		final String ALLOWED_SYMBOLS="[А-Яа-я\\s\\d,.\\-—?!№%\":*();]*";
		System.out.println("Обработка строки "+wordForCheck);
		return wordForCheck.matches(ALLOWED_SYMBOLS);
	}
}

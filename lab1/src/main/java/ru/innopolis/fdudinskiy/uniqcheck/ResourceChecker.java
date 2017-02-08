package ru.innopolis.fdudinskiy.uniqcheck;

import ru.innopolis.fdudinskiy.uniqcheck.exceptions.IllegalSymbolsException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WordAlreаdyAddedException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WrongResourceException;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by fedinskiy on 06.02.17.
 */
public class ResourceChecker {
	private String resourceName;
	private ArrayList<String> wordArray;
	
	public ResourceChecker(String filePath) throws WrongResourceException, IOException, IllegalSymbolsException {
		final String DEFAULT_ENCODING = "UTF-8";
		File resource;
		
		if (null == filePath) {
			throw new WrongResourceException("Не передан путь!");
		}
		resource = new File(filePath);
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
		resourceName = resource.getAbsolutePath();
		wordArray = new ArrayList<>(Math.toIntExact(resource.length()));
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(resource), DEFAULT_ENCODING))) {
			read(in);
			in.close();
		}
	}
	
	private void read(BufferedReader in) throws IOException, IllegalSymbolsException {
		final String SPACES = "[\\s]";
		String line;
		String[] lineContent;
		String word;
		
		if(null==wordArray){
			wordArray=new ArrayList<>();
		}
		line = in.readLine();
		while (null != line) {
			if (line.isEmpty()) {
				line = in.readLine();
				continue;
			}
			if (!isStringContainsAcceptableSymbolsOnly(line)) {
				throw new IllegalSymbolsException(line, resourceName);
			}
			lineContent = line.split(SPACES);
			for (String stringPiece : lineContent) {
				word = cleanWord(stringPiece);
				if (!word.isEmpty()) {
					wordArray.add(word);
				}
			}
			line = in.readLine();
		}
		wordArray.trimToSize();
	}
	
	private String cleanWord(String stringPiece) {
		final String NOT_WORD = "[^\\p{IsAlphabetic}-]+";
		
		return stringPiece.replaceAll(NOT_WORD, "");
	}
	
	public void checkForRepeats(WordsStore store) throws IllegalSymbolsException, WordAlreаdyAddedException, IOException {
		for (String word : this.wordArray) {
			store.addNewWord(word);
		}
	}
	
	private boolean isStringContainsAcceptableSymbolsOnly(String wordForCheck) {
		final String ALLOWED_SYMBOLS = "[А-Яа-яЁё0-9\\s\\d,.\\-—?!№%\":*();]*";
		
		System.out.println("Обработка строки " + wordForCheck);
		return wordForCheck.matches(ALLOWED_SYMBOLS);
	}
}

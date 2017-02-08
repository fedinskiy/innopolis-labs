package ru.innopolis.fdudinskiy.uniqcheck.resourceReaders;

import ru.innopolis.fdudinskiy.uniqcheck.WordsStore;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.IllegalSymbolsException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.ResourceTooLargeException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WordAlreаdyAddedException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WrongResourceException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by fedinskiy on 06.02.17.
 */
public class ResourceChecker {
	private String resourceName;
	private ArrayList<String> wordArray;
	
	/**
	 * @param sourcePath путь к ресурсу, который надо прочесть
	 * @throws WrongResourceException
	 * @throws IOException
	 * @throws IllegalSymbolsException
	 * @implSpec читает данные из ресурса по пути, если тот существует
	 */
	public static ResourceChecker createChecker(String sourcePath)
			throws WrongResourceException, IOException, IllegalSymbolsException {
		ResourceType sourceType = null;
		if (null == sourcePath) {
			throw new WrongResourceException("Не передан путь!");
		}
		sourceType = getSourceType(sourcePath);
		switch (sourceType) {
			case FILE:
				return new FileChecker(new File(sourcePath));
			case URL:
				return new URLChecker(new URL(sourcePath));
			default:
				return null;
		}
	}
	
	protected ResourceChecker(String resourceName) {
		this.resourceName = resourceName;
		this.wordArray = new ArrayList<>();
	}
	
	protected void prepareStore(long dataSize) throws ResourceTooLargeException {
		final int MAX_WORD_TO_SIZE_RATIO=2;
		if ((dataSize/MAX_WORD_TO_SIZE_RATIO)>Integer.MAX_VALUE){
			throw new ResourceTooLargeException("Ресурс  слишком велик!");
		}
		this.wordArray = new ArrayList<String>(
				(int) (dataSize/MAX_WORD_TO_SIZE_RATIO));
	}
	
	private static ResourceType getSourceType(String sourcePath) {
		final String URL_FLAG = "http";
		
		return (sourcePath.startsWith(URL_FLAG))
				? ResourceType.URL
				: ResourceType.FILE;
	}
	
	
	protected void read(BufferedReader in)
			throws IOException, IllegalSymbolsException {
		final String SPACES = "[\\s]";
		String line;
		String[] lineContent;
		String word;
		
		if (null == wordArray) {
			wordArray = new ArrayList<>();
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
	
	/**
	 * @param store
	 * @throws WordAlreаdyAddedException
	 * @implSpec Проверяет все слова, прочитанные из ресурса,
	 * на наличие в данном хранилище.
	 */
	public void checkForRepeats(WordsStore store)
			throws WordAlreаdyAddedException {
		for (String word : this.wordArray) {
			store.addNewWord(word);
		}
	}
	
	private boolean isStringContainsAcceptableSymbolsOnly(String wordForCheck) {
		final String ALLOWED_SYMBOLS = "[А-Яа-яЁё0-9\\s\\d,.\\-—?!№%\":*();]*";
		
		System.out.println("Обработка строки " + wordForCheck);
		return wordForCheck.matches(ALLOWED_SYMBOLS);
	}
	
	public int getSize() {
		return wordArray.size();
	}
	
	private enum ResourceType {
		URL, FILE
	}
}

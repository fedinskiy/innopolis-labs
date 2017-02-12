package ru.innopolis.fdudinskiy.uniqcheck.resourceReaders;

import ru.innopolis.fdudinskiy.uniqcheck.exceptions.IllegalSymbolsException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.ResourceTooLargeException;
import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WordAlreаdyAddedException;
import ru.innopolis.fdudinskiy.uniqcheck.store.WordsStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by fedinskiy on 06.02.17.
 */
public abstract class ResourceContent {
	private String resourceName;
	private ArrayList<String> wordArray;
	
	protected ResourceContent(String resourceName) {
		this.resourceName = resourceName;
		this.wordArray = new ArrayList<>();
	}
	
	protected void prepareStore(long dataSize) throws ResourceTooLargeException {
		final int MAX_WORD_TO_SIZE_RATIO = 2;
		if ((dataSize / MAX_WORD_TO_SIZE_RATIO) > Integer.MAX_VALUE) {
			throw new ResourceTooLargeException("Ресурс  слишком велик!");
		}
		this.wordArray = new ArrayList<String>(
				(int) (dataSize / MAX_WORD_TO_SIZE_RATIO));
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
			Integer i = new Integer("1");
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
	public boolean addNewWordsToStore(WordsStore store) throws WordAlreаdyAddedException {
		for (String word : this.wordArray) {
			System.out.println("пишем слово " + word + " из источника " + this
					.resourceName);
			
			if (!store.addNewWord(word))
				break;
		}
		return !store.isHasDoubles();
	}
	
	private boolean isStringContainsAcceptableSymbolsOnly(String wordForCheck) {
		final String ALLOWED_SYMBOLS = "[А-Яа-яЁё0-9\\s\\d,.\\-—?!№%\":*();" +
				"\\[\\]]*";
		
		System.out.println("Обработка строки " + wordForCheck);
		return wordForCheck.matches(ALLOWED_SYMBOLS);
	}
	
	public int getSize() {
		return wordArray.size();
	}
}

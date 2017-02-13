package ru.innopolis.fdudinskiy.uniqcheck.store;

import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WordAlreаdyAddedException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by fedinskiy on 06.02.17.
 */
public class WordsStore {
	private Set<String> words;
	private volatile boolean hasDoubles;
	
	/**
	 * @param size — размер массива
	 */
	public WordsStore(int size) {
		words = new HashSet<String>(size);
	}
	
	public boolean isHasDoubles() {
		return hasDoubles;
	}
	
	/**
	 * @param word
	 * @throws WordAlreаdyAddedException
	 * @implSpec Добавляет в хранилище новое слово.
	 * Если слово в хранилище уже есть — выдает ошибку.
	 */
	public synchronized boolean addNewWord(String word)
			throws WordAlreаdyAddedException {
		if (hasDoubles) {
			return false;
		}
		if (words.contains(word)) {
			hasDoubles = true;
			throw new WordAlreаdyAddedException(word);
		} else {
			addWord(word);
			return true;
		}
	}
	
	private void addWord(String word) {
		words.add(word);
	}
}

package ru.innopolis.fdudinskiy.uniqcheck;

import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WordAlreаdyAddedException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by fedinskiy on 06.02.17.
 */
public class WordsStore {
	private Set<String> words;
	
	public WordsStore() {
		words = new HashSet<String>();
	}
	
	/**
	 *
	 * @param size — размер массива
	 */
	public WordsStore(int size) {
		words = new HashSet<String>(size);
	}
	
	/**
	 * @param word
	 * @throws WordAlreаdyAddedException
	 * @implSpec Добавляет в хранилище новое слово.
	 * Если слово в хранилище уже есть — выдает ошибку.
	 */
	public synchronized void addNewWord(String word)
			throws WordAlreаdyAddedException {
		if (words.contains(word)) {
			throw new WordAlreаdyAddedException(word);
		} else {
			addWord(word);
		}
	}
	
	private void addWord(String word) {
		words.add(word);
	}
}

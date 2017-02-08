package ru.innopolis.fdudinskiy.uniqcheck;

import ru.innopolis.fdudinskiy.uniqcheck.exceptions.WordAlreаdyAddedException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by fedinskiy on 06.02.17.
 */
public class WordsStore {
	private Set<String> words = new HashSet<String>();
	
	/**
	 * @implSpec Добавляет в хранилище новое слово. Если слово в хранилище уже есть — выдает ошибку.
	 * @param word
	 * @throws WordAlreаdyAddedException
	 */
	public void addNewWord(String word) throws WordAlreаdyAddedException {
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

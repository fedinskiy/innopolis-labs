package ru.innopolis.fdudinskiy.uniqcheck.exceptions;

import com.sun.istack.internal.NotNull;

/**
 * Created by fedinskiy on 07.02.17.
 */
public class WordAlreаdyAddedException extends Exception {
	private String word;
	
	/**
	 * @param word
	 */
	public WordAlreаdyAddedException(@NotNull String word) {
		super("Слово '" + word + "' уже существует");
		this.word = word;
	}
}

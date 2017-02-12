package ru.innopolis.fdudinskiy.uniqcheck.exceptions;

import com.sun.istack.internal.NotNull;

/**
 * Created by fedinskiy on 07.02.17.
 */
public class WordAlreаdyAddedException extends BasicCallableException {
	
	/**
	 * @param word — слово, которое уже было добавлено
	 */
	public WordAlreаdyAddedException(@NotNull String word) {
		super("Слово '" + word + "' уже было добавлено");
	}
}

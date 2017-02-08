package ru.innopolis.fdudinskiy.uniqcheck.exceptions;

import com.sun.istack.internal.NotNull;

/**
 * Created by fedinskiy on 07.02.17.
 */
public class IllegalSymbolsException extends Exception {
	private String line;
	
	/**
	 * @param line
	 * @param filename
	 */
	public IllegalSymbolsException(@NotNull String line, @NotNull String filename) {
		super("Строка '" + line + "' в файле " + filename + " содержит запрещенные символы!");
		this.line = line;
	}
}

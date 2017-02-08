package ru.innopolis.fdudinskiy.uniqcheck.exceptions;

/**
 * Created by fedinskiy on 07.02.17.
 */
public class WrongResourceException extends Exception {
	/**
	 *
	 * @param cause
	 */
	public WrongResourceException(String cause) {
		this(cause,null);
	}
	
	public WrongResourceException(String cause, ResourceTooLargeException ex) {
		super(cause,ex);
	}
}

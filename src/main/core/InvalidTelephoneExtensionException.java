package main.core;

/**
 * Signals that a telephone number is of invalid length (5 digits).
 * 
 * @Author Jharik Richardson 
 * @Date 10th April 2019
 */
public class InvalidTelephoneExtensionException extends Exception {

	public InvalidTelephoneExtensionException(String message) {
		super(message);
	}
}
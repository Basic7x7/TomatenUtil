package de.tomatengames.util.function;

/**
 * A consumer that accepts no parameters {@code void}.
 * 
 * @param <T> The Throwable that may be thrown by the consumer.
 * 
 * @author Basic7x7
 * @version 2023-04-11
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface VoidConsumerWithThrows<T extends Throwable> {
	
	/**
	 * Performs this operation.
	 * @throws T If an exception occurs
	 */
	public void accept() throws T;
	
}
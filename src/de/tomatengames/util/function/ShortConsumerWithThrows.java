package de.tomatengames.util.function;

/**
 * A consumer that accepts a {@code short}.
 * 
 * @param <T> The Throwable that may be thrown by the consumer.
 * 
 * @author Basic7x7
 * @version 2023-04-11
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface ShortConsumerWithThrows<T extends Throwable> {
	
	/**
	 * Performs this operation.
	 * @param e The argument.
	 * @throws T If an exception occurs
	 */
	public void accept(short e) throws T;
	
}
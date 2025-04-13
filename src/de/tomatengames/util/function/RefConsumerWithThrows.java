package de.tomatengames.util.function;

/**
 * A consumer that accepts an {@code Object}.
 * 
 * @param <T> The Throwable that may be thrown by the consumer.
 * @param <I> The type of the input parameter.
 * 
 * @author Basic7x7
 * @version 2023-04-11
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface RefConsumerWithThrows<I, T extends Throwable> {
	
	/**
	 * Performs this operation.
	 * @param e The argument.
	 * @throws T If an exception occurs
	 */
	public void accept(I e) throws T;
	
}
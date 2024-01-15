package de.tomatengames.util.function;

/**
 * A consumer that accepts a {@code float}.
 * 
 * @param T The Throwable that may be thrown by the consumer.
 * 
 * @author Basic7x7
 * @version 2023-04-11
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface FloatConsumerWithThrows<T extends Throwable> {
	
	/**
	 * Performs this operation.
	 * @param e The argument.
	 */
	public void accept(float e) throws T;
	
}
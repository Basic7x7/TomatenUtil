// !!! TextScript generated !!!
package de.tomatengames.util.function;

/**
 * A consumer that accepts a {@code double}.
 * 
 * @param T The Throwable that may be thrown by the consumer.
 * 
 * @author Basic7x7
 * @version 2023-04-11
 * @since 1.2
 */
@FunctionalInterface
public interface DoubleConsumerWithThrows<T extends Throwable> {
	
	/**
	 * Performs this operation.
	 * @param e The argument.
	 */
	public void accept(double e) throws T;
}
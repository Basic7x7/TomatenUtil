// !!! TextScript generated !!!
package de.tomatengames.util.function;

/**
 * A function that accepts a {@code long} and returns a {@code byte}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2021-10-21 created
 * @since 1.2
 */
@FunctionalInterface
public interface LongToByteFunction {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public byte apply(long e);
}
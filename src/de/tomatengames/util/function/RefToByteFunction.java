package de.tomatengames.util.function;

/**
 * A function that accepts an {@code Object}
 * and returns a {@code byte}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2021-10-21 created
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface RefToByteFunction<I> {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public byte apply(I e);
	
}
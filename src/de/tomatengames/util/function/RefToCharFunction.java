package de.tomatengames.util.function;

/**
 * A function that accepts an {@code Object}
 * and returns a {@code char}.
 * 
 * @param <I> The input type.
 * @author Basic7x7
 * @version 2023-04-11 last modified
 * @version 2021-10-21 created
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface RefToCharFunction<I> {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public char apply(I e);
	
}
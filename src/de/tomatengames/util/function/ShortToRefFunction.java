// !!! TextScript generated !!!
package de.tomatengames.util.function;

/**
 * A function that accepts a {@code short} and returns an {@code Object}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2021-10-21 created
 * @since 1.2
 */
@FunctionalInterface
public interface ShortToRefFunction<O> {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public O apply(short e);
}
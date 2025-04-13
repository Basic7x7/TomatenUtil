package de.tomatengames.util.function;

/**
 * A function that accepts a {@code double}
 * and returns an {@code Object}.
 * 
 * @param <O> The output type.
 * @author Basic7x7
 * @version 2023-04-11 last modified
 * @version 2021-10-21 created
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface DoubleToRefFunction<O> {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public O apply(double e);
	
}
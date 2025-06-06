package de.tomatengames.util.function;

/**
 * A function that accepts an {@code Object}
 * and returns an {@code Object}.
 * 
 * @param <I> The input type.
 * @param <O> The output type.
 * @author Basic7x7
 * @version 2023-04-11 last modified
 * @version 2021-10-21 created
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface RefToRefFunction<I, O> {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public O apply(I e);
	
	/**
	 * Returns the {@code Object} identity function.
	 * The identity function always returns the input argument.
	 * @param <T> The input and output type of the function.
	 * @return The identity function.
	 */
	public static <T> RefToRefFunction<T, T> identity() {
		return e -> e;
	}
	
}
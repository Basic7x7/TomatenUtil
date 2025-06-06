package de.tomatengames.util.function;

/**
 * A function that accepts a {@code long}
 * and returns a {@code long}.
 * 
 * @author Basic7x7
 * @version 2023-04-11 last modified
 * @version 2021-10-21 created
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface LongToLongFunction {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public long apply(long e);
	
	/**
	 * Returns the {@code long} identity function.
	 * The identity function always returns the input argument.
	 * @return The identity function.
	 */
	public static LongToLongFunction identity() {
		return e -> e;
	}
	
}
// !!! TextScript generated !!!
package de.tomatengames.util.function;

/**
 * A function that accepts an {@code int} and returns an {@code int}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2021-10-21 created
 * @since 1.2
 */
@FunctionalInterface
public interface IntToIntFunction {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public int apply(int e);
	
	
	/**
	 * Returns the {@code int} identity function.
	 * The identity function always returns the input argument.
	 * @return The identity function.
	 */
	public static IntToIntFunction identity() {
		return e -> e;
	}
}
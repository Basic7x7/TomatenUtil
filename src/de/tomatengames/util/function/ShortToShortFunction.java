// !!! TextScript generated !!!
package de.tomatengames.util.function;

/**
 * A function that accepts a {@code short} and returns a {@code short}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2021-10-21 created
 * @since 1.2
 */
@FunctionalInterface
public interface ShortToShortFunction {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public short apply(short e);
	
	
	/**
	 * Returns the {@code short} identity function.
	 * The identity function always returns the input argument.
	 * @return The identity function.
	 */
	public static ShortToShortFunction identity() {
		return e -> e;
	}
}
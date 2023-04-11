// !!! TextScript generated !!!
package de.tomatengames.util.function;

/**
 * A function that accepts a {@code char} and returns a {@code char}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2021-10-21 created
 * @since 1.2
 */
@FunctionalInterface
public interface CharToCharFunction {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public char apply(char e);
	
	
	/**
	 * Returns the {@code char} identity function.
	 * The identity function always returns the input argument.
	 * @return The identity function.
	 */
	public static CharToCharFunction identity() {
		return e -> e;
	}
}
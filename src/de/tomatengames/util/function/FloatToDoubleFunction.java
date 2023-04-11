// !!! TextScript generated !!!
package de.tomatengames.util.function;

/**
 * A function that accepts a {@code float} and returns a {@code double}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2021-10-21 created
 * @since 1.2
 */
@FunctionalInterface
public interface FloatToDoubleFunction {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public double apply(float e);
}
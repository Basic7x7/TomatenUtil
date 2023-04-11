// !!! TextScript generated !!!
package de.tomatengames.util.function;

/**
 * A function that accepts a {@code double} and returns a {@code double}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2021-10-21 created
 * @since 1.2
 */
@FunctionalInterface
public interface DoubleToDoubleFunction {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public double apply(double e);
	
	
	/**
	 * Returns the {@code double} identity function.
	 * The identity function always returns the input argument.
	 * @return The identity function.
	 */
	public static DoubleToDoubleFunction identity() {
		return e -> e;
	}
}
// !!! TextScript generated !!!
package de.tomatengames.util.function;

/**
 * A function that accepts a {@code float} and returns a {@code float}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2021-10-21 created
 * @since 1.2
 */
@FunctionalInterface
public interface FloatToFloatFunction {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public float apply(float e);
	
	
	/**
	 * Returns the {@code float} identity function.
	 * The identity function always returns the input argument.
	 * @return The identity function.
	 */
	public static FloatToFloatFunction identity() {
		return e -> e;
	}
}
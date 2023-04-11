// !!! TextScript generated !!!
package de.tomatengames.util.function;

/**
 * A function that accepts an {@code int} and returns a {@code boolean}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2021-10-21 created
 * @since 1.2
 */
@FunctionalInterface
public interface IntToBooleanFunction {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public boolean apply(int e);
	
	
	/**
	 * Returns an IntToBooleanFunction that represents {@code !this.apply(e)}.
	 * @return The logical negation of this function.
	 */
	public default IntToBooleanFunction negate() {
		return e -> !this.apply(e);
	}
	
	/**
	 * Returns an IntToBooleanFunction that represents {@code !func.apply(e)}.
	 * @param func The function to negate. Must not be {@code null}.
	 * @return The logical negation of the specified function.
	 * @throws NullPointerException If the specified function is {@code null}.
	 */
	public static IntToBooleanFunction not(IntToBooleanFunction func) {
		return func.negate();
	}
}
package de.tomatengames.util.function;

/**
 * A function that accepts a {@code short}
 * and returns a {@code boolean}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2021-10-21 created
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface ShortToBooleanFunction {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public boolean apply(short e);
	
	/**
	 * Returns a ShortToBooleanFunction that represents {@code !this.apply(e)}.
	 * @return The logical negation of this function.
	 */
	public default ShortToBooleanFunction negate() {
		return (e) -> !this.apply(e);
	}
	
	/**
	 * Returns a ShortToBooleanFunction that represents {@code !func.apply(e)}.
	 * @param func The function to negate. Must not be {@code null}.
	 * @return The logical negation of the specified function.
	 * @throws NullPointerException If the specified function is {@code null}.
	 */
	public static ShortToBooleanFunction not(ShortToBooleanFunction func) {
		return func.negate();
	}
	
}
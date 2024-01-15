package de.tomatengames.util.function;

/**
 * A function that accepts an {@code Object}
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
public interface RefToBooleanFunction<I> {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public boolean apply(I e);
	
	/**
	 * Returns an RefToBooleanFunction that represents {@code !this.apply(e)}.
	 * @return The logical negation of this function.
	 */
	public default RefToBooleanFunction<I> negate() {
		return (e) -> !this.apply(e);
	}
	
	/**
	 * Returns an RefToBooleanFunction that represents {@code !func.apply(e)}.
	 * @param func The function to negate. Must not be {@code null}.
	 * @return The logical negation of the specified function.
	 * @throws NullPointerException If the specified function is {@code null}.
	 */
	public static <I> RefToBooleanFunction<I> not(RefToBooleanFunction<I> func) {
		return func.negate();
	}
	
}
package de.tomatengames.util.function;

/**
 * A function that accepts a {@code byte}
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
public interface ByteToBooleanFunction {
	
	/**
	 * Applies the function.
	 * @param e The argument.
	 * @return The result.
	 */
	public boolean apply(byte e);
	
	/**
	 * Returns a ByteToBooleanFunction that represents {@code !this.apply(e)}.
	 * @return The logical negation of this function.
	 */
	public default ByteToBooleanFunction negate() {
		return (e) -> !this.apply(e);
	}
	
	/**
	 * Returns a ByteToBooleanFunction that represents {@code !func.apply(e)}.
	 * @param func The function to negate. Must not be {@code null}.
	 * @return The logical negation of the specified function.
	 * @throws NullPointerException If the specified function is {@code null}.
	 */
	public static ByteToBooleanFunction not(ByteToBooleanFunction func) {
		return func.negate();
	}
	
}
package de.tomatengames.util.function;

/**
 * A function that accepts no parameters
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
public interface VoidToBooleanFunction {
	
	/**
	 * Applies the function.
	 * @return The result.
	 */
	public boolean apply();
	
	/**
	 * Returns a VoidToBooleanFunction that represents {@code !this.apply(e)}.
	 * @return The logical negation of this function.
	 */
	public default VoidToBooleanFunction negate() {
		return () -> !this.apply();
	}
	
	/**
	 * Returns a VoidToBooleanFunction that represents {@code !func.apply(e)}.
	 * @param func The function to negate. Must not be {@code null}.
	 * @return The logical negation of the specified function.
	 * @throws NullPointerException If the specified function is {@code null}.
	 */
	public static VoidToBooleanFunction not(VoidToBooleanFunction func) {
		return func.negate();
	}
	
}
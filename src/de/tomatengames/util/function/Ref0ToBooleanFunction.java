// !!! TextScript generated !!!
package de.tomatengames.util.function;

/**
 * A function that accepts 0 generic input parameters and returns a {@code boolean}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2023-02-19 created
 * @since 1.2
 */
@FunctionalInterface
public interface Ref0ToBooleanFunction {
	
	/**
	 * Applies the function.
	 * @return The result.
	 */
	public boolean apply();
	
	
	/**
	 * Returns a Ref0ToBooleanFunction that represents {@code !this.apply(...)}.
	 * @return The logical negation of this function.
	 */
	public default Ref0ToBooleanFunction negate() {
		return () -> !this.apply();
	}
	
	/**
	 * Returns a Ref0ToBooleanFunction that represents {@code !func.apply(...)}.
	 * @param func The function to negate. Must not be {@code null}.
	 * @return The logical negation of the specified function.
	 * @throws NullPointerException If the specified function is {@code null}.
	 */
	public static Ref0ToBooleanFunction not(Ref0ToBooleanFunction func) {
		return func.negate();
	}
	
}
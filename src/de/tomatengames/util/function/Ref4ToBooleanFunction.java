// !!! TextScript generated !!!
package de.tomatengames.util.function;

/**
 * A function that accepts 4 generic input parameters and returns a {@code boolean}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2023-02-19 created
 * @since 1.2
 */
@FunctionalInterface
public interface Ref4ToBooleanFunction<A, B, C, D> {
	
	/**
	 * Applies the function.
	 * @param a An argument.
	 * @param b An argument.
	 * @param c An argument.
	 * @param d An argument.
	 * @return The result.
	 */
	public boolean apply(A a, B b, C c, D d);
	
	
	/**
	 * Returns a Ref4ToBooleanFunction that represents {@code !this.apply(...)}.
	 * @return The logical negation of this function.
	 */
	public default Ref4ToBooleanFunction<A, B, C, D> negate() {
		return (a, b, c, d) -> !this.apply(a, b, c, d);
	}
	
	/**
	 * Returns a Ref4ToBooleanFunction that represents {@code !func.apply(...)}.
	 * @param func The function to negate. Must not be {@code null}.
	 * @return The logical negation of the specified function.
	 * @throws NullPointerException If the specified function is {@code null}.
	 */
	public static <A, B, C, D> Ref4ToBooleanFunction<A, B, C, D> not(Ref4ToBooleanFunction<A, B, C, D> func) {
		return func.negate();
	}
	
}
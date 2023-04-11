// !!! TextScript generated !!!
package de.tomatengames.util.function;

/**
 * A function that accepts 3 generic input parameters and returns a {@code boolean}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2023-02-19 created
 * @since 1.2
 */
@FunctionalInterface
public interface Ref3ToBooleanFunction<A, B, C> {
	
	/**
	 * Applies the function.
	 * @param a An argument.
	 * @param b An argument.
	 * @param c An argument.
	 * @return The result.
	 */
	public boolean apply(A a, B b, C c);
	
	
	/**
	 * Returns a Ref3ToBooleanFunction that represents {@code !this.apply(...)}.
	 * @return The logical negation of this function.
	 */
	public default Ref3ToBooleanFunction<A, B, C> negate() {
		return (a, b, c) -> !this.apply(a, b, c);
	}
	
	/**
	 * Returns a Ref3ToBooleanFunction that represents {@code !func.apply(...)}.
	 * @param func The function to negate. Must not be {@code null}.
	 * @return The logical negation of the specified function.
	 * @throws NullPointerException If the specified function is {@code null}.
	 */
	public static <A, B, C> Ref3ToBooleanFunction<A, B, C> not(Ref3ToBooleanFunction<A, B, C> func) {
		return func.negate();
	}
	
}
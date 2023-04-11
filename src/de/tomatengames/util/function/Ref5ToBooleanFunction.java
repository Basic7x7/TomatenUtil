// !!! TextScript generated !!!
package de.tomatengames.util.function;

/**
 * A function that accepts 5 generic input parameters and returns a {@code boolean}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2023-02-19 created
 * @since 1.2
 */
@FunctionalInterface
public interface Ref5ToBooleanFunction<A, B, C, D, E> {
	
	/**
	 * Applies the function.
	 * @param a An argument.
	 * @param b An argument.
	 * @param c An argument.
	 * @param d An argument.
	 * @param e An argument.
	 * @return The result.
	 */
	public boolean apply(A a, B b, C c, D d, E e);
	
	
	/**
	 * Returns a Ref5ToBooleanFunction that represents {@code !this.apply(...)}.
	 * @return The logical negation of this function.
	 */
	public default Ref5ToBooleanFunction<A, B, C, D, E> negate() {
		return (a, b, c, d, e) -> !this.apply(a, b, c, d, e);
	}
	
	/**
	 * Returns a Ref5ToBooleanFunction that represents {@code !func.apply(...)}.
	 * @param func The function to negate. Must not be {@code null}.
	 * @return The logical negation of the specified function.
	 * @throws NullPointerException If the specified function is {@code null}.
	 */
	public static <A, B, C, D, E> Ref5ToBooleanFunction<A, B, C, D, E> not(Ref5ToBooleanFunction<A, B, C, D, E> func) {
		return func.negate();
	}
	
}
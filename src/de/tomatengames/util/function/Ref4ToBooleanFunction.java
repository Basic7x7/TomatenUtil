package de.tomatengames.util.function;

/**
 * A function that accepts 4 generic input parameters and returns
 * a {@code boolean}.
 * 
 * @param <A> An input parameter type
 * @param <B> An input parameter type
 * @param <C> An input parameter type
 * @param <D> An input parameter type
 * @author Basic7x7
 * @version 2023-04-11 last modified
 * @version 2023-02-19 created
 * @since 1.2
 */
// !!! TextScript generated !!!
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
	 * @param <A> A function parameter type
	 * @param <B> A function parameter type
	 * @param <C> A function parameter type
	 * @param <D> A function parameter type
	 * @param func The function to negate. Must not be {@code null}.
	 * @return The logical negation of the specified function.
	 * @throws NullPointerException If the specified function is {@code null}.
	 */
	public static <A, B, C, D> Ref4ToBooleanFunction<A, B, C, D> not(Ref4ToBooleanFunction<A, B, C, D> func) {
		return func.negate();
	}
	
}
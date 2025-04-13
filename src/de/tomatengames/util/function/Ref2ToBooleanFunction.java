package de.tomatengames.util.function;

/**
 * A function that accepts 2 generic input parameters and returns
 * a {@code boolean}.
 * 
 * @param <A> An input parameter type
 * @param <B> An input parameter type
 * @author Basic7x7
 * @version 2023-04-11 last modified
 * @version 2023-02-19 created
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface Ref2ToBooleanFunction<A, B> {
	
	/**
	 * Applies the function.
	 * @param a An argument.
	 * @param b An argument.
	 * @return The result.
	 */
	public boolean apply(A a, B b);
	
	
	
	/**
	 * Returns a Ref2ToBooleanFunction that represents {@code !this.apply(...)}.
	 * @return The logical negation of this function.
	 */
	public default Ref2ToBooleanFunction<A, B> negate() {
		return (a, b) -> !this.apply(a, b);
	}
	
	/**
	 * Returns a Ref2ToBooleanFunction that represents {@code !func.apply(...)}.
	 * @param <A> A function parameter type
	 * @param <B> A function parameter type
	 * @param func The function to negate. Must not be {@code null}.
	 * @return The logical negation of the specified function.
	 * @throws NullPointerException If the specified function is {@code null}.
	 */
	public static <A, B> Ref2ToBooleanFunction<A, B> not(Ref2ToBooleanFunction<A, B> func) {
		return func.negate();
	}
	
}
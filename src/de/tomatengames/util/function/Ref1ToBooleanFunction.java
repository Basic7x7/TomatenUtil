package de.tomatengames.util.function;

/**
 * A function that accepts 1 generic input parameter and returns
 * a {@code boolean}.
 * 
 * @param <A> An input parameter type
 * @author Basic7x7
 * @version 2023-04-11 last modified
 * @version 2023-02-19 created
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface Ref1ToBooleanFunction<A> {
	
	/**
	 * Applies the function.
	 * @param a An argument.
	 * @return The result.
	 */
	public boolean apply(A a);
	
	
	
	/**
	 * Returns a Ref1ToBooleanFunction that represents {@code !this.apply(...)}.
	 * @return The logical negation of this function.
	 */
	public default Ref1ToBooleanFunction<A> negate() {
		return (a) -> !this.apply(a);
	}
	
	/**
	 * Returns a Ref1ToBooleanFunction that represents {@code !func.apply(...)}.
	 * @param <A> A function parameter type
	 * @param func The function to negate. Must not be {@code null}.
	 * @return The logical negation of the specified function.
	 * @throws NullPointerException If the specified function is {@code null}.
	 */
	public static <A> Ref1ToBooleanFunction<A> not(Ref1ToBooleanFunction<A> func) {
		return func.negate();
	}
	
}
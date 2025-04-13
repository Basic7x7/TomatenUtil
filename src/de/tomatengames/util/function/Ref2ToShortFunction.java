package de.tomatengames.util.function;

/**
 * A function that accepts 2 generic input parameters and returns
 * a {@code short}.
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
public interface Ref2ToShortFunction<A, B> {
	
	/**
	 * Applies the function.
	 * @param a An argument.
	 * @param b An argument.
	 * @return The result.
	 */
	public short apply(A a, B b);
	
}
package de.tomatengames.util.function;

/**
 * A function that accepts 3 generic input parameters and returns
 * nothing.
 * 
 * @param <A> An input parameter type
 * @param <B> An input parameter type
 * @param <C> An input parameter type
 * @author Basic7x7
 * @version 2023-04-11 last modified
 * @version 2023-02-19 created
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface Ref3ToVoidFunction<A, B, C> {
	
	/**
	 * Applies the function.
	 * @param a An argument.
	 * @param b An argument.
	 * @param c An argument.
	 */
	public void apply(A a, B b, C c);
	
}
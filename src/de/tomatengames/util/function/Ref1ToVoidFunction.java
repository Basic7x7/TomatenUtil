package de.tomatengames.util.function;

/**
 * A function that accepts 1 generic input parameter and returns
 * nothing.
 * 
 * @param <A> An input parameter type
 * @author Basic7x7
 * @version 2023-04-11 last modified
 * @version 2023-02-19 created
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface Ref1ToVoidFunction<A> {
	
	/**
	 * Applies the function.
	 * @param a An argument.
	 */
	public void apply(A a);
	
}
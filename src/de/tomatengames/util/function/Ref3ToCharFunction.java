package de.tomatengames.util.function;

/**
 * A function that accepts 3 generic input parameters and returns
 * a {@code char}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2023-02-19 created
 * @since 1.2
 */
// !!! TextScript generated !!!
@FunctionalInterface
public interface Ref3ToCharFunction<A, B, C> {
	
	/**
	 * Applies the function.
	 * @param a An argument.
	 * @param b An argument.
	 * @param c An argument.
	 * @return The result.
	 */
	public char apply(A a, B b, C c);
	
}
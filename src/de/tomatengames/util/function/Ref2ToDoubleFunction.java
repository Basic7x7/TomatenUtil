// !!! TextScript generated !!!
package de.tomatengames.util.function;

/**
 * A function that accepts 2 generic input parameters and returns a {@code double}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2023-02-19 created
 * @since 1.2
 */
@FunctionalInterface
public interface Ref2ToDoubleFunction<A, B> {
	
	/**
	 * Applies the function.
	 * @param a An argument.
	 * @param b An argument.
	 * @return The result.
	 */
	public double apply(A a, B b);
	
}
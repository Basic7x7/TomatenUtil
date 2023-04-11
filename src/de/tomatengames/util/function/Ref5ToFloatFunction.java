// !!! TextScript generated !!!
package de.tomatengames.util.function;

/**
 * A function that accepts 5 generic input parameters and returns a {@code float}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2023-02-19 created
 * @since 1.2
 */
@FunctionalInterface
public interface Ref5ToFloatFunction<A, B, C, D, E> {
	
	/**
	 * Applies the function.
	 * @param a An argument.
	 * @param b An argument.
	 * @param c An argument.
	 * @param d An argument.
	 * @param e An argument.
	 * @return The result.
	 */
	public float apply(A a, B b, C c, D d, E e);
	
}
// !!! TextScript generated !!!
package de.tomatengames.util.function;

/**
 * A function that accepts 8 generic input parameters and returns an {@code int}.
 * 
 * @author Basic7x7
 * @version
 * 2023-04-11 last modified<br>
 * 2023-02-19 created
 * @since 1.2
 */
@FunctionalInterface
public interface Ref8ToIntFunction<A, B, C, D, E, F, G, H> {
	
	/**
	 * Applies the function.
	 * @param a An argument.
	 * @param b An argument.
	 * @param c An argument.
	 * @param d An argument.
	 * @param e An argument.
	 * @param f An argument.
	 * @param g An argument.
	 * @param h An argument.
	 * @return The result.
	 */
	public int apply(A a, B b, C c, D d, E e, F f, G g, H h);
	
}